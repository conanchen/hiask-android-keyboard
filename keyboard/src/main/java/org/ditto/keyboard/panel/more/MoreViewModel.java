package org.ditto.keyboard.panel.more;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import org.ditto.keyboard.usecase.UsecaseFascade;

import javax.inject.Inject;

public class MoreViewModel extends ViewModel {
    private final static String TAG = "MoreViewModel";
    @VisibleForTesting
    final MutableLiveData<String> login = new MutableLiveData<>();

    @Inject
    UsecaseFascade mUsecaseFascade;

    @SuppressWarnings("unchecked")
    @Inject
    public MoreViewModel() {
    }

    public void refresh() {
        this.login.setValue("GO");
    }
}