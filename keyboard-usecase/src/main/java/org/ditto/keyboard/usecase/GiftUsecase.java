package org.ditto.keyboard.usecase;

import android.arch.lifecycle.LiveData;

import org.ditto.keyboard.apirest.util.Resource;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.gift.Giftgroup;
import org.ditto.keyboard.dbroom.user.User;
import org.ditto.keyboard.repository.RepositoryFascade;
import org.ditto.keyboard.usecase.hello.Hello;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/6/25.
 */

public class GiftUsecase {
    private RepositoryFascade repositoryFascade;

    @Inject
    public GiftUsecase(RepositoryFascade repositoryFascade ) {
        this.repositoryFascade = repositoryFascade;
    }


    public Observable<List<Long>> saveSampleGifts() {


        List<Giftgroup> groups = new ArrayList<Giftgroup>() {
            {
                for (int i = 0; i < 20; i++) {
                    String groupUuid = "uuid" + i;
                    add(Giftgroup
                            .builder()
                            .setUuid(groupUuid)
                            .setName(i + "组图")
                            .setSequence(i)
                            .build()
                    );

                }
            }
        };
        repositoryFascade.giftRepository
                .saveAllGroups(groups)
                .observeOn(Schedulers.io())
                .subscribe();

        List<Gift> gifts = new ArrayList<Gift>() {
            {
                for (int i = 0 ; i< groups.size();i++) {
                    Giftgroup group = groups.get(i);
                    for (int j = 0; j < 20; j++) {
                        add(Gift.builder()
                                .setUuid(group.uuid + j)
                                .setGroupUuid(group.uuid)
                                .setName(group.uuid + j + "礼物")
                                .setPrice(i*10+j+1)
                                .build());
                    }
                }
            }
        };
        return repositoryFascade.giftRepository
                .saveAllGifts(gifts)
                .subscribeOn(Schedulers.io());
    }

}
