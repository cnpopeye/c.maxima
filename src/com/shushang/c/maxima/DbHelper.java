package com.shushang.c.maxima;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
	static final String TAG="DbHelper";
	static final String DB_NAME="test.db";
	static final int DB_VERSION=1;
	static final String TABLE="test";
	static final String C_ID=BaseColumns._ID;
	static final String C_CREATED_AT="created_at";
	static final String C_TEXT="text";
	static final String C_USER="user";
	Context  context;
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table "+TABLE+" ( " + C_ID + " int primary key, " 
				+ C_CREATED_AT + " int , "+ C_TEXT + " text, " + C_USER +" text )";
		db.execSQL(sql);
		Log.d(TAG,"onCreated sql:"+sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists "+TABLE);
		Log.d(TAG,"OnUpgraded");
		onCreate(db);
	}

}
