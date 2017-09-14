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
public class VideoPlaybackFragment extends KeyboardPanelbaseFragment  {
    private final static String TAG = "VideoPlaybackFragment";

    private static final int RC_CAMERA_PERM = 123;

    @Inject
    VideoViewModelFactory viewModelFactory;
    private VideoViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoPlaybackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View   view = (View) inflater.inflate(R.layout.fragment_panel_videoaudio_playback, container, false);


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


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String title;
        private int icon;
        private String tagName;

        Builder() {
        }

        public VideoPlaybackFragment build() {
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
            VideoPlaybackFragment fragment = new VideoPlaybackFragment();
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
