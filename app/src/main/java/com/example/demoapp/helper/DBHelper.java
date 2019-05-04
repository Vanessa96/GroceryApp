package com.example.demoapp.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "sample.db";
	String[] location = { "Mumbai", "Navi Mumbai", "Pune", "Nagpur" };
	String[] area1 = { "Thane", "Kalyan", "Andheri", "CST" };
	String[] area2 = { "Ghansoli", "Nerul", "Sea wood", "Belapur", "Kharghar",
			"Panvel" };
	String[] area3 = { "Swargate", "Shivaji Nagar", "Aundh", "Karve Nagar" };
	String[] area4 = { "Manewada", "Bardi", "Hingna", "Nandanvan" };

	private String CREATE_TABLE_PRODUCT = "create table PRODUCT (product_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name text,product_image text,product_prise text,store_id integer)";
	private String CREATE_TABLE_STORE = "create table STORE (store_id INTEGER PRIMARY KEY AUTOINCREMENT, location text,area text,store_name text,mobile text,email text)";
	private String CREATE_TABLE_LOCATION = "create table LOCATION (location_id INTEGER PRIMARY KEY AUTOINCREMENT, location text)";
	private String CREATE_TABLE_AREA = "create table AREA (area_id INTEGER PRIMARY KEY AUTOINCREMENT, area text,location text)";
	private String CREATE_TABLE_LOGIN = "create table LOGIN (login_id text, password text,store_id text)";
	private SQLiteDatabase db;

	private DBHelper instance = null;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_STORE);
		db.execSQL(CREATE_TABLE_LOCATION);
		db.execSQL(CREATE_TABLE_AREA);
		db.execSQL(CREATE_TABLE_LOGIN);
		Log.d("TAG", "Table create sucessfully");
		List<String[]> list = new ArrayList<String[]>();
		list.add(area1);
		list.add(area2);
		list.add(area3);
		list.add(area4);
		for (int i = 0; i < location.length; i++) {
			ContentValues values = new ContentValues();
			values.put("location", location[i]);
			db.insert("LOCATION", null, values);
			for (int j = 0; j < list.get(i).length; j++) {
				ContentValues values2 = new ContentValues();
				values2.put("location", location[i]);
				values2.put("area", list.get(i)[j]);
				db.insert("AREA", null, values2);

			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	public long insertData(String tableName, ContentValues values) {
		long count = 0;
		count = db.insert(tableName, null, values);
		return count;
	}

	public void updateData(String tableName, String whereClause,
			ContentValues values) {
		db.update(tableName, values, whereClause, null);
	}

	public int deleteData(String tableName, String whereClause) {
		int count = 0;
		count = db.delete(tableName, whereClause, null);
		return count;
	}

	public Cursor getAllData(String tableName) {
		String query = "select * from " + tableName;
		Cursor mCursor = db.rawQuery(query, null);
		return mCursor;
	}

	public Cursor getAllDataFromQuery(String query) {

		Cursor mCursor = db.rawQuery(query, null);
		return mCursor;
	}

	public String getLastStoreId() {
		String query = "select store_id from STORE ORDER BY store_id desc limit 1";
		Cursor mCursor = db.rawQuery(query, null);
		String id = "";
		if (mCursor != null && mCursor.moveToFirst()) {
			id = mCursor.getString(mCursor.getColumnIndexOrThrow("store_id"));
		}
		return id;
	}

	public boolean validateUserDetail(String userId, String password,
			String storeId) {
		boolean isValid = false;
		String query = "select * from LOGIN where store_id ='" + storeId
				+ "' and login_id ='" + userId + "' and password='" + password
				+ "'";
		Cursor mCursor = db.rawQuery(query, null);
		if (mCursor != null && mCursor.moveToFirst() && mCursor.getCount() > 0) {
			isValid = true;
		}
		return isValid;

	}
}
