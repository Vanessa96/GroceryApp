package com.example.demoapp.adapter;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demoapp.R;
import com.example.demoapp.model.ProductModel;

public class ProductAdapter extends BaseAdapter {
	private Context mContext;
	private List<ProductModel> productList;

	public ProductAdapter(Context context, List<ProductModel> productList) {
		this.mContext = context;
		this.productList = productList;

	}

	@Override
	public int getCount() {

		return productList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return productList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ViewHolder {
		TextView mTvProdName;
		TextView mTvProdPrise;
		CheckBox mCheckBox;
		EditText mQty;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		View rowView = arg1;
		ViewHolder holder = new ViewHolder();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.adapter_product_cell, arg2, false);
		holder.mTvProdName = (TextView) rowView.findViewById(R.id.tv_prod_name);
		holder.mTvProdPrise = (TextView) rowView
				.findViewById(R.id.tv_prod_price);
		holder.mCheckBox = (CheckBox) rowView.findViewById(R.id.cb_select);
		holder.mQty = (EditText) rowView.findViewById(R.id.et_qty);

		holder.mTvProdName.setText(productList.get(position).getProductName());
		holder.mTvProdPrise
				.setText(productList.get(position).getProductPrice());
		holder.mQty.setText(productList.get(position).getProductQuntity());

		holder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						productList.get(position).setSelect(isChecked);

					}
				});

		holder.mQty.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					productList.get(position).setProductQuntity(s.toString());
				}

			}
		});

		return rowView;
	}

}
