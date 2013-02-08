package com.codeblue.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.projects.codeblue.CodeBlueEmergency;


public class CodeBlueWS extends   AsyncTask<Void, Void, Void>{
	JSONObject json = new JSONObject();
	HttpClient client = new DefaultHttpClient();
	HttpResponse response ;
	HttpPost post = new HttpPost("http://projects.usjr.edu.ph/12it422m2a01/cb_webservice.php");
	List<NameValuePair> paramz = new ArrayList<NameValuePair>(2);
	
	double longitude;
	double latitude;
	
	public CodeBlueWS(double longitude, double latitude){
		this.latitude = longitude;
		this.longitude = longitude;
		
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.e("FVK", "FVKER!!");
		try {
			json.put("first_name", "Mac");
			json.put("last_name", "Madrid");
			json.put("x-loc", latitude);
			json.put("y-loc", longitude);
			paramz.add(new BasicNameValuePair("data", json.toString()));
			Log.e("JSOn", json.toString());
			post.setEntity(new UrlEncodedFormEntity(paramz));
			response = client.execute(post);
			
			 
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
