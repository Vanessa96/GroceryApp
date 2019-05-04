package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.util.CommonConstantant;

public class AddShopActivity extends BaseActivity implements OnClickListener {

	private Spinner mSpLocation;
	private Spinner mSpArea;
	private EditText mEtName;
	private EditText mEtMobile;
	private EditText mEtEmail;
	private Button mBtnConfirm;
	private DBHelper dbHelper;
	private List<String> locationList;
	private List<String> areaList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_shop);
		dbHelper = new DBHelper(this);
		onRegisterComponent();
		setDataOnUi();
		getListOfLocation();
		getListOfArea();
	}

	private void setDataOnUi() {

	}

	private void onRegisterComponent() {
		mSpLocation = (Spinner) findViewById(R.id.sp_location);
		mSpArea = (Spinner) findViewById(R.id.sp_area);
		mEtName = (EditText) findViewById(R.id.et_name);
		mEtMobile = (EditText) findViewById(R.id.et_mobile);
		mEtEmail = (EditText) findViewById(R.id.et_email);
		mBtnConfirm = (Button) findViewById(R.id.btn_add_store);
		mBtnConfirm.setOnClickListener(this);
		mSpLocation.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getListOfArea();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		;

	}

	private void getListOfLocation() {
		locationList = new ArrayList<String>();
		Cursor mCursor = dbHelper.getAllData("LOCATION");

		if (mCursor != null && mCursor.moveToFirst()) {
			do {
				String location = mCursor.getString(mCursor
						.getColumnIndexOrThrow("location"));
				locationList.add(location);
			} while (mCursor.moveToNext());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, locationList);
		mSpLocation.setAdapter(adapter);

		getListOfArea();
	}

	private void getListOfArea() {
		areaList = new ArrayList<String>();
		String query = "select * from AREA where location='"
				+ mSpLocation.getSelectedItem().toString() + "'";
		Cursor mCursor = dbHelper.getAllDataFromQuery(query);

		if (mCursor != null && mCursor.moveToFirst()) {
			do {
				String location = mCursor.getString(mCursor
						.getColumnIndexOrThrow("area"));
				areaList.add(location);
			} while (mCursor.moveToNext());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, areaList);
		mSpArea.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_store:
			String name = mEtName.getText().toString();
			String mobile = mEtMobile.getText().toString();
			String email = mEtEmail.getText().toString();
			if (name.isEmpty()) {
				Toast.makeText(this, "Please enter shop name",
						Toast.LENGTH_LONG).show();
			} else if (mobile.isEmpty() && mobile.length() != 10) {
				Toast.makeText(this, "Please enter valid mobile no",
						Toast.LENGTH_LONG).show();
			} else if (email.isEmpty()) {
				Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG)
						.show();
			} else {
				// long count = 0;
				CommonConstantant.values = new ContentValues();
				CommonConstantant.values.put("store_name", name);
				CommonConstantant.values.put("location", mSpLocation
						.getSelectedItem().toString());
				CommonConstantant.values.put("area", mSpArea.getSelectedItem()
						.toString());
				CommonConstantant.values.put("mobile", mobile);
				CommonConstantant.values.put("email", email);
				//
				// count = dbHelper.insertData("STORE", values);
				// if (count > 0) {
				// Toast.makeText(this, "Store added sucessfully",
				// Toast.LENGTH_LONG).show();
				// finish();
				// }
				startActivity(new Intent(this,
						ConfirmRegistrationActivity.class));
				finish();
			}
			break;
		}
	}
}
