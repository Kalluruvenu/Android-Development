package com.example.donorfinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button signupBt, searchBt, loginBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		signupBt = (Button) findViewById(R.id.btn_signup);
		searchBt = (Button) findViewById(R.id.btn_search);
		loginBt=(Button) findViewById(R.id.btn_slogin);

		signupBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainActivity.this,
						RegistrationActivity.class);
				startActivity(intent);

			}
		});
		
		loginBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				
			}
		});
		searchBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Intent intent=new Intent(MainActivity.this, MapFragment.class);
				startActivity(intent);
			}
		});
	}

}
