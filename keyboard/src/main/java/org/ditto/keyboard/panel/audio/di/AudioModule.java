package org.ditto.keyboard.panel.audio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = AudioViewModelSubComponent.class)
public class AudioModule {

    @Singleton
    @Provides
    AudioViewModelFactory provideTestViewModelFactory(
            AudioViewModelSubComponent.Builder viewModelSubComponent) {
        return new AudioViewModelFactory(viewModelSubComponent.build());
    }


}