package dk.au.pp13.positionfinder;

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
    private Thread gpsUpdater;

    private int time = 0;
    private Handler handler;

    private GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic);

        this.timeInSecTextview = (TextView) findViewById(R.id.periodicTimeInSec);
        this.inputFieldEdittext = (EditText) findViewById(R.id.inputFieldTimeInSec);
        this.gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        this.timeInSecTextview.setText(time + "");
        handler = new Handler();
        gps = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.gpsUpdater != null) {
            this.gpsUpdater.interrupt();
        }
    }

    public void setPeriodicTime(View view) {
        if(gps == null) {
            // This blocks until the first fix is received
            gps = new GPS(this);
        }
        if(inputFieldEdittext.getText().length() > 0)
            time = Integer.parseInt(String.valueOf(inputFieldEdittext.getText()));
        if(time > 0) {
            if(this.gpsUpdater != null) {
                this.gpsUpdater.interrupt();
            }
            this.gpsUpdater = new Thread(new Task());
            this.gpsUpdater.start();
        }
    }

    class Task implements Runnable {
        @Override
        public void run() {
            do {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String gpsCoor = gps.getLastKnownLocation().toString();
                        gpsCoordinates.setText(gpsCoor);

                    }
                });
                try {
                    Thread.sleep(time);
                } catch (InterruptedException ignored) {
                    Log.d("PeriodicActivity", "Interrupted gps updater task");
                    return;
                }
            } while (true);
        }
    }

    private void updateLocation() {
        String gpsCoor = gps.getLastKnownLocation().toString();

        timeInSecTextview.setText(time + "");
        inputFieldEdittext.setText("");
        gpsCoordinates.setText(gpsCoor);
    }
}
