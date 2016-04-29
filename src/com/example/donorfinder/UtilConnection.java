package com.example.donorfinder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import android.os.StrictMode;

public class UtilConnection {
	static String response = "";
	static String url = "http://192.168.43.2:8081/DonorServer/";

	static String getConnectin(String request) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			request = request.replace(' ', '+');

			System.out.println("" + request);
			URI u = new URI(request);

			String urlstr = u.toASCIIString();

			URL url = new URL(urlstr); // find the address

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();// establish the connection

			// reading the response through input stream
			InputStream is = connection.getInputStream();

			int i = 0;
			StringBuffer buffer = new StringBuffer();
			while ((i = is.read()) != -1) {
				char c = (char) i;
				buffer.append(c);
			}

			response = buffer.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

}
