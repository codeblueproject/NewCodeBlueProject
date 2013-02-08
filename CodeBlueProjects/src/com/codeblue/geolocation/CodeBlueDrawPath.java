package com.codeblue.geolocation;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

public class CodeBlueDrawPath extends AsyncTask<GoogleMap, Void, GoogleMap> {

	private ProgressDialog dialog;
	private Context context;
	private GeoPoint origin;
	private GeoPoint destination;
	private Document document;
	private CodeBlueGeoLocationMethods cbGeoMethods;

	public CodeBlueDrawPath(Context context, GeoPoint origin,
			GeoPoint destination) {
		this.context = context;
		this.cbGeoMethods = new CodeBlueGeoLocationMethods();
		this.origin = origin;
		this.destination = destination;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.setMessage("Drawing location...");
		dialog.setIndeterminate(true);
		dialog.show();
	}

	@Override
	protected GoogleMap doInBackground(GoogleMap... params) {
		this.document = cbGeoMethods.dataFetch(origin, destination);
		return params[0];
	}

	@Override
	protected void onPostExecute(GoogleMap result) {
		super.onPostExecute(result);
		if (this.document != null) {
			NodeList nlStatus = document.getElementsByTagName("status");
			Node nodeStatus = nlStatus.item(0);
			String status = nodeStatus.getChildNodes().item(0).getNodeValue();

			if (status.equalsIgnoreCase("OK")) {
				NodeList nlOverviewPoly = document
						.getElementsByTagName("overview_polyline");
				Node nodeOverviewPoly = nlOverviewPoly.item(0);
				Element ePoints = (Element) nodeOverviewPoly;
				NodeList nlPoints = ePoints.getElementsByTagName("points");
				Node nodePoints = nlPoints.item(0);
				String points = nodePoints.getChildNodes().item(0)
						.getNodeValue();
				List<GeoPoint> decodeGeoPoints = cbGeoMethods
						.decodePoly(points);

				GeoPoint geoPoint1, geoPoint2;
				geoPoint2 = decodeGeoPoints.get(0);
				for (int i = 0; i < decodeGeoPoints.size(); i++) {
					geoPoint1 = geoPoint2;
					geoPoint2 = decodeGeoPoints.get(i);
					LatLng llgp1 = new LatLng(geoPoint1.getLatitudeE6() / 1.0E6,
							geoPoint1.getLongitudeE6() / 1.0E6);
					LatLng llgp2 = new LatLng(geoPoint2.getLatitudeE6() / 1.0E6,
							geoPoint2.getLongitudeE6() / 1.0E6);
					result.addPolyline(new PolylineOptions()
							.add(llgp1).add(llgp2).color(Color.BLUE).width(3));
				}
				dialog.dismiss();

			}

		}
	}

}
