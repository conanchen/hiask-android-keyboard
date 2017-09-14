package org.ditto.keyboard.dbroom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverters;

import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.dbroom.emoji.EmojiDao;
import org.ditto.keyboard.dbroom.emoji.Emojigroup;
import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gif.GifDao;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.dbroom.gift.GiftDao;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.gift.Giftgroup;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.dbroom.photo.PhotoDao;
import org.ditto.keyboard.dbroom.sync.Synchistory;
import org.ditto.keyboard.dbroom.sync.SynchistoryDao;
import org.ditto.keyboard.dbroom.user.UserDao;
import org.ditto.keyboard.dbroom.user.User;


@Database(entities =
        {
                User.class,
                Emoji.class,
                Emojigroup.class,
                Gift.class,
                Giftgroup.class,
                Gif.class,
                Gifgroup.class,
                Photo.class,
                Synchistory.class,
        }, version = 1)
public abstract class KeyboardRoomDatabase extends android.arch.persistence.room.RoomDatabase {
    public abstract SynchistoryDao daoSynchistory();
    public abstract EmojiDao daoEmoji();
    public abstract GiftDao daoGift();
    public abstract GifDao daoGif();
    public abstract PhotoDao daoPhoto();
    public abstract UserDao daoUser();
}