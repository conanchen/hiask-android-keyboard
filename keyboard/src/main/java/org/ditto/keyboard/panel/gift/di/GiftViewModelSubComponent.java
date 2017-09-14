package org.ditto.keyboard.panel.gift.di;


import org.ditto.keyboard.panel.gift.GiftViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link GiftViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface GiftViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        GiftViewModelSubComponent build();
    }
    GiftViewModel giftViewModel();
}