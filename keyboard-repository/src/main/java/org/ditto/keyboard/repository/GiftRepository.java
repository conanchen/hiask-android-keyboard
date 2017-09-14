package org.ditto.keyboard.repository;

/**
 * Created by admin on 2017/7/28.
 */


import android.arch.lifecycle.LiveData;

import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.gift.Giftgroup;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Repository that handles Gift objects.
 */
@Singleton
public class GiftRepository {

    private ApirestFascade apirestFascade;
    private RoomFascade roomFascade;

    @Inject
    public GiftRepository(ApirestFascade apirestFascade, RoomFascade roomFascade) {
        this.roomFascade = roomFascade;
        this.apirestFascade = apirestFascade;
    }

    public LiveData<List<Gift>> listGiftsBy(int size) {
        return roomFascade.giftDao.listGiftsBy(size);
    }

    public Observable<List<Long>> saveAllGifts(List<Gift> gifts) {
        return Observable
                .fromCallable(() -> roomFascade.giftDao.saveAllGifts(gifts))
                .subscribeOn(Schedulers.io());
    }
    public Observable<List<Long>> saveAllGroups(List<Giftgroup> gifts) {
        return Observable
                .fromCallable(() -> roomFascade.giftDao.saveAllGroups(gifts))
                .subscribeOn(Schedulers.io());
    }

    public  LiveData<List<Giftgroup>> listGiftgroupsBy(int size) {
        return roomFascade.giftDao.listGroupsBy(size);
    }
}
