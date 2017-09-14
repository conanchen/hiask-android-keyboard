package org.ditto.keyboard.usecase;

import android.arch.lifecycle.LiveData;

import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.repository.RepositoryFascade;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/6/25.
 */

public class PhotoUsecase {
    private RepositoryFascade repositoryFascade;

    @Inject
    public PhotoUsecase(RepositoryFascade repositoryFascade) {
        this.repositoryFascade = repositoryFascade;
    }

    public LiveData<Long> toggleSelect(Photo carousel) {
        return repositoryFascade.photoRepository.save(carousel);

    }

    public Flowable<List<Photo>> listFlowableSelectedPhotos() {
        return repositoryFascade.photoRepository.listFlowableSelectedPhotos();
    }

    public void cleanSelectedPhotos() {
        repositoryFascade.photoRepository.cleanSelectedPhotos();
    }
}
