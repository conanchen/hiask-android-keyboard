package org.ditto.keyboard;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiAppCompatEditText;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import org.ditto.keyboard.util.KeyboardUtil;

import java.io.File;

/**
 * Created by ericrichardson on 11/17/16.
 */

public class CommitEditText extends EmojiAppCompatEditText implements LifecycleOwner {
    private static final String TAG = "CommitEditText";
    private static final String AUTHORITY = "org.ditto.inappkeyboard.inputcontent";
    private LifecycleRegistry registry = new LifecycleRegistry(this);

    @Override
    public Lifecycle getLifecycle() {
        return (registry);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

      class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
        }
    }


    // Our communication link to the EditText
    private InputConnection mInputConnection;
    private final static EditorInfo mEditorInfo = new EditorInfo();
    private CommitKeyboard mCommitKeyboard;

    public void setCommitKeyboard(CommitKeyboard commitKeyboard) {
        this.mCommitKeyboard = commitKeyboard;
    }


    private InputContentInfoCompat mCurrentInputContentInfo;

    public CommitEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    public CommitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public CommitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInputConnection = this.onCreateInputConnection(mEditorInfo);
        this.getLifecycle().addObserver(new ThisLifecycleObserver());
    }

    @Override
    public InputConnection onCreateInputConnection(final EditorInfo editorInfo) {
        final InputConnection inputConnection = super.onCreateInputConnection(editorInfo);
        //TODO: should be passed from AttributeSet in layout
        String[] contentMimeTypes = new String[]{"image/png", "image/gif", "image/jpeg", "image/webp"};
        EditorInfoCompat.setContentMimeTypes(editorInfo, contentMimeTypes);

        return InputConnectionCompat.createWrapper(inputConnection, editorInfo,
                (InputContentInfoCompat inputContentInfo, int flags, Bundle opts) -> {
                    if (mCommitKeyboard.getCommitListener() != null) {
                        //----------------
                        boolean supported = false;
                        for (final String mimeType : contentMimeTypes) {
                            if (inputContentInfo.getDescription().hasMimeType(mimeType)) {
                                supported = true;
                                break;
                            }
                        }
                        if (!supported) {
                            return false;
                        }

                        // Clear the temporary permission (if any).  See below about why we do this here.
                        try {
                            if (mCurrentInputContentInfo != null) {
                                mCurrentInputContentInfo.releasePermission();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "InputContentInfoCompat#releasePermission() failed.", e);
                        } finally {
                            mCurrentInputContentInfo = null;
                        }
                        if ((flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
                            try {
                                inputContentInfo.requestPermission();
                            } catch (Exception e) {
                                Log.e(TAG, "InputContentInfoCompat#requestPermission() failed.", e);
                                return false;
                            }
                        }

                        mCommitKeyboard.getCommitListener().onCommitContent(inputContentInfo, flags, opts);
                        // Due to the asynchronous nature of WebView, it is a bit too early to call
                        // inputContentInfo.releasePermission() here. Hence we call IC#releasePermission() when this
                        // method is called next time.  Note that calling IC#releasePermission() is just to be a
                        // good citizen. Even if we failed to call that method, the system would eventually revoke
                        // the permission sometime after inputContentInfo object gets garbage-collected.
                        mCurrentInputContentInfo = inputContentInfo;
                    }
                    return true;  // return true if succeeded

                });
    }


    protected void upsertText(String value, int flag) {

        // do nothing if the InputConnection has not been set yet
        if (mInputConnection == null) {
            Log.e(TAG, "do nothing as the InputConnection has not been set yet");
            return;
        }
        mInputConnection.commitText(value, flag);
    }


    protected void deleteText() {

        // do nothing if the InputConnection has not been set yet
        if (mInputConnection == null) {
            Log.e(TAG, "do nothing as the InputConnection has not been set yet");
            return;
        }

        CharSequence selectedText = mInputConnection.getSelectedText(0);
        if (TextUtils.isEmpty(selectedText)) {
            // no selection, so delete previous character
            mInputConnection.deleteSurroundingText(1, 0);
        } else {
            // delete the selection
            mInputConnection.commitText("", 1);
        }

    }

    protected void commitContent(@NonNull String description, @NonNull String mimeType, @NonNull File file) {

        // do nothing if the InputConnection has not been set yet
        if (mInputConnection == null) {
            Log.e(TAG, "do nothing as the InputConnection has not been set yet");
            return;
        }

        // Validate packageName again just in case.
        if (!KeyboardUtil.validatePackageName(mEditorInfo)) {
            return;
        }

        final Uri contentUri = FileProvider.getUriForFile(getContext(), AUTHORITY, file);

        // As you as an IME author are most likely to have to implement your own content provider
        // to support CommitContent API, it is important to have a clear spec about what
        // applications are going to be allowed to access the content that your are going to share.
        final int flag;
        if (Build.VERSION.SDK_INT >= 25) {
            // On API 25 and later devices, as an analogy of Intent.FLAG_GRANT_READ_URI_PERMISSION,
            // you can specify InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION to give
            // a temporary read access to the recipient application without exporting your content
            // provider.
            flag = InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION;
        } else {
            // On API 24 and prior devices, we cannot rely on
            // InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION. You as an IME author
            // need to decide what access control is needed (or not needed) for content URIs that
            // you are going to expose. This sample uses Context.grantUriPermission(), but you can
            // implement your own mechanism that satisfies your own requirements.
            flag = 0;
            try {
                // TODO: Use revokeUriPermission to revoke as needed.
                getContext().grantUriPermission(
                        mEditorInfo.packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception e) {
                Log.e(TAG, "grantUriPermission failed packageName=" + mEditorInfo.packageName
                        + " contentUri=" + contentUri, e);
            }
        }

        final InputContentInfoCompat inputContentInfoCompat = new InputContentInfoCompat(
                contentUri,
                new ClipDescription(description, new String[]{mimeType}),
                null /* linkUrl */);
        InputConnectionCompat.commitContent(
                mInputConnection, mEditorInfo, inputContentInfoCompat,
                flag, null);
    }
}