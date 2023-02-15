package com.antplay.antplayApp.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ComiconUserDao {
    @Query("SELECT * FROM ComiconUser")
    List<ComiconUser> getAll();

   /* @Query("SELECT * FROM ComiconUser WHERE uid IN (:userIds)")
    List<ComiconUser> loadAllByIds(int[] userIds);
*/
  /*  @Query("SELECT * FROM ComiconUser WHERE first_name LIKE :first LIMIT 1")
    User findByName(String first);
*/
    @Insert
    void insertAll(ComiconUser... users);

    @Delete
    void delete(ComiconUser user);

    @Query("DELETE FROM ComiconUser")
    void deleteAll();
}
