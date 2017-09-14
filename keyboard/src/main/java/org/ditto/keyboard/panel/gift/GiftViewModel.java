package org.ditto.keyboard.panel.gift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import org.ditto.keyboard.apirest.data.GitUser;
import org.ditto.keyboard.apirest.util.AbsentLiveData;
import org.ditto.keyboard.apirest.util.Resource;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.gift.Giftgroup;
import org.ditto.keyboard.dbroom.user.User;
import org.ditto.keyboard.usecase.UsecaseFascade;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GiftViewModel extends ViewModel {
    private final static String TAG = "GiftViewModel";
    @VisibleForTesting
    final MutableLiveData<String> login = new MutableLiveData<>();

    @Inject
    UsecaseFascade mUsecaseFascade;
    private final LiveData<List<Gift>> liveGifts;
    private final LiveData<List<Giftgroup>> liveGifgroups;

    @SuppressWarnings("unchecked")
    @Inject
    public GiftViewModel() {

        liveGifgroups = Transformations.switchMap(login,login->{
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.repositoryFascade.giftRepository.listGiftgroupsBy(100);
            }
        });


        liveGifts = Transformations.switchMap(login,login->{
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.repositoryFascade.giftRepository.listGiftsBy(100);
            }
        });
    }


    public void refresh() {
            this.login.setValue("GO");
    }

    public LiveData<List<Gift>> getLiveGifts() {
        return liveGifts;
    }

    public LiveData<List<Giftgroup>> getLiveGiftgroups() {
        return liveGifgroups;
    }
}