package org.ditto.keyboard.panel.gif.di;


import org.ditto.keyboard.panel.gif.GifViewModel;
import org.ditto.keyboard.panel.gift.GiftViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link GifViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface GifViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        GifViewModelSubComponent build();
    }
    GifViewModel gifViewModel();
}