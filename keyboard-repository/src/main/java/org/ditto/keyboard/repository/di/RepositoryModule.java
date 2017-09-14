package org.ditto.keyboard.repository.di;


import android.content.Context;

import org.ditto.keyboard.apigrpc.ApigrpcFascade;
import org.ditto.keyboard.apigrpc.RouteGuideService;
import org.ditto.keyboard.apigrpc.di.ApigrpcModule;
import org.ditto.keyboard.apirest.ApirestFascade;
import org.ditto.keyboard.apirest.di.ApirestModule;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.di.RoomModule;
import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.repository.EmojiRepository;
import org.ditto.keyboard.repository.GifRepository;
import org.ditto.keyboard.repository.GiftRepository;
import org.ditto.keyboard.repository.PhotoContentQuery;
import org.ditto.keyboard.repository.PhotoRepository;
import org.ditto.keyboard.repository.RepositoryFascade;
import org.ditto.keyboard.repository.RouteGuideRepository;
import org.ditto.keyboard.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amirziarati on 10/4/16.
 */
@Singleton
@Module(includes = {
        RoomModule.class,
        ApigrpcModule.class,
        ApirestModule.class
})
public class RepositoryModule {

    @Singleton
    @Provides
    public RepositoryFascade provideRepositoryFascade(
            RouteGuideRepository routeGuideRepository,
            UserRepository userRepository,
            PhotoRepository photoRepository,
            EmojiRepository emojiRepository,
            GifRepository gifRepository,
            GiftRepository giftRepository
    ) {
        return new RepositoryFascade(
                routeGuideRepository,
                userRepository,
                photoRepository,
                emojiRepository,
                gifRepository,
                giftRepository);
    }

    @Singleton
    @Provides
    public RouteGuideRepository provideRouteGuideRepository(ApirestFascade apirestFascade, ApigrpcFascade apigrpcFascade, RoomFascade roomFascade) {
        return new RouteGuideRepository(apirestFascade,apigrpcFascade, roomFascade);
    }


    @Singleton
    @Provides
    public UserRepository provideUserRepository(ApirestFascade apirestFascade, ApigrpcFascade apigrpcFascade, RoomFascade roomFascade) {
        return new UserRepository(apirestFascade,apigrpcFascade, roomFascade);
    }


    @Singleton
    @Provides
    public GiftRepository provideGiftRepository(ApirestFascade apirestFascade, RoomFascade roomFascade) {
        return new GiftRepository(apirestFascade, roomFascade);
    }

    @Singleton
    @Provides
    PhotoContentQuery providePhotoContentQuery(Context context) {
        return new PhotoContentQuery(context);
    }



    @Singleton
    @Provides
    public GifRepository provideGifRepository(ApirestFascade apirestFascade, RoomFascade roomFascade) {
        return new GifRepository(apirestFascade, roomFascade);
    }
    @Singleton
    @Provides
    public EmojiRepository provideEmojiRepository(ApirestFascade apirestFascade, ApigrpcFascade apigrpcFascade, RoomFascade roomFascade) {
        return new EmojiRepository(apirestFascade,apigrpcFascade, roomFascade);
    }
    @Singleton
    @Provides
    public PhotoRepository providePhotoRepository(ApirestFascade apirestFascade, RoomFascade roomFascade, PhotoContentQuery photoContentQuery) {
        return new PhotoRepository(apirestFascade, roomFascade,photoContentQuery);
    }
}