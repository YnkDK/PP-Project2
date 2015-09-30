package dk.au.pp13.positionfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void periodic_reporting(View v) {
        Intent myIntent = new Intent(this, PeriodicActivity.class);
        this.startActivity(myIntent);
    }

    public void distance_reporting_naive(View v) {
        Intent myIntent = new Intent(this, DistanceNaiveActivity.class);
        this.startActivity(myIntent);
    }

    public void distance_reporting_max_speed(View v) {
        Intent myIntent = new Intent(this, DistanceMaxSpeedActivity.class);
        this.startActivity(myIntent);
    }

    public void distance_reporting_accelerometer(View v) {
        Intent myIntent = new Intent(this, DistanceAccelerometerActivity.class);
        this.startActivity(myIntent);
    }

    public void waypoint_collect(View view) {
        Intent myIntent = new Intent(this, WaypointCollectorActivity.class);
        this.startActivity(myIntent);
    }
}
