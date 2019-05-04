package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.adapter.ProductAdapter;
import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.model.ProductModel;
import com.example.demoapp.util.CommonConstantant;

public class AddToCartActivity extends BaseActivity implements OnClickListener {

	private ListView mLvProduct;
	private List<ProductModel> productList;
	private List<ProductModel> cartList;
	private ProductAdapter mAdapter;
	private List<String> prodList;
	private DBHelper dbHelper;
	private Spinner mSpShop;
	private Button mBtnAddToCart;
	private Button mBtnGoToCart;
	private TextView mCartItem;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_to_cart);
		dbHelper = new DBHelper(this);
		onRegisterComponent();
		getListOfProduct();
		setDataOnUi();
	}

	private void onRegisterComponent() {
		productList = new ArrayList<ProductModel>();
		cartList = new ArrayList<ProductModel>();
		mAdapter = new ProductAdapter(this, productList);
		mSpShop = (Spinner) findViewById(R.id.sp_shop);
		mLvProduct = (ListView) findViewById(R.id.lv);
		mBtnAddToCart = (Button) findViewById(R.id.btn_add_to_cart);
		mBtnGoToCart = (Button) findViewById(R.id.btn_go_to_cart);
		mCartItem = (TextView) findViewById(R.id.tv_item);
		mLvProduct.setAdapter(mAdapter);
		mBtnAddToCart.setOnClickListener(this);
		mBtnGoToCart.setOnClickListener(this);

	}

	private void getListOfProduct() {
		Cursor mCursor = dbHelper.getAllData("PRODUCT");

		if (mCursor != null && mCursor.moveToFirst()) {
			do {
				ProductModel model = new ProductModel();
				String prodName = mCursor.getString(mCursor
						.getColumnIndexOrThrow("product_name"));
				String prodPrice = mCursor.getString(mCursor
						.getColumnIndexOrThrow("product_prise"));
				model.setProductName(prodName);
				model.setProductPrice(prodPrice);
				model.setProductQuntity("1");
				productList.add(model);
			} while (mCursor.moveToNext());
		}
		mAdapter.notifyDataSetChanged();
	}

	private void setDataOnUi() {
		if (CommonConstantant.storeList != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item,
					CommonConstantant.storeList);
			mSpShop.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_add_to_cart:
			for (int i = 0; i < productList.size(); i++) {
				if (productList.get(i).isSelect() == true) {
					count++;
					cartList.add(productList.get(i));
				}

			}

			if (count > 0) {
				mCartItem.setText("" + count);
				mCartItem.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(this, "Please select at least one product.",
						Toast.LENGTH_LONG).show();
				mCartItem.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_go_to_cart:
			if (count > 0) {
				CommonConstantant.cartList = cartList;
				startActivity(new Intent(this, CartActivity.class));
			} else {
				Toast.makeText(this, "Please first add product to cart.",
						Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
}
