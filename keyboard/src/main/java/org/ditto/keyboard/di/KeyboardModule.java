package org.ditto.keyboard.di;

import android.app.Application;
import android.content.Context;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.panel.gift.di.GiftViewModelFactory;
import org.ditto.keyboard.panel.gift.di.GiftViewModelSubComponent;
import org.ditto.keyboard.usecase.hello.Hello;
import org.ditto.keyboard.usecase.di.UsecaseModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module(includes = {
        UsecaseModule.class
})
class KeyboardModule {


    @Provides
    @Singleton
    Context providesApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    CommitKeyboardViewModelFactory  provideCommitKeyboardViewModelFactory(
            CommitKeyboardViewModelSubComponent.Builder viewModelSubComponent) {
        return new CommitKeyboardViewModelFactory(viewModelSubComponent.build());
    }

}