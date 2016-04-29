package com.example.donorfinder;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SqliteDatabasehelper extends SQLiteOpenHelper{
	static String DATABASENAME = "donorSearchDb";
	
	// ==========================================================================================================================
		// REGISTRATION PAGE DATABASE
	String PROFILE_TABLE = "profileTable";
	String PROFILE_TABLE_COLUMN1 = "FIRSTNAME";
	String PROFILE_TABLE_COLUMN2 = "LASTNAME";
	String PROFILE_TABLE_COLUMN3 = "EMAIL";
	String PROFILE_TABLE_COLUMN4 = "USERNAME";
	String PROFILE_TABLE_COLUMN5 = "PASSWORD";
	String PROFILE_TABLE_COLUMN6 = "ZIPCODE";
	String PROFILE_TABLE_COLUMN7 = "PHONENUMBER";
	Context context;
	
	public SqliteDatabasehelper(Context context) {
		super(context, DATABASENAME, null, 1);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		// ==================================================================================================================
					// REGISTRATION TABLE CREATION
					String createProfileTabQuery = "create table " + PROFILE_TABLE
							+ "(" + PROFILE_TABLE_COLUMN1 + " TEXT, "
							+ PROFILE_TABLE_COLUMN2 + " TEXT, " + PROFILE_TABLE_COLUMN3
							+ " TEXT, " + PROFILE_TABLE_COLUMN4 + " TEXT,"
							+ PROFILE_TABLE_COLUMN5 + " TEXT, " + PROFILE_TABLE_COLUMN6
							+ " TEXT, " + PROFILE_TABLE_COLUMN7 + " TEXT)";
					System.out.println("PROFILE TABLE CREATEDDDDDDDDDDDDDDDDDDDDDDD");
					System.out.println("query is***********************************"
							+ createProfileTabQuery);

					Cursor cursor = db.rawQuery(createProfileTabQuery, null);

					if (cursor.moveToNext()) {
						Log.d("TABLE CREATION", "CREATED");
						Toast.makeText(context, "gottttt", Toast.LENGTH_LONG).show();
					}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("Drop Table if exists " + PROFILE_TABLE);

		String query;
		query = "DROP TABLE IF EXISTS notes";
		db.execSQL(query);
		onCreate(db);

		
	}
	
	// ==========================================================================================================================
		// REGISTRATION TABLE INSERT
		public String insertProfile(String FIRSTNAME, String LASTNAME, String EMAIL,
				String USERNAME, String PASSWORD, String Zipcode,String phoneNumber) {

			SQLiteDatabase db = getWritableDatabase();
	String response="";
			ContentValues initialValues = new ContentValues();
			initialValues.put(PROFILE_TABLE_COLUMN1, FIRSTNAME);
			initialValues.put(PROFILE_TABLE_COLUMN2, LASTNAME);
			initialValues.put(PROFILE_TABLE_COLUMN3, EMAIL);
			initialValues.put(PROFILE_TABLE_COLUMN4, USERNAME);
			initialValues.put(PROFILE_TABLE_COLUMN5, PASSWORD);
			initialValues.put(PROFILE_TABLE_COLUMN6, Zipcode);
			initialValues.put(PROFILE_TABLE_COLUMN7, phoneNumber);
			int i = (int) db.insert(PROFILE_TABLE, null, initialValues);
			System.out.println(db.update(PROFILE_TABLE, initialValues, null, null));

			Log.d("Values",
					"" + i + ""
							+ db.update(PROFILE_TABLE, initialValues, null, null));

			if (i > 0) {
				Log.d("Registration values", "Inserted");
				response="success";
			} else {
				Log.d("Registration", "Not Done");
				response="unsuccess";
			}
			return response;

		}
		
		
		
		//Profile Update
		public String updateProfile(String FIRSTNAME, String LASTNAME, String EMAIL,
				String USERNAME, String Zipcode,String phoneNumber) {

			SQLiteDatabase db = getWritableDatabase();
	String response="";
			ContentValues initialValues = new ContentValues();
			initialValues.put(PROFILE_TABLE_COLUMN1, FIRSTNAME);
			initialValues.put(PROFILE_TABLE_COLUMN2, LASTNAME);
			initialValues.put(PROFILE_TABLE_COLUMN3, EMAIL);
			initialValues.put(PROFILE_TABLE_COLUMN4, USERNAME);
			initialValues.put(PROFILE_TABLE_COLUMN6, Zipcode);
			initialValues.put(PROFILE_TABLE_COLUMN7, phoneNumber);
		//	int i = (int) db.insert(PROFILE_TABLE, null, initialValues);
		int i=db.update(PROFILE_TABLE, initialValues, null, null);

			Log.d("Values",
					"" + i + ""
							+ db.update(PROFILE_TABLE, initialValues, null, null));

			if (i > 0) {
				Log.d("Registration values", "Inserted");
				response="success";
			} else {
				Log.d("Registration", "Not Done");
				response="unsuccess";
			}
			return response;

		}
		
		
		// PROFILE TABLE SELECT QUERY
		public ArrayList<HashMap<String, String>> ProfileRetrieve(String Username) {
			ArrayList<HashMap<String, String>> profile;
			profile = new ArrayList<HashMap<String, String>>();
			String selectQuery = "SELECT  * FROM " + PROFILE_TABLE + " where "
					+ PROFILE_TABLE_COLUMN4 + " = '" + Username + "'";
			SQLiteDatabase database = this.getWritableDatabase();
			Cursor cursor = database.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("FIRSTNAME", cursor.getString(0));
					map.put("LASTNAME", cursor.getString(1));
					map.put("EMAIL", cursor.getString(2));
					map.put("USERNAME", cursor.getString(3));
					map.put("Password", cursor.getString(4));
					map.put("ZipCode", cursor.getString(5));
					map.put("mobile", cursor.getString(6));
					profile.add(map);
				} while (cursor.moveToNext());
			}
			return profile;
		}

		public String loginValidation(String userName, String password) {
			String response = "";
			String loginQuery = "select * from " + PROFILE_TABLE + " where "
					+ PROFILE_TABLE_COLUMN4 + " = '" + userName + "' and "
					+ PROFILE_TABLE_COLUMN5 + " = '" + password + "'";

			System.out.println("login query is:::: " + loginQuery);

			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery(loginQuery, null);
			if (cursor.moveToNext())

			{
				response = "Valid";
			} else {
				response = "Invalid";
			}

			return response;

		}
		


}
