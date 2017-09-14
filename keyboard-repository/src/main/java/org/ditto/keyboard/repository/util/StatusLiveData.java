package org.ditto.keyboard.repository.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;


public final class StatusLiveData extends LiveData {
    private final static String TAG = "ErrorLiveData";
    private final AtomicBoolean pending;


    public final void setStatus(final Status value) {
        checkNotNull(value);
        Observable.just(true)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    StatusLiveData.this.pending.set(true);
                    StatusLiveData.super.setValue(value);
                });
    }

    public void observe(LifecycleOwner owner, final Observer observer) {
        checkNotNull(owner);
        checkNotNull(observer);
        super.observe(owner, (new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((Status) var1);
            }

            public final void onChanged(@Nullable Status value) {
                if (StatusLiveData.this.pending.compareAndSet(true, false)) {
                    observer.onChanged(value);
                }
            }
        }));
    }


    public StatusLiveData() {
        super();
        this.pending = new AtomicBoolean(false);
    }
}
