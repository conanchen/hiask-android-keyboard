package org.ditto.keyboard.usecase;

import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.repository.RepositoryFascade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/6/25.
 */

public class GifUsecase {
    private RepositoryFascade repositoryFascade;

    @Inject
    public GifUsecase(RepositoryFascade repositoryFascade) {
        this.repositoryFascade = repositoryFascade;
    }

    public Observable<List<Long>> saveSampleGifs() {


        List<Gifgroup> groups = new ArrayList<Gifgroup>() {
            {
                for (int i = 0; i < 20; i++) {
                    String groupUuid = "uuid" + i;
                    add(Gifgroup
                            .builder()
                            .setUuid(groupUuid)
                            .setName(i + "组图")
                            .setSequence(i)
                            .build()
                    );

                }
            }
        };
        repositoryFascade.gifRepository
                .saveAllGroups(groups)
                .observeOn(Schedulers.io())
                .subscribe();

        List<Gif> gifs = new ArrayList<Gif>() {
            {
                for (Gifgroup group : groups) {
                    for (int i = 0; i < 20; i++) {
                        add(Gif
                                .builder()
                                .setUuid(group.uuid + i)
                                .setGroupUuid(group.uuid)
                                .setName(group.uuid + i + "斗图")
                                .build());
                    }
                }
            }
        };
        return repositoryFascade.gifRepository
                .saveAllGifs(gifs)
                .subscribeOn(Schedulers.io());
    }
}
