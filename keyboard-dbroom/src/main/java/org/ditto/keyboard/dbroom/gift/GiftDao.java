package org.ditto.keyboard.dbroom.gift;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.ditto.keyboard.dbroom.gif.Gifgroup;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by admin on 2017/7/28.
 */
@Dao
public interface GiftDao {

    @Insert(onConflict = REPLACE)
    Long save(Gift gift);


    @Insert(onConflict = REPLACE)
    List<Long> saveAllGifts(List<Gift> gifts);


    @Insert(onConflict = REPLACE)
    List<Long> saveAllGroups(List<Giftgroup> groups);


    @Query("SELECT * FROM Gift order by price ASC  LIMIT :size")
    LiveData<List<Gift>> listGiftsBy(int size);

    @Query("SELECT * FROM Giftgroup order by sequence ASC  LIMIT :size")
    LiveData<List<Giftgroup>> listGroupsBy(int size);


}
