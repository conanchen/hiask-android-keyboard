package org.ditto.keyboard.panel.photo;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.google.common.base.Strings;

import org.ditto.keyboard.apirest.util.AbsentLiveData;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.usecase.UsecaseFascade;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PhotoViewModel extends ViewModel {
    private final static String TAG = "PhotoViewModel";
    @VisibleForTesting
    final MutableLiveData<String> login = new MutableLiveData<>();

    @Inject
    Context mContext;
    @Inject
    UsecaseFascade mUsecaseFascade;
    private final LiveData<List<Photo>> liveContentQueryPhotos;
    private final LiveData<List<Photo>> liveDatabasePhotos;
    private final LiveData<Long> liveCountPhotoSelected;

    @SuppressWarnings("unchecked")
    @Inject
    public PhotoViewModel() {

        liveContentQueryPhotos = Transformations.switchMap(login, new Function<String, LiveData<List<Photo>>>() {
            @Override
            public LiveData<List<Photo>> apply(String s) {
                if (Strings.isNullOrEmpty(s)) {
                    return AbsentLiveData.create();
                } else {
                    return mUsecaseFascade.repositoryFascade.photoRepository.listLiveContentQueryPhotosBy(100, 0);
//
//                    //TODO: try to follow GiftDao_Impl implementation instead
//                    Observable<Cursor> cursorObservable = RxCursorLoader.create(mContext.getContentResolver(),
//                            MediaQuery.imagesQuery(100,0))
//                            .subscribeOn(Schedulers.io());
////                    Publisher<Cursor> publisher = cursorObservable.toFlowable(BackpressureStrategy.LATEST);
//
////                    return LiveDataReactiveStreams.fromPublisher(publisher);
//                    return new LiveData<Observable<Cursor>>() {
//                        {
//                            postValue(cursorObservable);
//                        }
//                    };
                }
            }
        });
        liveDatabasePhotos = Transformations.switchMap(login, new Function<String, LiveData<List<Photo>>>() {
            @Override
            public LiveData<List<Photo>> apply(String s) {
                if (Strings.isNullOrEmpty(s)) {
                    return AbsentLiveData.create();
                } else {
                    return mUsecaseFascade.repositoryFascade.photoRepository.listLiveDatabasePhotos();
                }
            }
        });
        liveCountPhotoSelected = Transformations.switchMap(login, new Function<String, LiveData<Long>>() {
            @Override
            public LiveData<Long> apply(String s) {
                if (Strings.isNullOrEmpty(s)) {
                    return AbsentLiveData.create();
                } else {
                    return mUsecaseFascade.repositoryFascade.photoRepository.liveCountPhotoSelected();
                }
            }
        });
    }


    public void refresh() {
        this.login.setValue("GO");
    }

    public void refresh(String value) {
        this.login.setValue(value);
    }

    public LiveData<List<Photo>> getLiveContentQueryPhotos() {
        return liveContentQueryPhotos;
    }

    public LiveData<List<Photo>> getLiveDatabasePhotos() {
        return liveDatabasePhotos;
    }

    public LiveData<Long> toggleSelect(Photo carousel) {
       return  mUsecaseFascade.photoUsecase.toggleSelect(carousel);
    }

    public LiveData<Long> getLiveCountPhotoSelected() {
        return liveCountPhotoSelected;
    }

    public Flowable<List<Photo>> listFlowableSelectedPhotos() {
        return mUsecaseFascade.photoUsecase.listFlowableSelectedPhotos();
    }

    public void cleanSelectedPhotos() {
        Observable.just(true).observeOn(Schedulers.io()).subscribe(aBoolean -> {
            mUsecaseFascade.photoUsecase.cleanSelectedPhotos();
        });
    }
}