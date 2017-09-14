package org.ditto.keyboard.panel.video.di;

import android.arch.lifecycle.ViewModel;

import org.ditto.keyboard.panel.audio.AudioViewModel;
import org.ditto.keyboard.panel.video.VideoViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VideoViewModelFactory extends BaseViewModelFactory {

    @Inject
    public VideoViewModelFactory(final VideoViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(VideoViewModel.class, () -> viewModelSubComponent.videoViewModel());
    }

}