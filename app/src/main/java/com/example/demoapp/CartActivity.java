package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.adapter.CartAdapter;
import com.example.demoapp.adapter.ProductAdapter;
import com.example.demoapp.helper.DBHelper;
import com.example.demoapp.model.ProductModel;
import com.example.demoapp.util.CommonConstantant;

public class CartActivity extends BaseActivity implements OnClickListener {
	private ListView mLvProduct;
	private List<ProductModel> productList;
	private List<ProductModel> cartList;
	private CartAdapter mAdapter;
	private List<String> prodList;
	private DBHelper dbHelper;
	private Button mBtnBuyNow;
	private TextView mTvTotal;
	private int total = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		dbHelper = new DBHelper(this);
		onRegisterComponent();
		setDataOnUi();
	}

	private void onRegisterComponent() {
		productList = CommonConstantant.cartList;
		cartList = new ArrayList<ProductModel>();
		mAdapter = new CartAdapter(this, productList);
		mLvProduct = (ListView) findViewById(R.id.lv);
		mBtnBuyNow = (Button) findViewById(R.id.btn_buy_now);
		mTvTotal = (TextView) findViewById(R.id.tv_total);
		mLvProduct.setAdapter(mAdapter);
		mBtnBuyNow.setOnClickListener(this);

	}

	private void setDataOnUi() {
		for (int i = 0; i < productList.size(); i++) {
			total = total
					+ Integer.parseInt(productList.get(i).getProductPrice());
		}

		mTvTotal.setText("" + total);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_buy_now:
				startActivity(new Intent(this, CustomerDetailActivity.class));
				
			break;
		}
	}
}
