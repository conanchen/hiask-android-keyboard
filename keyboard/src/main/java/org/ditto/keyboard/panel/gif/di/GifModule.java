package org.ditto.keyboard.panel.gif.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = GifViewModelSubComponent.class)
public class GifModule {

    @Singleton
    @Provides
    GifViewModelFactory provideTestViewModelFactory(
            GifViewModelSubComponent.Builder viewModelSubComponent) {
        return new GifViewModelFactory(viewModelSubComponent.build());
    }


}