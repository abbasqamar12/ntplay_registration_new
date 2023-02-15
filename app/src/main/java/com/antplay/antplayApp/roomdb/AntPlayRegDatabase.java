package com.antplay.antplayApp.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,ComiconUser.class}, version = 1)
public abstract class AntPlayRegDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ComiconUserDao comiconUserDao();
    public abstract UserComiconUserDao userComiconUserDao();

}
