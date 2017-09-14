package org.ditto.keyboard.usecase;

import android.arch.lifecycle.LiveData;


import org.ditto.keyboard.apirest.util.Resource;
import org.ditto.keyboard.dbroom.user.User;
import org.ditto.keyboard.repository.RepositoryFascade;
import org.ditto.keyboard.usecase.hello.Hello;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/6/25.
 */

public class UserUsecase {
    private RepositoryFascade repositoryFascade;
    private Hello hello;

    @Inject
    public UserUsecase(RepositoryFascade repositoryFascade,Hello hello) {
        this.repositoryFascade = repositoryFascade;
        this.hello= hello;
    }

    public LiveData<Resource<User>> loadByLogin(String login) {
        return repositoryFascade.userRepository.loadByLogin(login);
    }

    public String getHello() {
        return hello.getName();
    }
}
