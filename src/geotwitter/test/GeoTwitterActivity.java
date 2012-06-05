package geotwitter.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GeoTwitterActivity extends Activity {

	GridView listView;
	ImageView imgGeo;
	ImageView imgRefresh;
	ImageView imgSettings;
	int geoLat = 0;
	int geoLng = 0;
	int radius = 1000;
	int minutes = 300;

	TwitList _list;
	boolean _click = false;

	LocationListener locationListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (GridView) findViewById(R.id.mainlist);
		imgGeo = (ImageView) findViewById(R.id.geo_button);
		imgRefresh = (ImageView) findViewById(R.id.refresh_button);
		imgSettings = (ImageView) findViewById(R.id.settings_button);

		imgGeo.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.button_selected_shape);
					_click = true;
					break;
				case MotionEvent.ACTION_MOVE:
					if (event.getY() > v.getBottom()
							| event.getY() < v.getTop()
							| event.getX() + imgGeo.getLeft() < v.getLeft()
							| event.getX() + imgGeo.getLeft() > v.getRight()) {
						ClearColor();
						_click = false;
					}
					break;
				case MotionEvent.ACTION_UP:
					ClearColor();
					if (!_click) {
						break;
					}
					Intent i = new Intent(GeoTwitterActivity.this,
							MapGoogle.class);
					i.putExtra("GeoLat", geoLat);
					i.putExtra("GeoLng", geoLng);
					i.putExtra("Radius", radius);
					startActivityForResult(i, 12);
					break;
				default:
					break;
				}
				return true;
			}
		});

		imgRefresh.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.button_selected_shape);
					_click = true;
					break;
				case MotionEvent.ACTION_MOVE:
					if (event.getY() > v.getBottom()
							| event.getY() < v.getTop()
							| event.getX() + imgRefresh.getLeft() < v.getLeft()
							| event.getX() + imgRefresh.getLeft() > v
									.getRight()) {
						ClearColor();
						_click = false;
					}
					break;
				case MotionEvent.ACTION_UP:
					ClearColor();
					if (!_click) {
						break;
					}
					// fillList();
					break;
				default:
					break;
				}
				return true;
			}
		});

		imgSettings.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.button_selected_shape);
					_click = true;
					break;
				case MotionEvent.ACTION_MOVE:
					if (event.getY() > v.getBottom()
							| event.getY() < v.getTop()
							| event.getX() + imgSettings.getLeft() < v
									.getLeft()
							| event.getX() + imgSettings.getLeft() > v
									.getRight()) {
						ClearColor();
						_click = false;
					}
					break;
				case MotionEvent.ACTION_UP:
					ClearColor();
					if (!_click) {
						break;
					}
					Intent i = new Intent(GeoTwitterActivity.this,
							SettingActivity.class);
					i.putExtra("Radius", radius);
					i.putExtra("Minutes", minutes);
					startActivityForResult(i, 14);
					break;
				default:
					break;
				}
				return true;
			}
		});
	
		fillList(geoLat,geoLng,radius,minutes);
	}

	private void fillList(int lat, int lng, int radius, int minutes) {
		if (geoLat == 0 & geoLng == 0) {
			LocationManager locationManager = (LocationManager) this
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
			Location location = Utility.getLocation(this);
			if (location != null) {
				geoLat = (int)(location.getLatitude()*1000000.0);
				geoLng = (int)(location.getLongitude()*1000000.0);
				_list = TwitList.GetListByJSON(location.getLatitude(),
						location.getLongitude(), (double)radius/1000d);
			} else {
				Toast.makeText(getApplicationContext(), "No GPS",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			double _lat = (double) lat / 1000000d;
			double _lng = (double) lng / 1000000d;
			_list = TwitList.GetListByJSON(_lat, _lng, (double)radius/1000d);
		}

		listView.setAdapter(null);
		listView.setAdapter(new ViewAdapter(this, _list));

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (12): {
			if (resultCode == Activity.RESULT_OK) {
				geoLat = data.getIntExtra("LastClickGeoLat", 0);
				geoLng = data.getIntExtra("LastClickGeoLng", 0);
				if (geoLat != 0 & geoLng != 0) {
					fillList(geoLat,geoLng,radius,minutes);
				} else {
					AlertDialog.Builder dialog = new AlertDialog.Builder(this);
					dialog.setTitle("My Geo");
					dialog.setMessage("No Geo");
					dialog.show();
				}
			}
			break;
		}
		case (14): {
			if (resultCode == Activity.RESULT_OK) {
				radius = data.getIntExtra("RadiusOut", 1000);
				minutes = data.getIntExtra("MinutesOut", 300);
				if (geoLat != 0 & geoLng != 0) {
					fillList(geoLat,geoLng,radius,minutes);
				} else {
					AlertDialog.Builder dialog = new AlertDialog.Builder(this);
					dialog.setTitle("My Geo");
					dialog.setMessage("No Geo");
					dialog.show();
				}
			}
			break;
		}
		}
	}

	void ClearColor() {
		imgGeo.setBackgroundResource(R.drawable.button_shape);
		imgRefresh.setBackgroundResource(R.drawable.button_shape);
		imgSettings.setBackgroundResource(R.drawable.button_shape);
	}
}