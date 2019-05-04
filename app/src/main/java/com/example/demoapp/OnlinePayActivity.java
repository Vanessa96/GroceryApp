package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class OnlinePayActivity extends BaseActivity {

	private WebView mWebView;
	private Button mBtnDone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_pay);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("https://www.paypal.com/signin/?country.x=IN");
		mBtnDone = (Button) findViewById(R.id.btn_done);
		mBtnDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OnlinePayActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
	}
}
