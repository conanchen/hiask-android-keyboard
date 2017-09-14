package org.ditto.keyboard.panel.video.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = VideoViewModelSubComponent.class)
public class VideoModule {

    @Singleton
    @Provides
    VideoViewModelFactory provideVideoViewModelFactory(
            VideoViewModelSubComponent.Builder viewModelSubComponent) {
        return new VideoViewModelFactory(viewModelSubComponent.build());
    }
}