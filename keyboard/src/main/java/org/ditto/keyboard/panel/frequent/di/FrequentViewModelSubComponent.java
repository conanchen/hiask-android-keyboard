package org.ditto.keyboard.panel.frequent.di;


import org.ditto.keyboard.panel.frequent.FrequentViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link FrequentViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface FrequentViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        FrequentViewModelSubComponent build();
    }
    FrequentViewModel frequentViewModel();
}