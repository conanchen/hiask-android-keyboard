package org.ditto.keyboard.panel.frequent.di;

import android.arch.lifecycle.ViewModel;

import org.ditto.keyboard.panel.frequent.FrequentViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FrequentViewModelFactory extends BaseViewModelFactory {

    @Inject
    public FrequentViewModelFactory(final FrequentViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(FrequentViewModel.class, ()-> viewModelSubComponent.frequentViewModel());
    }

}