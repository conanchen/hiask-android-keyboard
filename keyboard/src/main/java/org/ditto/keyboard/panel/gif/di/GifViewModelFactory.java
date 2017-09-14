package org.ditto.keyboard.panel.gif.di;

import org.ditto.keyboard.panel.gif.GifViewModel;
import org.ditto.keyboard.panel.gift.GiftViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GifViewModelFactory extends BaseViewModelFactory {

    @Inject
    public GifViewModelFactory(final GifViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(GifViewModel.class, () -> viewModelSubComponent.gifViewModel());
    }
}