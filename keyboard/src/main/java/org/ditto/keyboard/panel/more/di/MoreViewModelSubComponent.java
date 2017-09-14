package org.ditto.keyboard.panel.more.di;


import org.ditto.keyboard.panel.gift.GiftViewModel;
import org.ditto.keyboard.panel.more.MoreViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link MoreViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface MoreViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        MoreViewModelSubComponent build();
    }
    MoreViewModel moreViewModel();
}