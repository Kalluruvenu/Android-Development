package com.example.donorfinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapFragment extends FragmentActivity implements android.location.LocationListener {
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	private GoogleMap googleMap;
	MapView mMapView;
	EditText zipEt;
	Button searchBt;

	// JSON Node names
	private static final String TAG_RESULTS = "results";

	// contacts JSONArray
	JSONArray contacts = null;
	String zipCode = "";
	double latitude, longitude;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donormap);

		/********** get Gps location service LocationManager object ***********/

		mMapView = (MapView) findViewById(R.id.map);
		zipEt = (EditText) findViewById(R.id.searchEtId);
		searchBt = (Button) findViewById(R.id.searchBtId);

		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();

	//	zipCode = getLocation_Zipcode();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 0, this);

		searchBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*if (!isValidZipCode(zipEt.getText().toString())) {
					zipEt.setError(Html
							.fromHtml("<font color='red'>Cannot be empty</font>"));
				}

				if (isValidZipCode(zipEt.getText().toString())) {*/
					// url to make request
					String url1 = "http://maps.googleapis.com/maps/api/geocode/json?components=postal_code:"
							+ zipCode + "&sensor=false";
					System.out.println("urlllllllllllllllllllllllllllllllllllllllllll "+url1);
					String url = UtilConnection.url
							+ "DonorRegServlet?action=getDonors&zipCode="
							+ zipCode;
					String responce = UtilConnection.getConnectin(url);

					// Creating JSON Parser instance
					JSONParser jParser = new JSONParser();

					// getting JSON string from URL
					JSONObject json = new JSONObject();
					json = jParser.getJSONFromUrl(url1);

					try {

						JSONArray resultsArray = new JSONArray();
						resultsArray = json.getJSONArray("results");

						JSONObject obj = new JSONObject();
						obj = resultsArray.getJSONObject(0);

						String lat = obj.getJSONObject("geometry")
								.getJSONObject("bounds")
								.getJSONObject("northeast").getString("lat");
						String longitude = obj.getJSONObject("geometry")
								.getJSONObject("bounds")
								.getJSONObject("northeast").getString("lng");
						Log.d("latitude", lat);
						Log.d("longitude", longitude);

						/*
						 * for (int j = 0; j < obj2.length(); j++) {
						 * Log.d("object",
						 * obj2.getJSONObject("bounds").toString());
						 * 
						 * }
						 */

						/*
						 * JSONArray
						 * array=resultsArray.getJSONObject(i).getJSONArray
						 * ("address_components"); Log.d("array",
						 * array.toString());
						 * 
						 * for (int j = 0; j < array.length(); j++) { JSONObject
						 * obj2=array.getJSONObject(j); Log.d("obj "+j,
						 * obj2.toString()+"\n"); }
						 */

						JSONArray array = new JSONArray(responce);
						for (int i = 0; i < array.length(); i++) {

							System.out.println(array.getJSONObject(i)
									.getString("firstName"));

							googleMap.addMarker(new MarkerOptions().position(
									new LatLng(Double.parseDouble(lat), Double
											.parseDouble(longitude))).title(
									array.getJSONObject(i).getString(
											"firstName")
											+ ","
											+ array.getJSONObject(i).getString(
													"mobileNumber")));
						}

						googleMap
								.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

									@Override
									public void onInfoWindowClick(Marker marker) {

										// TODO Auto-generated method stub

										String[] numberArray = marker
												.getTitle().split(",");

										Toast.makeText(MapFragment.this,
												"info window",
												Toast.LENGTH_LONG).show();

										Intent intent = new Intent(
												Intent.ACTION_CALL);
										intent.setData(Uri.parse("tel:"
												+ numberArray[1]));
										startActivity(intent);
									}
								});

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

			//	}

			}
		});

		try {
			FragmentManager fragmentManager = getSupportFragmentManager();
			SupportMapFragment fragment = (SupportMapFragment) fragmentManager
					.findFragmentById(R.id.map);
			googleMap = fragment.getMap();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		googleMap = mMapView.getMap();

		// TODO Auto-generated method stub
	}

	private String getLocation_Zipcode() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isValidZipCode(String zipCode) {
		// TODO Auto-generated method stub
		if (zipCode != null && !zipCode.equals("")) {
			return true;
		}
		return false;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		mMapView.onLowMemory();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	//	Toast.makeText(MapFragment.this, "on location changed", Toast.LENGTH_LONG).show();
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(this, Locale.getDefault());
		try {
			addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
		zipCode=	addresses.get(0).getPostalCode();
		System.out.println("Zip Code "+zipCode);
	//	Toast.makeText(MapFragment.this, ""+zipCode, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Latitude", "disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("Latitude", "enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("Latitude", "status");
	}}
