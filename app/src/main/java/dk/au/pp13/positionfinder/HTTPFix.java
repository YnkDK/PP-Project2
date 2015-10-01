package dk.au.pp13.positionfinder;

import android.location.Location;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mys on 9/30/15.
 */
public class HTTPFix extends AsyncTask<String, Void, String> {
    private final String sessionid;
    private static final HTTPFix FIXER = new HTTPFix();

    public HTTPFix(String task) {
        sessionid = task + "-sessionStart" + System.currentTimeMillis();
    }

    public HTTPFix() {
        sessionid = null;
    }

    ;

    public void log(Location location) {
        // Create a new HttpClient and Post Header
        FIXER.execute(
                "log",
                Double.toString(location.getLatitude()),
                Double.toString(location.getLongitude()),
                Double.toString(location.getAltitude()),
                Long.toString(System.currentTimeMillis()),
                sessionid
        );
    }

    public void waypoint() {
        FIXER.execute(
                "waypoint",
                Long.toString(System.currentTimeMillis())
        );
    }


    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://jmkristensen.dk/pp/location");
        try {
            if (params[0].equals("log")) {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<>(5);
                nameValuePairs.add(new BasicNameValuePair("xpos", params[1]));
                nameValuePairs.add(new BasicNameValuePair("ypos", params[2]));
                nameValuePairs.add(new BasicNameValuePair("zpos", params[3]));
                nameValuePairs.add(new BasicNameValuePair("timestamp", params[4]));
                nameValuePairs.add(new BasicNameValuePair("sessionid", params[5]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                return response.toString();

            } else if (params[0].equals("waypoint")) {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair("timestamp", params[1]));
                nameValuePairs.add(new BasicNameValuePair("sessionid", params[2]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                return response.toString();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return null;
    }
}
