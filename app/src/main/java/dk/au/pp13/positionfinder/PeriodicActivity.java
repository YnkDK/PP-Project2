package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PeriodicActivity extends AppCompatActivity {

    private TextView timeInSecTextview;
    private TextView gpsCoordinates;
    private EditText inputFieldEdittext;

    private LocationManager locationManager = null;
    private GPSListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic);

        this.timeInSecTextview = (TextView) findViewById(R.id.periodicTimeInSec);
        this.inputFieldEdittext = (EditText) findViewById(R.id.inputFieldTimeInSec);
        this.gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);

        // Get the location service from the current context
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
    }

    public void setPeriodicTime(View view) {
        stopGPS(null);
        gpsCoordinates.setText("");

        if(inputFieldEdittext.getText().length() > 0) {
            long time = Long.parseLong(String.valueOf(inputFieldEdittext.getText()));
            if (time > 0) {
                timeInSecTextview.setText("Current time interval: " + time + " sec");
                inputFieldEdittext.setText("");
                gpsCoordinates.setText("Waiting for GPS signal...");


                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, time * 1000, 0,
                        locationListener);
            }

        }
    }

    public void stopGPS(View view) {
        if(locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
