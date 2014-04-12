package com.saulo.blogreader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainListActivity extends ListActivity{
	protected String[] mBlogPostTitle;
	public static final int NUMBER_OF_POSTS = 20;
	public static final String TAG = MainListActivity.class.getSimpleName();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_list);
		GetBlogPostTask getBlogPostTask = new GetBlogPostTask();
		getBlogPostTask.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
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
				Log.i(TAG, "Code " + responseCode);
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