package org.ditto.keyboard.panel.photo.di;

import org.ditto.keyboard.panel.gif.GifViewModel;
import org.ditto.keyboard.panel.photo.PhotoViewModel;
import org.ditto.keyboard.util.BaseViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoViewModelFactory extends BaseViewModelFactory {

    @Inject
    public PhotoViewModelFactory(final PhotoViewModelSubComponent viewModelSubComponent) {
        super();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.
        super.creators.put(PhotoViewModel.class, () -> viewModelSubComponent.photoViewModel());
    }
}