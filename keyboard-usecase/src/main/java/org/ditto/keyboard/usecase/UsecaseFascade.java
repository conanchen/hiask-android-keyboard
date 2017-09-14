package org.ditto.keyboard.usecase;


import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.repository.PhotoRepository;
import org.ditto.keyboard.repository.RepositoryFascade;

import javax.inject.Inject;

/**
 * Created by amirziarati on 10/4/16.
 */
public class UsecaseFascade {


    public RouteGuideUsecase routeGuideUsecase;
    public PhotoUsecase photoUsecase;
    public EmojiUsecase emojiUsecase;
    public GifUsecase gifUsecase;
    public GiftUsecase giftUsecase;
    public UserUsecase userUsecase;
    public RepositoryFascade repositoryFascade;


    @Inject
    public UsecaseFascade(
            RouteGuideUsecase routeGuideUsecase,
            PhotoUsecase photoUsecase,
            EmojiUsecase emojiUsecase,
            GifUsecase gifUsecase,
            GiftUsecase giftUsecase,
            UserUsecase userUsecase,
            RepositoryFascade repositoryFascade) {
        this.routeGuideUsecase = routeGuideUsecase;
        this.photoUsecase = photoUsecase;
        this.emojiUsecase = emojiUsecase;
        this.gifUsecase = gifUsecase;
        this.giftUsecase = giftUsecase;
        this.userUsecase = userUsecase;
        this.repositoryFascade = repositoryFascade;
    }

}