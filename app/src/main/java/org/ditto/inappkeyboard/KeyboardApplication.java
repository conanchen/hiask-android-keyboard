package org.ditto.inappkeyboard;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.github.anrwatchdog.ANRWatchDog;

import org.ditto.keyboard.di.KeyboardInjector;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


/**
 * Created by admin on 2017/5/3.
 */

public class KeyboardApplication extends MultiDexApplication {
    public static final String TAG = "HiaskApp";


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
            new ANRWatchDog().start();
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            initStethoDebugBridge();
        }

        KeyboardInjector.init(this);

        /*
        * 查看结果
        * 严格模式有很多种报告违例的形式，但是想要分析具体违例情况，
        * 还是需要查看日志，终端下过滤StrictMode就能得到违例的具体stacktrace信息。
        *
        * adb logcat | grep StrictMode
        */
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }


    private void initStethoDebugBridge() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (BuildConfig.DEBUG) {
//            Stetho.initialize(
//                    Stetho.newInitializerBuilder(this)
//                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                            //.enableWebKitInspector(new CouchbaseInspectorModulesProvider.Builder(this).build())
//                            .build());
            Stetho.initializeWithDefaults(this);
        }
    }

}
