package org.ditto.keyboard.repository;

import android.arch.lifecycle.LiveData;
import android.support.v4.util.Pair;

import org.ditto.keyboard.apigrpc.ApigrpcFascade;
import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.dbroom.RoomFascade;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.grpc.StatusRuntimeException;

/**
 * Repository that handles VoUser objects.
 */
@Singleton
public class RouteGuideRepository {

    private ApigrpcFascade apigrpcFascade;
    private ApirestFascade apirestFascade;
    private RoomFascade roomFascade;

    @Inject
    public RouteGuideRepository(ApirestFascade apirestFascade, ApigrpcFascade apigrpcFascade, RoomFascade roomFascade) {
        this.roomFascade = roomFascade;
        this.apigrpcFascade = apigrpcFascade;
        this.apirestFascade = apirestFascade;
    }

    public LiveData<Pair<String,StatusRuntimeException>> getFeature(final int lat, final int lon) {
        return new LiveData<Pair<String,StatusRuntimeException>>() {
            {
                Pair<String,StatusRuntimeException> p = apigrpcFascade.routeGuideService.getFeature(lat, lon);
                postValue(p);
            }
        };
    }

}