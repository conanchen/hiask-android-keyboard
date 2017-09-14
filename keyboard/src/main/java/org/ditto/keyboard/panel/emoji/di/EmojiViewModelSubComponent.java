package org.ditto.keyboard.panel.emoji.di;


import org.ditto.keyboard.panel.emoji.EmojiViewModel;
import org.ditto.keyboard.panel.gift.GiftViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link EmojiViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface EmojiViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        EmojiViewModelSubComponent build();
    }
    EmojiViewModel emojiViewModel();
}