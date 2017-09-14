package org.ditto.keyboard.repository;

/**
 * Created by admin on 2017/7/28.
 */


import android.arch.lifecycle.LiveData;

import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.photo.Photo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Repository that handles Photo objects.
 */
@Singleton
public class PhotoRepository {

    private ApirestFascade apirestFascade;
    private RoomFascade roomFascade;
    private PhotoContentQuery photoContentQuery;

    @Inject
    public PhotoRepository(ApirestFascade apirestFascade, RoomFascade roomFascade,PhotoContentQuery photoContentQuery) {
        this.roomFascade = roomFascade;
        this.apirestFascade = apirestFascade;
        this.photoContentQuery = photoContentQuery;
    }

    public LiveData<List<Photo>> listLiveContentQueryPhotosBy(int pageSize, int pageIndex) {
        return photoContentQuery.listPhotosBy(pageSize, pageIndex);
    }

    public LiveData<Long> save(final Photo carousel) {
        return new LiveData<Long>() {
            {
                Observable
                        .fromCallable(() -> roomFascade.photoDao.save(carousel))
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribe(id -> {
                            postValue(id);
                        });
            }
        };
    }

    public void delete(Photo carousel) {
        roomFascade.photoDao.delete(carousel);
    }

    public List<Photo> listPhotos() {
        return roomFascade.photoDao.listPhotos();
    }

    public LiveData<List<Photo>> listLiveDatabasePhotos() {
        return roomFascade.photoDao.listLivePhotos();
    }

    public LiveData<Long> liveCountPhotoSelected() {
        return roomFascade.photoDao.liveCountPhotoSelected();
    }

    public Flowable<List<Photo>> listFlowableSelectedPhotos() {
        return roomFascade.photoDao.listFlowableSelectedPhotos();
    }

    public void cleanSelectedPhotos() {
          roomFascade.photoDao.cleanSelectedPhotos();
    }
}
