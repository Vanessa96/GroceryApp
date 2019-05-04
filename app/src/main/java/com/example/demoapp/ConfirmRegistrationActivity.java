package com.example.demoapp;

import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.util.CommonConstantant;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmRegistrationActivity extends BaseActivity implements
		OnClickListener {

	private EditText mEtUserName;
	private EditText mEtPassword;
	private EditText mEtConfirmPassword;
	private Button mBtnLogin;
	private DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_registration);
		dbHelper = new DBHelper(this);
		onRegisterComponent();

	}

	private void onRegisterComponent() {

		mEtUserName = (EditText) findViewById(R.id.edtUserName);
		mEtPassword = (EditText) findViewById(R.id.edtPassword);
		mEtConfirmPassword = (EditText) findViewById(R.id.edtConfirm);
		mBtnLogin = (Button) findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		String user_name = mEtUserName.getText().toString();
		String password = mEtPassword.getText().toString();
		String confirmPassword = mEtConfirmPassword.getText().toString();

		if (user_name.isEmpty()) {
			Toast.makeText(this, "Please enter user name", Toast.LENGTH_LONG)
					.show();
		} else if (password.isEmpty()) {
			Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG)
					.show();
		} else if (confirmPassword.isEmpty()) {
			Toast.makeText(this, "Please enter confirm password",
					Toast.LENGTH_LONG).show();
		} else if (!password.equalsIgnoreCase(confirmPassword)) {
			Toast.makeText(this,
					"Password and confirm password dose not match.",
					Toast.LENGTH_LONG).show();
		} else {
			long count = 0;
			count = dbHelper.insertData("STORE", CommonConstantant.values);
			ContentValues loginValues = new ContentValues();
			loginValues.put("login_id", user_name);
			loginValues.put("password", password);
			loginValues.put("store_id", dbHelper.getLastStoreId());
			dbHelper.insertData("LOGIN", loginValues);
			
			if (count > 0) {
				Toast.makeText(this, "Store added sucessfully",
						Toast.LENGTH_LONG).show();
				finish();
			}

		}
	}

}
