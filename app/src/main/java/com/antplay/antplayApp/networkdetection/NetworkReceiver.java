package com.antplay.antplayApp.networkdetection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetworkReceiver extends BroadcastReceiver {
   NetworkAvailability availability;

  public NetworkReceiver(NetworkAvailability availability){
      this.availability = availability;

   }
    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            if (isOnline(context)) {
                availability.networkStatus(true);
               // dialog(true);
                Log.d("NETWORK_STATUS",   "Connected to internet");
            } else {
                availability.networkStatus(false);
              // dialog(false);
                Log.d("NETWORK_STATUS",   "Not connected to internet");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
