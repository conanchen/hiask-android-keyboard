package org.ditto.keyboard.panel.video.di;



import org.ditto.keyboard.panel.audio.AudioViewModel;
import org.ditto.keyboard.panel.video.VideoViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link VideoViewModelFactory}. Using this component allows
 * ViewModels to define {@link javax.inject.Inject} constructors.
 */
@Subcomponent
public interface VideoViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        VideoViewModelSubComponent build();
    }
    VideoViewModel videoViewModel();
}