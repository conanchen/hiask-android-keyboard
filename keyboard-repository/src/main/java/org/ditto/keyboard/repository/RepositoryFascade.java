package org.ditto.keyboard.repository;


import org.ditto.keyboard.apirest.data.GitUser;
import org.ditto.keyboard.dbroom.emoji.Emoji;

import io.reactivex.Observable;

import javax.inject.Inject;

/**
 * Created by amirziarati on 10/4/16.
 */
public class RepositoryFascade {

    final public RouteGuideRepository routeGuideRepository;
    final public UserRepository userRepository;
    final public PhotoRepository photoRepository;
    final public EmojiRepository emojiRepository;
    final public GifRepository gifRepository;
    final public GiftRepository giftRepository;

    @Inject
    public RepositoryFascade(
            RouteGuideRepository routeGuideRepository,
            UserRepository userRepository,
            PhotoRepository photoRepository,
            EmojiRepository emojiRepository,
            GifRepository gifRepository,
            GiftRepository giftRepository) {
        this.routeGuideRepository = routeGuideRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.emojiRepository = emojiRepository;
        this.gifRepository = gifRepository;
        this.giftRepository = giftRepository;
    }
}