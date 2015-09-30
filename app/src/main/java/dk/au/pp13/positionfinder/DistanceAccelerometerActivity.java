package dk.au.pp13.positionfinder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class DistanceAccelerometerActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float lastX = 0, lastY = 0, lastZ = 0;
    private float threshold;
    private EditText thresholdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_accelerometer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);

        thresholdText = (EditText) findViewById(R.id.inputFieldThreshold);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(Math.abs(lastX - sensorEvent.values[0]) > threshold ||
           Math.abs(lastY - sensorEvent.values[1]) > threshold ||
           Math.abs(lastZ - sensorEvent.values[2]) > threshold) {
            Toast.makeText(getBaseContext(), "MOVEMENT", Toast.LENGTH_SHORT).show();
        }

        lastX = sensorEvent.values[0];
        lastY = sensorEvent.values[1];
        lastZ = sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void setThreshold(View view) {
        threshold = Integer.valueOf(thresholdText.getText().toString());
    }
}
