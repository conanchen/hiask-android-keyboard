package org.ditto.keyboard.di;

import org.ditto.keyboard.CommitKeyboardViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommitKeyboardViewModelFactory extends BaseViewModelFactory {

    @Inject
    public CommitKeyboardViewModelFactory(final CommitKeyboardViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(CommitKeyboardViewModel.class, () -> viewModelSubComponent.commitKeyboardViewModel());
    }
}