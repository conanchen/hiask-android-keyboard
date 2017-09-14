package org.ditto.keyboard.panel.photo.di;


import org.ditto.keyboard.panel.gif.GifViewModel;
import org.ditto.keyboard.panel.photo.PhotoViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link PhotoViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface PhotoViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        PhotoViewModelSubComponent build();
    }
    PhotoViewModel photoViewModel();
}