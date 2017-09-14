package org.ditto.keyboard.di;


import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.CommitKeyboardViewModel;
import org.ditto.keyboard.panel.gift.GiftViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link CommitKeyboardViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface CommitKeyboardViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        CommitKeyboardViewModelSubComponent build();
    }
    CommitKeyboardViewModel commitKeyboardViewModel();
}