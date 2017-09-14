package org.ditto.keyboard.apigrpc;


import javax.inject.Inject;

/**
 * Created by amirziarati on 10/4/16.
 */
public class ApigrpcFascade {


   public EmojiService emojiService;
     public RouteGuideService routeGuideService;

    @Inject
    public ApigrpcFascade(
            RouteGuideService routeGuideService,
                          EmojiService emojiService
    ) {
        this.routeGuideService = routeGuideService;
        this.emojiService = emojiService;
    }



}