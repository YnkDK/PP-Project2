package dk.au.pp13.positionfinder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dk.au.pp13.positionfinder.filters.AccelerometerFilter;


public class DistanceAccelerometerActivity extends ActionBarActivity implements SensorEventListener {

    private float lastX = 0, lastY = 0, lastZ = 0;
    private long lastMovement = 0;
    private AccelerometerFilter filter;
    private TextView maxDistance;
    private EditText editFieldDistance;
    private GPSListener locationListener;
    private TextView movementBool;
    private LocationManager locationManager;
    private HTTPFix fixer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_accelerometer);

        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);

        filter = null;

        TextView gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        maxDistance = (TextView) findViewById(R.id.maxDistance);
        movementBool = (TextView) findViewById(R.id.movementBool);
        editFieldDistance = (EditText) findViewById(R.id.inputFieldDistance);

        gpsCoordinates.setText("Waiting for GPS...");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                locationListener);
        fixer = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float THRESHOLD = 4;
        if (Math.abs(lastX - sensorEvent.values[0]) > THRESHOLD ||
                Math.abs(lastY - sensorEvent.values[1]) > THRESHOLD ||
                Math.abs(lastZ - sensorEvent.values[2]) > THRESHOLD) {
            if (filter != null) {
                filter.movement();
                movementBool.setText("Movement: YES");
                lastMovement = sensorEvent.timestamp;
            }
        } else {
            if (filter != null && sensorEvent.timestamp - lastMovement > 1500000000) {
                filter.noMovement();
                movementBool.setText("Movement: no.");
            }
        }

        lastX = sensorEvent.values[0];
        lastY = sensorEvent.values[1];
        lastZ = sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void setDistance(View view) {
        if (editFieldDistance.getText().length() > 0) {
            long distance = Long.parseLong(String.valueOf(editFieldDistance.getText()));
            fixer = new HTTPFix("Accelerometer-distance" + distance);
            locationListener.setLogger(fixer);

            filter = new AccelerometerFilter(distance);
            this.locationListener.setFilter(filter);

            maxDistance.setText("Current distance threshold: " + distance + " meters");
            editFieldDistance.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        locationManager.removeUpdates(locationListener);
    }

    public void sendWaypoint(View view) {
        if (fixer != null) {
            fixer.waypoint();
            Toast.makeText(this, "Waypoint send!", Toast.LENGTH_SHORT).show();
        }
    }
}
