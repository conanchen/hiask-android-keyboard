package org.ditto.keyboard.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Utility methods for manipulating the onscreen keyboard
 */
public class KeyboardUtil {
    private final static String TAG = "KeyboardUtil";

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Context context) {
        Activity activity = getActivity(context);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }


    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


    /**
     * 开关输入法
     *
     * @param context 找到当前焦点view
     */
    public static void toggleSoftInput(Context context) {
        Activity activity = getActivity(context);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(focusedView, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void updateSoftInputMethod(Context context, int softInputMode) {
        Activity activity = getActivity(context);
        if (!activity.isFinishing()) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            if (params.softInputMode != softInputMode) {
                params.softInputMode = softInputMode;
                activity.getWindow().setAttributes(params);
            }
        }
    }

    public static boolean validatePackageName(@Nullable EditorInfo editorInfo) {

        if (editorInfo == null) {
            return false;
        }
        return true;

//        if (editorInfo == null) {
//            return false;
//        }
//        final String packageName = editorInfo.packageName;
//        if (packageName == null) {
//            return false;
//        }
//
//        // In Android L MR-1 and prior devices, EditorInfo.packageName is not a reliable identifier
//        // of the target application because:
//        //   1. the system does not verify it [1]
//        //   2. InputMethodManager.startInputInner() had filled EditorInfo.packageName with
//        //      view.getContext().getPackageName() [2]
//        // [1]: https://android.googlesource.com/platform/frameworks/base/+/a0f3ad1b5aabe04d9eb1df8bad34124b826ab641
//        // [2]: https://android.googlesource.com/platform/frameworks/base/+/02df328f0cd12f2af87ca96ecf5819c8a3470dc8
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return true;
//        }
//
//        final InputBinding inputBinding = getCurrentInputBinding();
//        if (inputBinding == null) {
//            // Due to b.android.com/225029, it is possible that getCurrentInputBinding() returns
//            // null even after onStartInputView() is called.
//            // TODO: Come up with a way to work around this bug....
//            Log.e(TAG, "inputBinding should not be null here. "
//                    + "You are likely to be hitting b.android.com/225029");
//            return false;
//        }
//        final int packageUid = inputBinding.getUid();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            final AppOpsManager appOpsManager =
//                    (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//            try {
//                appOpsManager.checkPackage(packageUid, packageName);
//            } catch (Exception e) {
//                return false;
//            }
//            return true;
//        }
//
//        final PackageManager packageManager = getPackageManager();
//        final String possiblePackageNames[] = packageManager.getPackagesForUid(packageUid);
//        for (final String possiblePackageName : possiblePackageNames) {
//            if (packageName.equals(possiblePackageName)) {
//                return true;
//            }
//        }
//        return false;
    }

    public static int convertDpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // Class is not instantiable
    private KeyboardUtil() {
    }

}
