package com.example.donorfinder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	EditText userNameEt, passwordEt;
	Button loginbt;
	String userName, Password;
	static String userName1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		userNameEt = (EditText) findViewById(R.id.loginuserNameEtId);
		passwordEt = (EditText) findViewById(R.id.loginpasswordEtId);
		loginbt = (Button) findViewById(R.id.loginBtId);

		loginbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = userNameEt.getText().toString();
				Password = passwordEt.getText().toString();
				userName1=userName;
				
				if (!isValiduserName(userNameEt.getText().toString())) {
					userNameEt.setError(Html.fromHtml("<font color='red'>Cannot be empty</font>"));
				}

			
				if (!isValidPassword(passwordEt.getText().toString())) {
					passwordEt.setError(Html.fromHtml("<font color='red'>Cannot be empty</font>"));
				}

				if(isValiduserName(userNameEt.getText().toString())&& isValidPassword(passwordEt.getText().toString()))
				{
				SqliteDatabasehelper helper = new SqliteDatabasehelper(
						LoginActivity.this);
				helper.getReadableDatabase();

				String response = helper.loginValidation(userName, Password);
				if (response.equals("Valid")) {
					Toast.makeText(LoginActivity.this, "Valid User",
							Toast.LENGTH_LONG).show();
					
					Intent intent=new Intent(LoginActivity.this, Profile.class);
					startActivity(intent);
				} else if (response.equals("Invalid")) {
					Toast.makeText(LoginActivity.this, "InValid User",
							Toast.LENGTH_LONG).show();
				}

			}
			}
		});
	}
	
	private boolean isValiduserName(String userName) {
		// TODO Auto-generated method stub
		if (userName != null && !userName.equals("")) {
			return true;
		}
		return false;
	}

	private boolean isValidPassword(String password) {
		// TODO Auto-generated method stub
		if (password != null && !password.equals("")) {
			return true;
		}
		return false;
	}
}
