package org.ditto.keyboard.dbroom.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.ditto.keyboard.dbroom.KeyboardRoomDatabase;
import org.ditto.keyboard.dbroom.RoomFascade;
import org.ditto.keyboard.dbroom.emoji.EmojiDao;
import org.ditto.keyboard.dbroom.gif.GifDao;
import org.ditto.keyboard.dbroom.gift.GiftDao;
import org.ditto.keyboard.dbroom.photo.PhotoDao;
import org.ditto.keyboard.dbroom.sync.SynchistoryDao;
import org.ditto.keyboard.dbroom.user.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amirziarati on 10/4/16.
 */
@Singleton
@Module
public class RoomModule {

    @Singleton
    @Provides
    KeyboardRoomDatabase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, KeyboardRoomDatabase.class, "keyboard.db").build();
    }

    @Singleton
    @Provides
    SynchistoryDao provideDaoSynchistory(KeyboardRoomDatabase db) {
        return db.daoSynchistory();
    }


    @Singleton
    @Provides
    EmojiDao provideDaoEmoji(KeyboardRoomDatabase db) {
        return db.daoEmoji();
    }


    @Singleton
    @Provides
    GifDao provideDaoGif(KeyboardRoomDatabase db) {
        return db.daoGif();
    }


    @Singleton
    @Provides
    GiftDao provideDaoGift(KeyboardRoomDatabase db) {
        return db.daoGift();
    }


    @Singleton
    @Provides
    UserDao provideDaoUser(KeyboardRoomDatabase db) {
        return db.daoUser();
    }


    @Singleton
    @Provides
    PhotoDao providePhotoDao(KeyboardRoomDatabase db) {
        return db.daoPhoto();
    }



    @Singleton
    @Provides
    public RoomFascade provideRoomFascade(
            SynchistoryDao synchistoryDao,
            EmojiDao emojiDao,
            GiftDao giftDao,
            GifDao gifDao,
            PhotoDao photoDao,
            UserDao userDao
    ) {
        return new RoomFascade(synchistoryDao,emojiDao,giftDao,gifDao, photoDao, userDao);
    }
}