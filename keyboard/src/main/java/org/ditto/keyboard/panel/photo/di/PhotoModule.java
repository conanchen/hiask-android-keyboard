package org.ditto.keyboard.panel.photo.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = PhotoViewModelSubComponent.class)
public class PhotoModule {

    @Singleton
    @Provides
    PhotoViewModelFactory provideTestViewModelFactory(
            PhotoViewModelSubComponent.Builder viewModelSubComponent) {
        return new PhotoViewModelFactory(viewModelSubComponent.build());
    }


}