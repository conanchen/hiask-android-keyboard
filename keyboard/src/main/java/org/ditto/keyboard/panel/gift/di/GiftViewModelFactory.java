package org.ditto.keyboard.panel.gift.di;

import org.ditto.keyboard.panel.gift.GiftViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GiftViewModelFactory extends BaseViewModelFactory {

    @Inject
    public GiftViewModelFactory(final GiftViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(GiftViewModel.class, () -> viewModelSubComponent.giftViewModel());
    }
}