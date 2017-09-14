package org.ditto.keyboard.usecase.di;


import android.content.Context;

import org.ditto.keyboard.repository.RepositoryFascade;
import org.ditto.keyboard.repository.di.RepositoryModule;
import org.ditto.keyboard.usecase.EmojiUsecase;
import org.ditto.keyboard.usecase.GifUsecase;
import org.ditto.keyboard.usecase.GiftUsecase;
import org.ditto.keyboard.usecase.PhotoUsecase;
import org.ditto.keyboard.usecase.RouteGuideUsecase;
import org.ditto.keyboard.usecase.UsecaseFascade;
import org.ditto.keyboard.usecase.UserUsecase;
import org.ditto.keyboard.usecase.hello.Hello;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amirziarati on 10/4/16.
 */
@Singleton
@Module(includes = {
        RepositoryModule.class
})
public class UsecaseModule {

    @Singleton
    @Provides
    public UsecaseFascade provideServiceFascade(
            RouteGuideUsecase routeGuideUsecase,
            PhotoUsecase photoUsecase,
            EmojiUsecase emojiUsecase,
            GifUsecase gifUsecase,
            GiftUsecase giftUsecase,
            UserUsecase userUsecase,
            RepositoryFascade repositoryFascade) {

        return new UsecaseFascade(
                routeGuideUsecase,
                photoUsecase,
                emojiUsecase,
                gifUsecase,
                giftUsecase,
                userUsecase,
                repositoryFascade);
    }

    @Provides
    @Singleton
    Hello providesHello(Context context) {
        return new Hello(context, "Conan Chen");
    }

    @Singleton
    @Provides
    public UserUsecase provideUserUsecase(RepositoryFascade repositoryFascade, Hello hello) {
        return new UserUsecase(repositoryFascade, hello);
    }
    @Singleton
    @Provides
    public GiftUsecase provideGiftUsecase(RepositoryFascade repositoryFascade) {
        return new GiftUsecase(repositoryFascade);
    }
    @Singleton
    @Provides
    public GifUsecase provideGifUsecase(RepositoryFascade repositoryFascade) {
        return new GifUsecase(repositoryFascade);
    }
    @Singleton
    @Provides
    public EmojiUsecase provideEmojiUsecase(RepositoryFascade repositoryFascade) {
        return new EmojiUsecase(repositoryFascade);
    }
    @Singleton
    @Provides
    public PhotoUsecase providePhotoUsecase(RepositoryFascade repositoryFascade) {
        return new PhotoUsecase(repositoryFascade);
    }
}