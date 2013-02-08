package com.codeblue.geolocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.util.Log;

import com.google.android.maps.GeoPoint;

public class CodeBlueGeoLocationMethods {

	/* this method is called to fetch datas */
	public Document dataFetch(GeoPoint src, GeoPoint destination) {
		String mapURL = "http://maps.google.com/maps/api/directions/xml?origin="
				+ Double.toString(src.getLatitudeE6() / 1.0E6)
				+ ","
				+ Double.toString(src.getLongitudeE6() / 1.0E6)
				+ "&destination="
				+ Double.toString(destination.getLatitudeE6() / 1.0E6)
				+ ","
				+ Double.toString(destination.getLongitudeE6() / 1.0E6)
				+ "&sensor=true&mode=driving";

		Log.e("url", mapURL);

		Document document = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(mapURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = (Document) db.parse(urlConnection.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return document;

	}
	
	/* function from the internet for decoding polylines */
	/* http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java */
	public List<GeoPoint> decodePoly(String encoded) {

		List<GeoPoint> poly = new ArrayList<GeoPoint>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),
					(int) (((double) lng / 1E5) * 1E6));
			poly.add(p);
		}

		return poly;
	}

}
