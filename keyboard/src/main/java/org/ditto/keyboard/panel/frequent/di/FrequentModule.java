package org.ditto.keyboard.panel.frequent.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = FrequentViewModelSubComponent.class)
public class FrequentModule {

    @Singleton
    @Provides
    FrequentViewModelFactory provideTestViewModelFactory(
            FrequentViewModelSubComponent.Builder viewModelSubComponent) {
        return new FrequentViewModelFactory(viewModelSubComponent.build());
    }


}