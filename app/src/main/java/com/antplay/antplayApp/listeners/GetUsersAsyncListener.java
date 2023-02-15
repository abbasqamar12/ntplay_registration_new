package com.antplay.antplayApp.listeners;

import com.antplay.antplayApp.roomdb.ComiconUser;
import com.antplay.antplayApp.roomdb.User;

import java.util.List;

public interface GetUsersAsyncListener {
    void userListUpdated(List<ComiconUser> users);
}
