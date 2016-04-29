package com.example.donorfinder;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {

	TextView pfirstname, plastname, pemail, pusername, pdob, pgender,pNumber;
	Button updateBt;

	ArrayList<HashMap<String, String>> ProfileRetrieve = new ArrayList<HashMap<String, String>>();
	int value = 0;
	ArrayList<Integer> tdayprofile = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		pfirstname = (TextView) findViewById(R.id.pfirstname);
		plastname = (TextView) findViewById(R.id.plastname);
		pemail = (TextView) findViewById(R.id.pemail);
		pusername = (TextView) findViewById(R.id.pusername);
		pgender = (TextView) findViewById(R.id.pgender);
		// pdob=(TextView) findViewById(R.id.pdob);
		pNumber=(TextView) findViewById(R.id.zip);
		updateBt=(Button) findViewById(R.id.updateBt);
		
		updateBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url = UtilConnection.url
						+ "DonorRegServlet?action=donorUpdate&firstname="
						+ pfirstname.getText().toString() + "&lastname="
						+ plastname.getText().toString() + "&userid="
						+ pusername.getText().toString() + "&email="
						+ pemail.getText().toString() + "&phonenumber="
						+ pNumber.getText().toString()+"&zipcode="+pgender.getText().toString();
				String res=UtilConnection.getConnectin(url);

				if (res.equals("success")) {
					Toast.makeText(Profile.this,
							"" + res, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(Profile.this,
							"" + res, Toast.LENGTH_LONG).show();
				}
				
			}
		});

		Profiletoday();

	}

	public void Profiletoday() {
		ArrayList<HashMap<String, String>> profileMap = new ArrayList<HashMap<String, String>>();
		SqliteDatabasehelper helper = new SqliteDatabasehelper(Profile.this);
		profileMap = helper.ProfileRetrieve(LoginActivity.userName1);

		pfirstname.setText(profileMap.get(0).get("FIRSTNAME"));
		plastname.setText(profileMap.get(0).get("LASTNAME"));
		pemail.setText(profileMap.get(0).get("USERNAME"));
		pusername.setText(profileMap.get(0).get("EMAIL"));
		// Toast.makeText(Profile.this, ""+profileMap.get(0).get("DOB"),
		// Toast.LENGTH_LONG).show();
		pgender.setText(profileMap.get(0).get("ZipCode"));
		pNumber.setText(profileMap.get(0).get("mobile"));

	}


}
