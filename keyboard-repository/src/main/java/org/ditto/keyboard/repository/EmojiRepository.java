package org.ditto.keyboard.repository;

/**
 * Created by admin on 2017/7/28.
 */


import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.gson.Gson;

import org.ditto.grpc.emoji.EmojiOuterClass;
import org.ditto.keyboard.apigrpc.ApigrpcFascade;
import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.dbroom.emoji.Emojigroup;
import org.ditto.keyboard.repository.util.LiveDataAndStatus;
import org.ditto.keyboard.repository.util.Status;
import org.ditto.keyboard.repository.util.StatusLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Repository that handles Emojit objects.
 */
@Singleton
public class EmojiRepository {

    private final static String TAG = "EmojiRepository";

    private ApirestFascade apirestFascade;
    private ApigrpcFascade apigrpcFascade;
    private RoomFascade roomFascade;

    private final static Gson gson = new Gson();
    @Inject
    public EmojiRepository(
            ApirestFascade apirestFascade,
            ApigrpcFascade apigrpcFascade,
            RoomFascade roomFascade) {
        this.roomFascade = roomFascade;
        this.apirestFascade = apirestFascade;
        this.apigrpcFascade = apigrpcFascade;
    }


    public LiveDataAndStatus<List<Emoji>> listEmojisBy(int size) {

        StatusLiveData statusLiveData = new StatusLiveData();
        Observable.just(true)
                .map(new Function<Boolean, List<EmojiOuterClass.EmojiResponse>>() {
                    @Override
                    public List<EmojiOuterClass.EmojiResponse> apply(@NonNull Boolean aBoolean) throws Exception {
                        return apigrpcFascade.emojiService.getEmojiResponses(100);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        new Consumer<List<EmojiOuterClass.EmojiResponse>>() {
                            @Override
                            public void accept(@NonNull List<EmojiOuterClass.EmojiResponse> emojiResponses) throws Exception {
                                for (EmojiOuterClass.EmojiResponse emojiResponse : emojiResponses) {
                                    roomFascade.emojiDao.save(
                                            Emoji
                                                    .builder()
                                                    .setCodepoint(emojiResponse.getCodepoint())
                                                    .setCodepointu16(emojiResponse.getCodepointu16())
                                                    .setGroupId(emojiResponse.getGroupId())
                                                    .setSubgroupId(emojiResponse.getSubgroupId())
                                                    .setName(emojiResponse.getName())
                                                    .setSequence(emojiResponse.getSequence())
                                                    .setLastUpdated(emojiResponse.getLastUpdated())
                                                    .setActive(emojiResponse.getActive())
                                                    .build()
                                    );
                                    Log.e(TAG,"emojiResponse="+gson.toJson(emojiResponse));
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Log.e(TAG, "call apigrpcFascade.emojiService.getEmojiResponses(-1) " + throwable.getMessage());
                                statusLiveData.setStatus(Status
                                        .builder()
                                        .setCode(Status.Code.BAD_URL)
                                        .setMessage("call apigrpcFascade.emojiService.getEmojiResponses(-1) " + throwable.getMessage())
                                        .build());
                            }
                        }
                );

        Log.e(TAG, "call roomFascade.emojiDao.listEmojisBy(size)");
        LiveData<List<Emoji>> liveEmojis = roomFascade.emojiDao.listEmojisBy(size);
        return new LiveDataAndStatus(liveEmojis, statusLiveData);
    }

    public LiveData<List<Emojigroup>> listEmojigroupsBy(int size) {
        return roomFascade.emojiDao.listGroupsBy(size);
    }

    public Observable<List<Long>> saveAllEmojis(List<Emoji> gifs) {
        return Observable
                .fromCallable(() -> roomFascade.emojiDao.saveAllEmojis(gifs))
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Long>> saveAllGroups(List<Emojigroup> gifts) {
        return Observable
                .fromCallable(() -> roomFascade.emojiDao.saveAllGroups(gifts))
                .subscribeOn(Schedulers.io());
    }

    public Observable<Long> save(Emojigroup group) {
        return Observable.fromCallable(() -> roomFascade.emojiDao.save(group))
                .subscribeOn(Schedulers.io());

    }

}
