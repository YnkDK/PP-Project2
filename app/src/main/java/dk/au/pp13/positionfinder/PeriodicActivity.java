package dk.au.pp13.positionfinder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PeriodicActivity extends ActionBarActivity {

    private TextView timeInSecTextview;
    private TextView gpsCoordinates;
    private EditText inputFieldEdittext;
    private int time = 0;
    private GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic);

        this.timeInSecTextview = (TextView) findViewById(R.id.periodicTimeInSec);
        this.inputFieldEdittext = (EditText) findViewById(R.id.inputFieldTimeInSec);
        this.gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        this.timeInSecTextview.setText(time + "");
        gps = new GPS(this);
    }


    public void setPeriodicTime(View view) {

        if(inputFieldEdittext.getText().length() > 0)
            time = Integer.parseInt(String.valueOf(inputFieldEdittext.getText()));

        String gpsCoor = gps.requestLocationUpdates().toString();

        timeInSecTextview.setText(time);
        inputFieldEdittext.setText("");
        gpsCoordinates.setText(gpsCoor);
    }
}
