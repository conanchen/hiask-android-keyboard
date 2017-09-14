package org.ditto.keyboard.panel.emoji.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = EmojiViewModelSubComponent.class)
public class EmojiModule {

    @Singleton
    @Provides
    EmojiViewModelFactory provideTestViewModelFactory(
            EmojiViewModelSubComponent.Builder viewModelSubComponent) {
        return new EmojiViewModelFactory(viewModelSubComponent.build());
    }


}