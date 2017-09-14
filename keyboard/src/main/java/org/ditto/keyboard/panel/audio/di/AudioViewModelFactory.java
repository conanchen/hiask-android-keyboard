package org.ditto.keyboard.panel.audio.di;

import android.arch.lifecycle.ViewModel;


import org.ditto.keyboard.panel.audio.AudioViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AudioViewModelFactory extends BaseViewModelFactory {

    @Inject
    public AudioViewModelFactory(final AudioViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(AudioViewModel.class, new Callable<ViewModel>() {
            @Override
            public ViewModel call() throws Exception {
                return viewModelSubComponent.testViewModel();
            }
        });
    }

}