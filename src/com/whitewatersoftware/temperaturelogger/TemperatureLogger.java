package com.whitewatersoftware.temperaturelogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class TemperatureLogger
    extends Activity
    implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mTemperature;
    private TextView textView;

    private Thermometer thermometer=new Thermometer(10);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	textView=new TextView(this);
	textView.setTextSize(40);
	textView.setText("");
	setContentView(textView);
	int sensor=getResources().getInteger(R.integer.sensor);
	mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
	mTemperature=mSensorManager.getDefaultSensor(sensor);
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
     }

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public final void onSensorChanged(SensorEvent event) {
	if (thermometer.change(event.values[0])) {
	    Intent intent=new Intent("ACTION_WIDGET_UPDATE_FROM_ACTIVITY");
	    intent.putExtra("INTENT_EXTRA_WIDGET_TEXT",
			    thermometer.toStringWidget());
	    sendBroadcast(intent);
	}
	textView.setText(thermometer.toString());
    }

    protected void onResume() {
	super.onResume();
	mSensorManager.registerListener(this,
					mTemperature,
					SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
	super.onPause();
	// mSensorManager.unregisterListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater=getMenuInflater();
	inflater.inflate(R.menu.app,menu);
	return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
	Intent intent=new Intent("ACTION_WIDGET_UPDATE_FROM_ACTIVITY");
	intent.putExtra("INTENT_EXTRA_WIDGET_TEXT","finish");
	sendBroadcast(intent);
	finish();
	return true;
    }

}
