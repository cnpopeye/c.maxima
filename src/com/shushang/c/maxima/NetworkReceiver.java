package com.shushang.c.maxima;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {
	public static String TAG = "NetworkReceiver"; 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		boolean isNetworkDown = intent.getBooleanExtra(
				ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		
		if (isNetworkDown){
			Log.d(TAG, "OnReceive: NOT connected, stopping UpdaterService");
			context.stopService(new Intent (context, UpdaterService.class));
		} else{
			Log.d(TAG, "OnReceive: connected, starting UpdaterService");
			context.startService(new Intent (context, UpdaterService.class));
		}
		
	}

}
