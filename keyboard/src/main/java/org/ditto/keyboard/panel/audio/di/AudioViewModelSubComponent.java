package org.ditto.keyboard.panel.audio.di;



import org.ditto.keyboard.panel.audio.AudioViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link AudioViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface AudioViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        AudioViewModelSubComponent build();
    }
    AudioViewModel testViewModel();
}