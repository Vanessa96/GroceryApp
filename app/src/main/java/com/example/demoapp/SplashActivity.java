package com.example.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}

}
