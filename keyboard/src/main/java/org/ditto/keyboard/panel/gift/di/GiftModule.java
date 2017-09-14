package org.ditto.keyboard.panel.gift.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = GiftViewModelSubComponent.class)
public class GiftModule {

    @Singleton
    @Provides
    GiftViewModelFactory provideTestViewModelFactory(
            GiftViewModelSubComponent.Builder viewModelSubComponent) {
        return new GiftViewModelFactory(viewModelSubComponent.build());
    }


}