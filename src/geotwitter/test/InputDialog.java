package geotwitter.test;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputDialog extends Dialog {
	private ReadyRadiusListener readyRadiusListener;
	private ReadyMinutesListener readyMinutesListener;
	private String input;
	EditText inputText;
	boolean isRadius = true;


	public interface ReadyRadiusListener {
		public void ready(int value);
	}
	
	public interface ReadyMinutesListener {
		public void ready(int value);
	}

	public InputDialog(Context context, String input,
			ReadyRadiusListener readyListener) {
		super(context);
		this.input = input;
		this.readyRadiusListener = readyListener;
		isRadius = true;
	}

	public InputDialog(Context context, String input,
			ReadyMinutesListener readyListener) {
		super(context);
		this.input = input;
		this.readyMinutesListener = readyListener;
		isRadius = false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_dialog);

		Button buttonOK = (Button) findViewById(R.id.dialog_input_save_button);
		buttonOK.setOnClickListener(new OKListener());

		Button buttonCancael = (Button) findViewById(R.id.dialog_input_cancel_button);
		buttonCancael.setOnClickListener(new CancelListener());

		inputText = (EditText) findViewById(R.id.dialog_input_edit_text);
		
		SetDialog();

	}
	
	private void SetDialog(){
		inputText.setText(input);
//		if (typeData == 1) {
			inputText.setInputType(0x00002002);
//		} else if (typeData == 3) {
//			inputText.setInputType(0x00002002);
//		} else {
//			inputText.setInputType(0x00000001);
//		}

		if (isRadius) {
			this.setTitle("Set radius, m");
		} else {
			this.setTitle("Set minutes before");
		}
	}

	private class OKListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			if(inputText.getText().toString().compareTo("")==0){
				inputText.setText("0");
			}
			if (isRadius) {
				readyRadiusListener.ready(Integer.parseInt(inputText.getText().toString()));
			} else {
				readyMinutesListener.ready(Integer.parseInt(inputText.getText().toString()));
			}
			InputDialog.this.dismiss();
		}
	}

	private class CancelListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			InputDialog.this.dismiss();
		}
	}
	
	public void updateRadius(String value) {
		isRadius = true;
		this.input = value;
		SetDialog();
	}
	
	public void updateMinutes(String value) {
		isRadius = false;
		this.input = value;
		SetDialog();
	}

}
