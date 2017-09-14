package org.ditto.keyboard.repository;

/**
 * Created by admin on 2017/7/28.
 */


import android.arch.lifecycle.LiveData;

import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.dbroom.gift.Gift;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Repository that handles Gift objects.
 */
@Singleton
public class GifRepository {

    private ApirestFascade apirestFascade;
    private RoomFascade roomFascade;

    @Inject
    public GifRepository(ApirestFascade apirestFascade, RoomFascade roomFascade) {
        this.roomFascade = roomFascade;
        this.apirestFascade = apirestFascade;
    }

    public LiveData<List<Gif>> listGifsBy(int size) {
        return roomFascade.gifDao.listGifsBy(size);
    }

    public  LiveData<List<Gifgroup>> listGifgroupsBy(int size) {
        return roomFascade.gifDao.listGroupsBy(size);
    }
    public Observable<List<Long>> saveAllGifs(List<Gif> gifs) {
        return Observable
                .fromCallable(() -> roomFascade.gifDao.saveAllGifs(gifs))
                .subscribeOn(Schedulers.io());
    }
    public Observable<List<Long>> saveAllGroups(List<Gifgroup> gifts) {
        return Observable
                .fromCallable(() -> roomFascade.gifDao.saveAllGroups(gifts))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Long> save(Gifgroup group) {
        return Observable.fromCallable(()->roomFascade.gifDao.save(group))
                .subscribeOn(Schedulers.io());

    }

}
