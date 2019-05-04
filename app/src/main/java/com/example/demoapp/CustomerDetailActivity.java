package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.util.CommonConstantant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CustomerDetailActivity extends BaseActivity implements OnClickListener {

	private Spinner mSpLocation;
	private Spinner mSpArea;
	private EditText mEtName;
	private EditText mEtMobile;
	private EditText mEtEmail;
	private Button mBtnBuyNow;
	private DBHelper dbHelper;
	private List<String> locationList;
	private List<String> areaList;
	private AlertDialog dialogPayment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_detail);
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
		mBtnBuyNow = (Button) findViewById(R.id.btn_buy_now);
		mBtnBuyNow.setOnClickListener(this);
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
		case R.id.btn_buy_now:
			String name = mEtName.getText().toString();
			String mobile = mEtMobile.getText().toString();
			String email = mEtEmail.getText().toString();
			if (name.isEmpty()) {
				Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG)
						.show();
			} else if (mobile.isEmpty() && mobile.length() != 10) {
				Toast.makeText(this, "Please enter valid mobile no",
						Toast.LENGTH_LONG).show();
			} else if (email.isEmpty()) {
				Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG)
						.show();
			} else {
				selectPaymentMethodAlert();
			}
			break;
		}
	}

	private void selectPaymentMethodAlert() {
		final CharSequence[] items = { "Cash on delivery", "Pay Online" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Payment Method");
		builder.setCancelable(false);
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch (item) {
						case 0:
							AlertDialog.Builder builder = new AlertDialog.Builder(
									CustomerDetailActivity.this);
							builder.setTitle("Thank you.");
							builder.setMessage("Your order place sucessfully.");
							builder.setCancelable(false);
							builder.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CustomerDetailActivity.this,
													MainActivity.class);
											intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
													| Intent.FLAG_ACTIVITY_CLEAR_TOP);
											startActivity(intent);
											dialog.dismiss();

										}
									});

							builder.create().show();
							break;
						case 1:
							startActivity(new Intent(
									CustomerDetailActivity.this,
									OnlinePayActivity.class));
							break;
						}
						dialogPayment.dismiss();
					}
				});

		builder.setPositiveButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialogPayment.dismiss();
					}
				});

		dialogPayment = builder.create();
		dialogPayment.show();
	}

}
