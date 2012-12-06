package com.shushang.c.maxima;
 

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, 
		TextWatcher,OnSharedPreferenceChangeListener{
	private static final String TAG= "MainActivity";
	EditText edtText;
	TextView textCount;
	Button btnUpdate;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtText = (EditText)findViewById(R.id.edt_update);
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this);
		
		textCount = (TextView)findViewById(R.id.textCount);
		textCount.setText(Integer.toString(140));
		textCount.setTextColor(Color.GREEN);
		
		edtText.addTextChangedListener(this);
		
	    // Setup preferences
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    prefs.registerOnSharedPreferenceChangeListener(this);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater(); // <1>
	    inflater.inflate(R.menu.activity_main, menu); // <2>
		return true;
	}

	
	class postToUpdate extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... status) {
			// TODO Auto-generated method stub
			return status[0];
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String updates = edtText.getText().toString();
		new postToUpdate().execute(updates);
		// edtText.setText("dsafasdfdsaf");
		Log.d(TAG,"onClicked");
	}

	
	  // TextWatcher methods
	public void afterTextChanged(Editable statusText) {
		int count = 140 - statusText.length();
		textCount.setText(Integer.toString(count));
		textCount.setTextColor(Color.GREEN);
		if (count<10)
			textCount.setTextColor(Color.YELLOW);
		if (count<0)
			textCount.setTextColor(Color.RED);
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	

	  // Called when an options item is clicked
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) { // <1>
	    case R.id.menu_settings:
	      startActivity(new Intent(this, PrefsActivity.class)); // <2>
	      break;
	    case R.id.menu_start:
	    	startService(new Intent(this,UpdaterService.class));
	    	break;
	    case R.id.menu_stop:
	    	stopService(new Intent(this,UpdaterService.class));
	    	break;
	    }

	    return true; // <3>
	  }

	  
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
