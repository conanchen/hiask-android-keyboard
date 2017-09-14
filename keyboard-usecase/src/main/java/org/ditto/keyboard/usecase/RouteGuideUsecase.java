package org.ditto.keyboard.usecase;

import android.arch.lifecycle.LiveData;
import android.support.v4.util.Pair;

import org.ditto.keyboard.apirest.util.Resource;
import org.ditto.keyboard.dbroom.user.User;
import org.ditto.keyboard.repository.RepositoryFascade;
import org.ditto.keyboard.usecase.hello.Hello;

import javax.inject.Inject;

import io.grpc.StatusRuntimeException;

/**
 * Created by admin on 2017/6/25.
 */

public class RouteGuideUsecase {
    private RepositoryFascade repositoryFascade;

    @Inject
    public RouteGuideUsecase(RepositoryFascade repositoryFascade) {
        this.repositoryFascade = repositoryFascade;
    }

    public LiveData<Pair<String, StatusRuntimeException>> getFeature(int lat, int lon) {
        return repositoryFascade.routeGuideRepository.getFeature(lat, lon);
    }

}
