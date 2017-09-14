package org.ditto.keyboard.dbroom.gif;


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
public interface GifDao {

    @Insert(onConflict = REPLACE)
    Long save(Gif gif);

    @Insert(onConflict = REPLACE)
    Long save(Gifgroup group);


    @Insert(onConflict = REPLACE)
    List<Long> saveAllGifs(List<Gif> gifs);


    @Insert(onConflict = REPLACE)
    List<Long> saveAllGroups(List<Gifgroup> groups);


    @Query("SELECT * FROM Gif order by sequence ASC  LIMIT :size")
    LiveData<List<Gif>> listGifsBy(int size);

    @Query("SELECT * FROM Gifgroup order by sequence ASC  LIMIT :size")
    LiveData<List<Gifgroup>> listGroupsBy(int size);

}
