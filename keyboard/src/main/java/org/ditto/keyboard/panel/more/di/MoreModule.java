package org.ditto.keyboard.panel.more.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = MoreViewModelSubComponent.class)
public class MoreModule {

    @Singleton
    @Provides
    MoreViewModelFactory provideTestViewModelFactory(
            MoreViewModelSubComponent.Builder viewModelSubComponent) {
        return new MoreViewModelFactory(viewModelSubComponent.build());
    }


}