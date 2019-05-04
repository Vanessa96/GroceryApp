package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.model.ProductModel;
import com.example.demoapp.util.CommonConstantant;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class AddUpdateDeleteActivity extends BaseActivity implements
		OnClickListener {

	private EditText mEtProductName;
	private EditText mEtProductPrice;
	private ImageView mIvUploadImage;
	private Button mBtnAddUpdateDelete;
	private RelativeLayout mRlSelect;
	private LinearLayout mLLAdd;
	private Spinner mSpSelect;
	private List<String> prodList;
	private List<ProductModel> productList;
	private DBHelper dbHelper;
	String prod_Id = "";
	String action = "";
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_update_delete);
		dbHelper = new DBHelper(this);
		intent = getIntent();
		action = intent.getStringExtra("action");
		onRegisterCompponent();
		setDataOnUi();
		getListOfProduct();
	}

	private void setDataOnUi() {

		if (action != null && action.equalsIgnoreCase("add")) {
			mBtnAddUpdateDelete.setText("Add");
			mRlSelect.setVisibility(View.GONE);
			mLLAdd.setVisibility(View.VISIBLE);
		} else if (action != null && action.equalsIgnoreCase("update")) {
			mBtnAddUpdateDelete.setText("Update");
			mRlSelect.setVisibility(View.VISIBLE);
			mLLAdd.setVisibility(View.GONE);
		} else if (action != null && action.equalsIgnoreCase("delete")) {
			mBtnAddUpdateDelete.setText("Delete");
			mLLAdd.setVisibility(View.GONE);
			mRlSelect.setVisibility(View.VISIBLE);
			mEtProductName.setEnabled(false);
			mEtProductPrice.setEnabled(false);
		}

	}

	private void onRegisterCompponent() {

		mEtProductName = (EditText) findViewById(R.id.et_name);
		mEtProductPrice = (EditText) findViewById(R.id.edt_prise);
		mIvUploadImage = (ImageView) findViewById(R.id.img);
		mBtnAddUpdateDelete = (Button) findViewById(R.id.btn_add_update_delete);
		mRlSelect = (RelativeLayout) findViewById(R.id.rl_select);
		mSpSelect = (Spinner) findViewById(R.id.sp_select);
		mLLAdd = (LinearLayout) findViewById(R.id.llAdd);
		mBtnAddUpdateDelete.setOnClickListener(this);
		mIvUploadImage.setOnClickListener(this);
		mSpSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 > 0) {
					mLLAdd.setVisibility(View.VISIBLE);
					mEtProductName.setText(productList.get(arg2)
							.getProductName());
					mEtProductPrice.setText(productList.get(arg2)
							.getProductPrice());
					prod_Id = productList.get(arg2).getProductId();
				} else {
					mLLAdd.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_add_update_delete:
			if (action != null && action.equalsIgnoreCase("add")) {
				addProduct();
			} else if (action != null && action.equalsIgnoreCase("update")) {
				updateProduct();
			} else if (action != null && action.equalsIgnoreCase("delete")) {
				deleteProduct();
			}
			break;

		case R.id.img:
			break;
		}
	}

	private void getListOfProduct() {
		prodList = new ArrayList<String>();
		productList = new ArrayList<ProductModel>();
		ProductModel model1 = new ProductModel();
		model1.setProductName("Select");
		productList.add(model1);
		prodList.add("-- Select -- ");
		Cursor mCursor = dbHelper.getAllData("PRODUCT");

		if (mCursor != null && mCursor.moveToFirst()) {
			do {

				ProductModel model = new ProductModel();
				String prodName = mCursor.getString(mCursor
						.getColumnIndexOrThrow("product_name"));
				String prodPrice = mCursor.getString(mCursor
						.getColumnIndexOrThrow("product_prise"));
				String prodId = mCursor.getString(mCursor
						.getColumnIndexOrThrow("product_id"));
				String storeId = mCursor.getString(mCursor
						.getColumnIndexOrThrow("store_id"));
				prodList.add(prodName);
				model.setProductName(prodName);
				model.setProductPrice(prodPrice);
				model.setProductQuntity("1");
				model.setProductId(prodId);
				model.setStoreId(storeId);
				productList.add(model);
			} while (mCursor.moveToNext());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, prodList);
		mSpSelect.setAdapter(adapter);

	}

	private void addProduct() {
		String prod_name = mEtProductName.getText().toString();
		String prod_price = mEtProductPrice.getText().toString();

		if (prod_name.isEmpty()) {
			Toast.makeText(this, "Please enter product name", Toast.LENGTH_LONG)
					.show();
		} else if (prod_price.isEmpty()) {
			Toast.makeText(this, "Please enter product price",
					Toast.LENGTH_LONG).show();
		} else {
			ContentValues values = new ContentValues();
			values.put("product_name", prod_name);
			values.put("product_prise", prod_price);
			values.put("store_id", CommonConstantant.STORE_ID);
			dbHelper.insertData("PRODUCT", values);
			Toast.makeText(this, "Product data insert sucessfully",
					Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private void updateProduct() {
		String prod_name = mEtProductName.getText().toString();
		String prod_price = mEtProductPrice.getText().toString();

		if (prod_name.isEmpty()) {
			Toast.makeText(this, "Please enter product name", Toast.LENGTH_LONG)
					.show();
		} else if (prod_price.isEmpty()) {
			Toast.makeText(this, "Please enter product price",
					Toast.LENGTH_LONG).show();
		} else {

			ContentValues values = new ContentValues();
			values.put("product_name", prod_name);
			values.put("product_prise", prod_price);
			values.put("store_id", CommonConstantant.STORE_ID);
			dbHelper.updateData("PRODUCT", "product_id='" + prod_Id + "'",
					values);
			Toast.makeText(this, "Product data update sucessfully",
					Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private void deleteProduct() {
		String prod_name = mEtProductName.getText().toString();
		String prod_price = mEtProductPrice.getText().toString();

		if (prod_name.isEmpty()) {
			Toast.makeText(this, "Please enter product name", Toast.LENGTH_LONG)
					.show();
		} else if (prod_price.isEmpty()) {
			Toast.makeText(this, "Please enter product price",
					Toast.LENGTH_LONG).show();
		} else {

			dbHelper.deleteData("PRODUCT", "product_id='" + prod_Id + "'");
			Toast.makeText(this, "Product data delete sucessfully",
					Toast.LENGTH_LONG).show();
			finish();
		}

	}
}
