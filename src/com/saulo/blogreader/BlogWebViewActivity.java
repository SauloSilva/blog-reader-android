package com.saulo.blogreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.webkit.WebView;

public class BlogWebViewActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_web_view);
		
		Intent intent = getIntent();
		Uri blogUri = intent.getData();
		
		WebView webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl(blogUri.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.blog_web_view, menu);
		return true;
	}
}
