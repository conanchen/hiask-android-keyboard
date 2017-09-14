package org.ditto.keyboard.dbroom.photo;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.gift.Giftgroup;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by admin on 2017/7/28.
 */
@Dao
public interface PhotoDao {

    @Insert(onConflict = REPLACE)
    Long save(Photo gift);

    @Delete
    void delete(Photo user);

    @Query("SELECT * FROM Photo order by selectIdx ASC ")
    LiveData<List<Photo>> listLivePhotos( );

    @Query("SELECT * FROM Photo where id = :id LIMIT 1")
    Photo findPhotoBy(int id);

    @Query("SELECT * FROM Photo order by selectIdx ASC ")
    List<Photo> listPhotos( );

    @Query("SELECT COUNT(*) FROM Photo where selectIdx > 0 ")
    LiveData<Long> liveCountPhotoSelected();

    @Query("SELECT * from Photo where selectIdx >0 ")
    public Flowable<List<Photo>> listFlowableSelectedPhotos();

    @Query("Delete from Photo")
    void cleanSelectedPhotos();
}
