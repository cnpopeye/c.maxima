package com.shushang.c.maxima;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	static final String TAG="UpdaterService";
	DbHelper dbHelper;
	SQLiteDatabase db;
	
	static final int DELAY = 6000;
	private boolean runflag = false;
	private Updater updater ;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.updater = new Updater();
		dbHelper = new DbHelper(this);
		Log.d(TAG,"OnCreated");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onStartCommand");
		this.runflag = true;
		this.updater.start();
		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.runflag = false;
		this.updater.interrupt();
		this.updater = null;
		Log.d(TAG,"onDestroyed");
	}
	
	// 服务启动单独线程
	private class Updater extends Thread {
		int cnt = 0;
		
		public Updater(){
			super("UpdaterService-Updater");
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			UpdaterService updaterService = UpdaterService.this;
			db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			while (updaterService.runflag){
				Log.d(TAG,"Updater running...");
				try {
					cnt = cnt +1;
					values.clear();
					values.put(DbHelper.C_ID, cnt);
					values.put(DbHelper.C_CREATED_AT, 12112112);
					values.put(DbHelper.C_USER, "zz");
					values.put(DbHelper.C_TEXT, "sdfwsdfaasdf asd es wqsadvalue");
					try {
						db.insertOrThrow(DbHelper.TABLE, null, values);
					} catch (SQLException e) {
						//e.printStackTrace();
					}
					Log.d(TAG,"Updater ran.");
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					Log.e(TAG,e.toString());
					updaterService.runflag = false;
				}
			}
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			super.destroy();
			db.close();
		}
	} //Updater
	
}