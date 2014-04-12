package com.saulo.blogreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainListActivity extends ListActivity{
	protected String[] mBlogPostTitle;
	public static final int NUMBER_OF_POSTS = 20;
	public static final String TAG = MainListActivity.class.getSimpleName();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		
		if (isNetworkAvailable()){
			GetBlogPostTask getBlogPostTask = new GetBlogPostTask();
			getBlogPostTask.execute();
			
		} else {
			Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();	
		
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) { 
			isAvailable = true; 
		}
		
		return isAvailable;
	}
	
	private class GetBlogPostTask extends AsyncTask<Object, Void, String> {
		int responseCode = -1;
		
		@Override
		protected String doInBackground(Object... arg0) {
			try {
				URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);	
				HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
				connection.connect();

				responseCode = connection.getResponseCode();
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream inputStream = connection.getInputStream();
					Reader reader = new  InputStreamReader(inputStream);
					
					int contentLength = connection.getContentLength();
					char[] charArray = new char[contentLength];
					reader.read(charArray);
					String responseData = new String(charArray);
					
					Log.v(TAG, "JSON: " + responseData);	
				} else {
					Log.i(TAG, "Unsucessful HTTP resposnse Code: " + responseCode);
				}
				
			} catch (MalformedURLException e) {
				Log.e(TAG, "Exception caught", e);
			} catch (IOException e) {
				Log.e(TAG, "Exception caught", e);
			} catch (Exception e) {
				Log.e(TAG, "Exception caught", e);
			}
			
			return "Code " + responseCode;
		}
		
	}
}
