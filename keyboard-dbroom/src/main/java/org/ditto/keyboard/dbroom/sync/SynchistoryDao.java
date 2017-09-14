package org.ditto.keyboard.dbroom.sync;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.dbroom.emoji.Emojigroup;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by admin on 2017/7/28.
 */
@Dao
public interface SynchistoryDao {

    @Insert(onConflict = REPLACE)
    Long save(Synchistory synchistory);

    @Query("SELECT * FROM Synchistory WHERE entityClassName = :entityClassName  LIMIT 1")
    Synchistory findBy(String entityClassName);

}
