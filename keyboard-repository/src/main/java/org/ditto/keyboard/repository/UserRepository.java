package org.ditto.keyboard.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.ditto.keyboard.apigrpc.ApigrpcFascade;
import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.apirest.data.GitUser;
import org.ditto.keyboard.apirest.util.ApiResponse;
import org.ditto.keyboard.apirest.util.AppExecutors;
import org.ditto.keyboard.apirest.util.Resource;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.user.User;
import org.ditto.keyboard.apirest.util.NetworkRestBoundResource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Repository that handles VoUser objects.
 */
@Singleton
public class UserRepository {

    private ApigrpcFascade apigrpcFascade;
    private ApirestFascade apirestFascade;
    private final AppExecutors appExecutors;
    private RoomFascade roomFascade;

    @Inject
    public UserRepository(ApirestFascade apirestFascade, ApigrpcFascade apigrpcFascade, RoomFascade roomFascade) {
        this.roomFascade = roomFascade;
        this.apigrpcFascade = apigrpcFascade;
        this.apirestFascade = apirestFascade;
        this.appExecutors = new AppExecutors();
    }

    public LiveData<Resource<User>> loadByLogin(final String login) {
        return new NetworkRestBoundResource<User, GitUser>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull GitUser item) {
                item.setCreated(System.currentTimeMillis());
                Timber.e("UserRepository saveCallResult:%s login=%s  " +
                                "\n  reposUrl=%s name=%s " +
                                "\navatarUrl=%s company=%s" +
                                "\n url=%s",
                        item.toString(), item.getLogin(),
                        item.getReposUrl(), item.getName(),
                        item.getAvatarUrl(), item.getCompany(),
                        item.getUrl());

                User user = User.builder().setLogin(item.getLogin()).setName(item.getName()).build();
                roomFascade.userDao.save(user);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                Timber.e("shouldFetch login=%s, data=%s", login, data);
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                LiveData<User> result = roomFascade.userDao.load(login);

                Timber.e("loadFromDb login=%s result=%s", login, result);

                return result;
            }

            @Override
            protected void onFetchFailed() {
                Timber.e("onFetchFailed apirestFascade.getVoUser, login=%s", login);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<GitUser>> createCall() {
                Timber.e("createCall apirestFascade.getVoUser, login=%s", login);

                return apirestFascade.getGithubService().getUser(login);
            }
        }.asLiveData();
    }

    public LiveData<User> findByLogin(String login) {
        return roomFascade.userDao.load(login);
    }

    public Observable<GitUser> getRxUser(String login) {
        return apirestFascade.getRxGithubService().getUser(login);
    }
}