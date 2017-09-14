package org.ditto.keyboard.dbroom;


import org.ditto.keyboard.dbroom.emoji.EmojiDao;
import org.ditto.keyboard.dbroom.gif.GifDao;
import org.ditto.keyboard.dbroom.gift.GiftDao;
import org.ditto.keyboard.dbroom.photo.PhotoDao;
import org.ditto.keyboard.dbroom.sync.SynchistoryDao;
import org.ditto.keyboard.dbroom.user.UserDao;

import javax.inject.Inject;

/**
 * Created by amirziarati on 10/4/16.
 */
public class RoomFascade {

    public final SynchistoryDao synchistoryDao;
    public final EmojiDao emojiDao;
    public final GiftDao giftDao;
    public final UserDao userDao;
    public final GifDao gifDao;
    public final PhotoDao photoDao;

    @Inject
    public RoomFascade(
            SynchistoryDao synchistoryDao,
            EmojiDao emojiDao,
            GiftDao giftDao,
            GifDao gifDao,
            PhotoDao photoDao,
            UserDao userDao
    ) {
        this.synchistoryDao = synchistoryDao;
        this.emojiDao = emojiDao;
        this.giftDao = giftDao;
        this.gifDao = gifDao;
        this.photoDao = photoDao;
        this.userDao = userDao;
    }
}