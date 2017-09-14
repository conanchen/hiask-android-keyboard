package org.ditto.keyboard.panel.gif;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import org.ditto.keyboard.apirest.util.AbsentLiveData;
import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.usecase.UsecaseFascade;

import java.util.List;

import javax.inject.Inject;

public class GifViewModel extends ViewModel {
    private final static String TAG = "GifViewModel";
    @VisibleForTesting
    final MutableLiveData<String> login = new MutableLiveData<>();

    @Inject
    UsecaseFascade mUsecaseFascade;
    private final LiveData<List<Gif>> liveGifs;
    private final LiveData<List<Gifgroup>> liveGifgroups;

    @SuppressWarnings("unchecked")
    @Inject
    public GifViewModel() {

        liveGifgroups = Transformations.switchMap(login,login->{
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.repositoryFascade.gifRepository.listGifgroupsBy(100);
            }
        });

        liveGifs = Transformations.switchMap(login,login->{
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.repositoryFascade.gifRepository.listGifsBy(100);
            }
        });
    }


    public void refresh() {
            this.login.setValue("GO");
    }

    public LiveData<List<Gif>> getLiveGifs() {
        return liveGifs;
    }

    public LiveData<List<Gifgroup>> getLiveGifgroups() {
        return liveGifgroups;
    }
}