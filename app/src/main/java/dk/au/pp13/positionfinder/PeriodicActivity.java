package dk.au.pp13.positionfinder;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PeriodicActivity extends ActionBarActivity {

    private TextView timeInSecTextview;
    private TextView gpsCoordinates;
    private EditText inputFieldEdittext;

    private long time = 0;
    private LocationManager locationManager = null;
    private GPSListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic);

        this.timeInSecTextview = (TextView) findViewById(R.id.periodicTimeInSec);
        this.inputFieldEdittext = (EditText) findViewById(R.id.inputFieldTimeInSec);
        this.gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        this.timeInSecTextview.setText(time + "");

        // Get the location service from the current context
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener(gpsCoordinates);
    }

    public void setPeriodicTime(View view) {
        if(locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }

        if(inputFieldEdittext.getText().length() > 0)
            time = Long.parseLong(String.valueOf(inputFieldEdittext.getText()));
        timeInSecTextview.setText(time + "");



        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, time * 1000, 0,
                locationListener);
    }
}
