package org.ditto.keyboard.dbroom.emoji;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by admin on 2017/7/28.
 */
@Dao
public interface EmojiDao {

    @Insert(onConflict = REPLACE)
    Long save(Emoji emoji);

    @Insert(onConflict = REPLACE)
    Long save(Emojigroup group);


    @Insert(onConflict = REPLACE)
    List<Long> saveAllEmojis(List<Emoji> emojis);


    @Insert(onConflict = REPLACE)
    List<Long> saveAllGroups(List<Emojigroup> groups);


    @Query("SELECT * FROM Emoji order by sequence ASC  LIMIT :size")
    LiveData<List<Emoji>> listEmojisBy(int size);

    @Query("SELECT * FROM Emojigroup order by sequence ASC  LIMIT :size")
    LiveData<List<Emojigroup>> listGroupsBy(int size);

}
