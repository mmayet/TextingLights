package com.example.textinglights;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mTextViewLight;
	private SensorManager mSensorManager;
	private SensorEventListener mEventListnerLight;
	private float lastLightValue;

	private void updateUI() {
		runOnUiThread(new Runnable(){
			@Override
			public void run() {
				mTextViewLight.setText("light is" + lastLightValue);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mTextViewLight = (TextView) findViewById(R.id.editText1);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		mEventListnerLight = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				float[] values = event.values;
				lastLightValue = values[0];
				updateUI();
			}

			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
			}
		};
	}

	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(mEventListnerLight, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), 
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void onStop() {
		mSensorManager.unregisterListener(mEventListnerLight);
		super.onStop();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
