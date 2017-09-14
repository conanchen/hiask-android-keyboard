package org.ditto.keyboard.panel.emoji.di;

import org.ditto.keyboard.panel.emoji.EmojiViewModel;
import org.ditto.keyboard.panel.gift.GiftViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmojiViewModelFactory extends BaseViewModelFactory {

    @Inject
    public EmojiViewModelFactory(final EmojiViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(EmojiViewModel.class, () -> viewModelSubComponent.emojiViewModel());
    }
}