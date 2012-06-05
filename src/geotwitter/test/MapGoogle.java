package geotwitter.test;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class MapGoogle extends MapActivity {

	MyLocationOverlay cMyLocationOverlay;
	MapView mapView;
	MapController mMapController;

	LocationListener locationListener;

	List<Overlay> mapOverlays;
	Drawable drawable;
	TwitItemizedOverlay itemizedOverlay;

	GeoPoint ptLastClick = null;
	int geoLat = 0;
	int geoLng = 0;
	int radius = 1000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		mapView = (MapView) findViewById(R.id.map_view);
		Button buttonOK = (Button) findViewById(R.id.googlemaps_select_location);
		buttonOK.setOnClickListener(new OKListener());

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey("GeoLat")) {
				geoLat = extras.getInt("GeoLat");
			}
			if (extras.containsKey("GeoLng")) {
				geoLng = extras.getInt("GeoLng");
			}
			if (extras.containsKey("Radius")) {
				radius = extras.getInt("Radius");
			}
		}
		mapView.setBuiltInZoomControls(true);
		mMapController = mapView.getController();

		loadMap();
	}

	void loadMap() {
		GeoPoint point = null;
		if (geoLat == 0 & geoLng == 0) {
			final LocationManager locationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			locationListener = new LocationListener() {

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

				@Override
				public void onProviderEnabled(String provider) {
				}

				@Override
				public void onProviderDisabled(String provider) {
				}

				@Override
				public void onLocationChanged(Location location) {
				}
			};
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 300, 300,
					locationListener);
			mMapController.setZoom(15);
			Location location = Utility.getLocation(this);
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
				geoLat = (int) (lat * 1E6);
				geoLng = (int) (lng * 1E6);
			}
		} else {
			point = new GeoPoint(geoLat, geoLng);
		}

		// MyLocationOverlay cMyLocationOverlay = new
		// MyLocationOverlay(this,
		// mapView);
		// cMyLocationOverlay.enableCompass();
		// cMyLocationOverlay.enableMyLocation();
		// mapView.getOverlays().add(cMyLocationOverlay);

		mMapController.animateTo(point);
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.pin);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());

		mapView.getOverlays().add(
				new TwitItemizedOverlay(drawable, point,
						(ImageView) findViewById(R.id.drag), MapGoogle.this,
						new OnMarkerListener(),radius));
		mapView.getOverlays().add(
				new CircleOverlay(MapGoogle.this, (double) geoLat / 1000000d,
						(double) geoLng / 1000000d, (float)radius));
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private class OnMarkerListener implements
			TwitItemizedOverlay.MarkerDragDropListener {
		@Override
		public void ready(GeoPoint pt) {
			ptLastClick = pt;
			
		}
	}

	private class OKListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent resultIntent = new Intent();
			if (ptLastClick != null) {
				resultIntent.putExtra("LastClickGeoLat",
						ptLastClick.getLatitudeE6());
				resultIntent.putExtra("LastClickGeoLng",
						ptLastClick.getLongitudeE6());
			} else {
				resultIntent.putExtra("LastClickGeoLat", 0.0);
				resultIntent.putExtra("LastClickGeoLng", 0.0);
			}
			setResult(RESULT_OK, resultIntent);
			MapGoogle.this.finish();
		}
	}
}
