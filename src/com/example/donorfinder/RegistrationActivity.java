package com.example.donorfinder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	EditText firstnameEt, lastNameEt, emailEt, usernameEt, passwordEt,
			confirmPwdEt, zipEt, phoneNumberEt;
	Button loginBt, signupBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		firstnameEt = (EditText) findViewById(R.id.firstnameEtid);
		lastNameEt = (EditText) findViewById(R.id.lastnameEtid);
		emailEt = (EditText) findViewById(R.id.emailEtid);
		usernameEt = (EditText) findViewById(R.id.usernameEtid);
		passwordEt = (EditText) findViewById(R.id.passwordEtid);
		confirmPwdEt = (EditText) findViewById(R.id.cpasswordEtid);
		zipEt = (EditText) findViewById(R.id.zipEtId);
		phoneNumberEt = (EditText) findViewById(R.id.phoneNumberEtId);
		signupBt = (Button) findViewById(R.id.signupBtid);

		signupBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!isValidEmail(emailEt.getText().toString())) {
					emailEt.setError(Html
							.fromHtml("<font color='red'>Invalid Email</font>"));

				}

				if (!isValidFirstName(firstnameEt.getText().toString())) {
					firstnameEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}

				if (!isValidlastName(lastNameEt.getText().toString())) {
					lastNameEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}
				if (!isValiduserName(usernameEt.getText().toString())) {
					usernameEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}
				if (!isValidPassword(passwordEt.getText().toString())) {
					passwordEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}
				if (!isValidConfirmPassword(confirmPwdEt.getText().toString())) {
					confirmPwdEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}
				if (!isValidZipCode(zipEt.getText().toString())) {
					zipEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}
				if (!isValidPhoneNumber(phoneNumberEt.getText().toString())) {
					zipEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}

				if (isValidConfirmPassword(confirmPwdEt.getText().toString())
						&& isValidEmail(emailEt.getText().toString())
						&& isValidFirstName(firstnameEt.getText().toString())
						&& isValidlastName(lastNameEt.getText().toString())
						&& isValidPassword(passwordEt.getText().toString())
						&& isValiduserName(usernameEt.getText().toString())
						&& isValidZipCode(zipEt.getText().toString())
						&& isValidPhoneNumber(phoneNumberEt.getText()
								.toString())
						&& isValidpassConfirm(passwordEt.getText().toString(),
								confirmPwdEt.getText().toString())) {
					SqliteDatabasehelper helper = new SqliteDatabasehelper(
							RegistrationActivity.this);
					helper.getWritableDatabase();

					String response = helper.insertProfile(firstnameEt
							.getText().toString(), lastNameEt.getText()
							.toString(), emailEt.getText().toString(),
							usernameEt.getText().toString(), passwordEt
									.getText().toString(), zipEt.getText()
									.toString(), phoneNumberEt.getText()
									.toString());

					String url = UtilConnection.url
							+ "DonorRegServlet?action=donorReg&firstName="
							+ firstnameEt.getText().toString() + "&lastName="
							+ lastNameEt.getText().toString() + "&userName="
							+ usernameEt.getText().toString() + "&password="
							+ passwordEt.getText().toString() + "&email="
							+ emailEt.getText().toString() + "&mobileNumber="
							+ phoneNumberEt.getText().toString()+"&zipcode="+zipEt.getText().toString();
					String res=UtilConnection.getConnectin(url);

					if (response.equals("success")) {
						Toast.makeText(RegistrationActivity.this,
								"" + response, Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(RegistrationActivity.this,
								"" + response, Toast.LENGTH_LONG).show();
					}
				}

			}
		});

	}

	private boolean isValidZipCode(String zip) {
		// TODO Auto-generated method stub
		if (zip != null && !zip.equals("")) {
			return true;
		}
		return false;
	}

	private boolean isValidpassConfirm(String password, String confirmPassword) {
		// TODO Auto-generated method stub
		if (password.equals(confirmPassword)) {
			return true;
		}
		return false;
	}

	private boolean isValidConfirmPassword(String confirmPassword) {
		// TODO Auto-generated method stub
		if (confirmPassword != null && !confirmPassword.equals("")) {
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

	private boolean isValiduserName(String userName) {
		// TODO Auto-generated method stub
		if (userName != null && !userName.equals("")) {
			return true;
		}
		return false;
	}

	private boolean isValidlastName(String lastName) {
		// TODO Auto-generated method stub
		if (lastName != null && !lastName.equals("")) {
			return true;
		}
		return false;
	}

	private boolean isValidFirstName(String firstName) {
		// TODO Auto-generated method stub
		if (firstName != null && !firstName.equals("")) {
			return true;
		}
		return false;
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		if (phoneNumber != null && !phoneNumber.equals("")) {
			return true;
		}
		return false;
	}

	private boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
