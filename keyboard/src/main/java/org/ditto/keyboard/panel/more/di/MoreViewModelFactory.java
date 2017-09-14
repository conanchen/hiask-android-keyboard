package org.ditto.keyboard.panel.more.di;

import org.ditto.keyboard.panel.gift.GiftViewModel;
import org.ditto.keyboard.panel.more.MoreViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MoreViewModelFactory extends BaseViewModelFactory {

    @Inject
    public MoreViewModelFactory(final MoreViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(MoreViewModel.class, () -> viewModelSubComponent.moreViewModel());
    }
}