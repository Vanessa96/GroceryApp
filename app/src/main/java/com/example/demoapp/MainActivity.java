package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.model.ProductModel;
import com.example.demoapp.model.StoreModel;
import com.example.demoapp.util.CommonConstantant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends BaseActivity implements OnClickListener,
		OnItemSelectedListener {

	private Spinner mSpLocation;
	private Spinner mSpArea;
	private Spinner mSpStore;
	private RadioButton mRbUser;
	private RadioButton mRbStore;
	private Button mBtnSearch;
	private Button mBtnAddStore;
	private DBHelper dbHelper;
	private List<String> locationList;
	private List<String> areaList;
	private List<String> shopList;
	private List<StoreModel> storelistAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onRegisterComponent();
		dbHelper = new DBHelper(this);

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
		getListOfStore();
	}

	private void getListOfStore() {
		shopList = new ArrayList<String>();
		storelistAll = new ArrayList<StoreModel>();
		String query = "select * from STORE where location='"
				+ mSpLocation.getSelectedItem().toString() + "' and area='"
				+ mSpArea.getSelectedItem().toString() + "'";
		Cursor mCursor = dbHelper.getAllDataFromQuery(query);

		if (mCursor != null && mCursor.moveToFirst()) {
			do {
				String shop = mCursor.getString(mCursor
						.getColumnIndexOrThrow("store_name"));
				String shopId = mCursor.getString(mCursor
						.getColumnIndexOrThrow("store_id"));
				shopList.add(shop);
				StoreModel model = new StoreModel();
				model.setStoreId(shopId);
				model.setStoreName(shop);
				storelistAll.add(model);
			} while (mCursor.moveToNext());
		}
		CommonConstantant.storeList = shopList;

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, shopList);
		mSpStore.setAdapter(adapter);
	}

	private void onRegisterComponent() {
		mSpLocation = (Spinner) findViewById(R.id.sp_location);
		mSpArea = (Spinner) findViewById(R.id.sp_area);
		mSpStore = (Spinner) findViewById(R.id.sp_shop);
		mRbUser = (RadioButton) findViewById(R.id.rb_user);
		mRbStore = (RadioButton) findViewById(R.id.rb_store);
		mBtnSearch = (Button) findViewById(R.id.btn_search);
		mBtnAddStore = (Button) findViewById(R.id.btn_add_store);
		mRbUser.setChecked(true);
		mRbUser.setOnClickListener(this);
		mRbStore.setOnClickListener(this);
		mBtnAddStore.setOnClickListener(this);
		mBtnSearch.setOnClickListener(this);
		mSpLocation.setOnItemSelectedListener(this);
		mSpArea.setOnItemSelectedListener(this);
		mBtnAddStore.setVisibility(View.GONE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getListOfLocation();
		if (mRbUser.isChecked()) {
			mRbStore.setChecked(false);
			mRbUser.setChecked(true);
			mBtnAddStore.setVisibility(View.GONE);
			mBtnSearch.setText("Search");
		} else {
			mRbStore.setChecked(true);
			mRbUser.setChecked(false);
			mBtnAddStore.setVisibility(View.VISIBLE);
			mBtnSearch.setText("Login");
		}

	}

	@Override
	public void onClick(View view) {
		if (storelistAll.size() > 0)
			CommonConstantant.STORE_ID = ""
					+ storelistAll.get(mSpStore.getSelectedItemPosition()).getStoreId();
		switch (view.getId()) {
		case R.id.rb_user:
			mRbStore.setChecked(false);
			mRbUser.setChecked(true);
			mBtnAddStore.setVisibility(View.GONE);
			mBtnSearch.setText("Search");
			break;
		case R.id.rb_store:
			mRbStore.setChecked(true);
			mRbUser.setChecked(false);
			mBtnAddStore.setVisibility(View.VISIBLE);
			mBtnSearch.setText("Login");
			break;
		case R.id.btn_search:
			if (mRbUser.isChecked()) {
				startActivity(new Intent(this, AddToCartActivity.class));
			} else if (mRbStore.isChecked()) {
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;
		case R.id.btn_add_store:
			startActivity(new Intent(this, AddShopActivity.class));
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.sp_location:
			getListOfArea();
			break;
		case R.id.sp_area:
			getListOfStore();
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
