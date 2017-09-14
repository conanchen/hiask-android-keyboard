package org.ditto.keyboard.panel.video;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Strings;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.R;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.video.di.VideoViewModelFactory;
import org.ditto.keyboard.util.Constants;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;

import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;


/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class VideoScanFragment extends KeyboardPanelbaseFragment implements EasyPermissions.PermissionCallbacks {
    private final static String TAG = "VideoRecordFragment";


    @Inject
    VideoViewModelFactory viewModelFactory;
    private VideoViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoScanFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_panel_video_record, container, false);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KeyboardInjector.getKeyboardComponent().inject(this);
        setupController();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        viewModel.refresh();
        audioTask();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //recyclerView.setAdapter(null);
    }


    private void setupController() {
        Timber.d("calling setupController");
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel.class);

//        Map<String, Object> datas = new HashMap<>();
//        datas.put(Constants.DATA1, new ArrayList<IndexBuyanswer>());
//
//
//        viewModel.getLiveBuyanswerIndices().observe(this, buyanswers -> {
//            datas.put(Constants.DATA1, buyanswers);
//            controller.setData((ArrayList<IndexBuyanswer>) datas.get(Constants.DATA1));
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "viewModel.mUsecaseFascade.userUsecase.getHello()=" + viewModel.mUsecaseFascade.userUsecase.getHello());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(Constants.RC_CAMERA_PERM)
    public void audioTask() {
        if (EasyPermissions.hasPermissions(this.getContext(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            Toast.makeText(this.getContext(), "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_audio),
                    Constants.RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this.getContext(), R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String title;
        private int icon;
        private String tagName;

        Builder() {
        }

        public VideoScanFragment build() {
            String missing = "";
            if (Strings.isNullOrEmpty(title)) {
                missing += " title";
            }
            if (Strings.isNullOrEmpty(tagName)) {
                missing += " tagName";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            VideoScanFragment fragment = new VideoScanFragment();
            fragment.setTitle(title).setIcon(icon).setTagName(tagName);
            return fragment;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }


    }

}
