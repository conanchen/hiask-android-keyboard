package org.ditto.keyboard.panel.frequent;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pair;

import org.ditto.keyboard.apirest.util.AbsentLiveData;
import org.ditto.keyboard.usecase.UsecaseFascade;

import javax.inject.Inject;

import io.grpc.StatusRuntimeException;

public class FrequentViewModel extends ViewModel {
    private final static String TAG = "TestViewModel";
    @VisibleForTesting
    final MutableLiveData<Pair<Integer,Integer>> location = new MutableLiveData<>();
    private final LiveData<Pair<String,StatusRuntimeException>> grpcfeature;

    @Inject
    UsecaseFascade mUsecaseFascade;

    @SuppressWarnings("unchecked")
    @Inject
    public FrequentViewModel() {
        grpcfeature = Transformations.switchMap(location, loc -> {
            if (loc == null) {
                return AbsentLiveData.create();
            } else {
                return mUsecaseFascade.routeGuideUsecase.getFeature(loc.first,loc.second);
            }
        });
    }


    LiveData<Pair<String,StatusRuntimeException>> getGrpcfeature() {
        return grpcfeature;
    }


    void updateCurrentLocation(Pair<Integer,Integer> loc) {
            this.location.setValue(loc);
    }
}