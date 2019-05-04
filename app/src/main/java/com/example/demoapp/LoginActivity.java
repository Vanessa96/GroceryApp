package com.example.demoapp;

import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.util.CommonConstantant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText mEtUserName;
	private EditText mEtPassword;
	private Button mBtnLogin;
	private DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		dbHelper = new DBHelper(this);
		onRegisterComponent();

	}

	private void onRegisterComponent() {

		mEtUserName = (EditText) findViewById(R.id.edtUserName);
		mEtPassword = (EditText) findViewById(R.id.edtPassword);
		mBtnLogin = (Button) findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		String user_name = mEtUserName.getText().toString();
		String password = mEtPassword.getText().toString();
		if (user_name.isEmpty()) {
			Toast.makeText(this, "Please enter user name", Toast.LENGTH_LONG)
					.show();
		} else if (password.isEmpty()) {
			Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG)
					.show();
		} else {
			if (dbHelper.validateUserDetail(user_name, password,
					CommonConstantant.STORE_ID)) {

				startActivity(new Intent(this, DashboardActivity.class));
				finish();
			} else
				Toast.makeText(this, "Invalid credentials !", Toast.LENGTH_LONG)
						.show();

		}
	}

}
