package org.ditto.keyboard.dbroom.user;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    Long save(User user);


    @Query("SELECT * FROM user WHERE login = :login")
    LiveData<User> load(String login);


}