package geotwitter.test;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends Activity {

	private static final int RADIUS_DIALOG_ID = 1;
	private static final int MINURES_DIALOG_ID = 2;

	LinearLayout radius_layout;
	LinearLayout minutes_layout;

	TextView txtRadius;
	TextView txtRadiusHeader;
	TextView txtMinutes;
	TextView txtMinutesHeader;
	boolean _click = false;

	int _radius = 0;
	int _minutes = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		radius_layout = (LinearLayout) findViewById(R.id.radius_layout);
		minutes_layout = (LinearLayout) findViewById(R.id.minutes_layout);

		txtRadius = (TextView) findViewById(R.id.radius_text);
		txtRadiusHeader = (TextView) findViewById(R.id.radius_text_header);
		txtMinutes = (TextView) findViewById(R.id.minutes_text);
		txtMinutesHeader = (TextView) findViewById(R.id.minutes_text_header);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey("Radius")) {
				_radius = extras.getInt("Radius", 0);
			}
			if (extras.containsKey("Minutes")) {
				_minutes = extras.getInt("Minutes", 0);
			}
		}

		Button okB = (Button) findViewById(R.id.settings_set_button);
		okB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("RadiusOut", _radius);
				resultIntent.putExtra("MinutesOut", _minutes);
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});

		Button cancelB = (Button) findViewById(R.id.settings_cancel_button);
		cancelB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		radius_layout.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.color.selected_item);
					txtRadius.setTextColor(Color.BLACK);
					txtRadiusHeader.setTextColor(Color.BLACK);
					_click = true;
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundResource(0);
					txtRadius.setTextColor(Color.WHITE);
					txtRadiusHeader.setTextColor(Color.WHITE);
					_click = false;
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(0);
					txtRadius.setTextColor(Color.WHITE);
					txtRadiusHeader.setTextColor(Color.WHITE);
					if (!_click) {
						break;
					}
					showDialog(RADIUS_DIALOG_ID);
					break;
				default:
					break;
				}
				return true;
			}
		});

		minutes_layout.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.color.selected_item);
					txtMinutes.setTextColor(Color.BLACK);
					txtMinutesHeader.setTextColor(Color.BLACK);
					_click = true;
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundResource(0);
					txtMinutes.setTextColor(Color.WHITE);
					txtMinutesHeader.setTextColor(Color.WHITE);
					_click = false;
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(0);
					txtMinutes.setTextColor(Color.WHITE);
					txtMinutesHeader.setTextColor(Color.WHITE);
					if (!_click) {
						break;
					}
					showDialog(MINURES_DIALOG_ID);
					break;
				default:
					break;
				}
				return true;
			}
		});

		SetParameters();
	}

	void ClearColor() {
		radius_layout.setBackgroundResource(0);
		txtRadius.setTextColor(Color.WHITE);
		txtRadiusHeader.setTextColor(Color.WHITE);

		minutes_layout.setBackgroundResource(0);
		txtRadius.setTextColor(Color.WHITE);
		txtRadiusHeader.setTextColor(Color.WHITE);
	}

	private void SetParameters() {
		txtRadius.setText(String.valueOf(_radius));
		txtMinutes.setText(String.valueOf(_minutes));

	}

	private class OnReadyRadiusListener implements
			InputDialog.ReadyRadiusListener {
		@Override
		public void ready(int value) {
			_radius = value;
			SetParameters();
		}
	}

	private class OnReadyMinutesListener implements
			InputDialog.ReadyMinutesListener {
		@Override
		public void ready(int value) {
			_minutes = value;
			SetParameters();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case RADIUS_DIALOG_ID:
			InputDialog rValue = new InputDialog(this,
					"1000", new OnReadyRadiusListener());
			return rValue;

		case MINURES_DIALOG_ID:
			InputDialog mValue = new InputDialog(this,
					"300", new OnReadyMinutesListener());
			return mValue;
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(final int id, final Dialog dialog) {
		switch (id) {
		case RADIUS_DIALOG_ID:
			((InputDialog) dialog).updateRadius(
					String.valueOf(_radius));
			break;

		case MINURES_DIALOG_ID:
			((InputDialog) dialog).updateMinutes(
					String.valueOf(_minutes));
			break;
		}

	}
}
