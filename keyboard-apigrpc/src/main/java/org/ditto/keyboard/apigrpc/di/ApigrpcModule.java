package org.ditto.keyboard.apigrpc.di;

import org.ditto.keyboard.apigrpc.ApigrpcFascade;
import org.ditto.keyboard.apigrpc.EmojiService;
import org.ditto.keyboard.apigrpc.RouteGuideService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amirziarati on 10/4/16.
 */
@Singleton
@Module
public class ApigrpcModule {


    @Singleton
    @Provides
    ApigrpcFascade provideApirestFascade(
            EmojiService emojiService,
            RouteGuideService routeGuideService
    ) {
        return new ApigrpcFascade(
                routeGuideService,
                emojiService);
    }


    @Singleton
    @Provides
    EmojiService provideEmojiService() {
        return new EmojiService();
    }

    @Singleton
    @Provides
    RouteGuideService provideRouteGuideService() {
        return new RouteGuideService();
    }
}