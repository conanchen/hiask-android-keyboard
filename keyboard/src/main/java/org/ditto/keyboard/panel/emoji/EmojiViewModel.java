package org.ditto.keyboard.panel.emoji;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pair;

import org.ditto.keyboard.apirest.util.AbsentLiveData;
import org.ditto.keyboard.repository.util.Status;
import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.usecase.UsecaseFascade;

import java.util.List;

import javax.inject.Inject;

public class EmojiViewModel extends ViewModel   {
    private final static String TAG = "EmojiViewModel";
    @VisibleForTesting
    final MutableLiveData<String> login = new MutableLiveData<>();

    @Inject
    UsecaseFascade mUsecaseFascade;
    private final LiveData<List<Gifgroup>> liveGifgroups;

    private final LiveData<Pair<List<Emoji>,Status>> liveEmojisAndStatus;
    @SuppressWarnings("unchecked")
    @Inject
    public EmojiViewModel() {

        liveGifgroups = Transformations.switchMap(login, login -> {
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.repositoryFascade.gifRepository.listGifgroupsBy(100);
            }
        });


        liveEmojisAndStatus = Transformations.switchMap(login, login -> {
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.repositoryFascade.emojiRepository.listEmojisBy(100);
            }
        });
    }


    public void refresh() {
        this.login.setValue("GO");
    }

    public LiveData<Pair<List<Emoji>, Status>> getLiveEmojisAndStatus() {
        return liveEmojisAndStatus;
    }

    public LiveData<List<Gifgroup>> getLiveGifgroups() {
        return liveGifgroups;
    }
}