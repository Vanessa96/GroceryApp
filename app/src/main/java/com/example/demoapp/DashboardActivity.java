package com.example.demoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashboardActivity extends BaseActivity implements OnClickListener {

	private Button mBtnAdd;
	private Button mBtnUpdate;
	private Button mBtnDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		onRegisterComponent();
	}

	private void onRegisterComponent() {
		mBtnAdd = (Button) findViewById(R.id.btn_add);
		mBtnUpdate = (Button) findViewById(R.id.btn_update);
		mBtnDelete = (Button) findViewById(R.id.btn_delete);
		
		mBtnAdd.setOnClickListener(this);
		mBtnUpdate.setOnClickListener(this);
		mBtnDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(this,AddUpdateDeleteActivity.class); 
		
		switch(view.getId()){
		case R.id.btn_add:
			intent.putExtra("action", "add");
			startActivity(intent);
			break;
		case R.id.btn_update:
			intent.putExtra("action", "update");
			startActivity(intent);
			break;
		case R.id.btn_delete:
			intent.putExtra("action", "delete");
			startActivity(intent);
			break;
		}
		
	}

}
