package com.example.testapp.filter;

import android.os.AsyncTask;

import com.example.testapp.db.entity.SchoolEntity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to sort schools based on user distance.
 */
public class DistanceFilter extends AsyncTask<Void, Void, List<SchoolEntity>> implements Filter{
    private String postalCode;
    private List<SchoolEntity> beforeSort;
    private List<SchoolEntity> afterSort;
    private String longitude;
    private String latitude;

    /**
     * Class constructor.
     * @param postalCode user's postal code
     * @param schools list of unsorted schools
     */
    public DistanceFilter(String postalCode, List<SchoolEntity> schools) {
        super();
        this.postalCode = postalCode;
        this.beforeSort = schools;
    }

    /**
     * Get distance between the user and schools, and sort the schools based on distance.
     * @param arg0
     */
    @Override
    protected List<SchoolEntity> doInBackground(Void... arg0) {
        // Making a request to url and getting response

        postalCodeToCoor(postalCode);
        distanceSort();

        return afterSort;
    }

    /**
     * Get sorted school based on distance.
     * @return list of sorted schools
     */
    public List<SchoolEntity> getSorted() {
        return afterSort;
    }

    private void postalCodeToCoor(String postalCode) {
        String reqUrl = "https://developers.onemap.sg/commonapi/search?searchVal={postalCode}&returnGeom=Y&getAddrDetails=N&pageNum=1";
        reqUrl = reqUrl.replace("{postalCode}",postalCode);

        longitude = "-1";
        latitude = "-1";
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = convertStreamToString(in);

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getJSONArray("results").length() != 0) {
                longitude = jsonObject.getJSONArray("results").getJSONObject(0).getString("LONGITUDE");
                latitude = jsonObject.getJSONArray("results").getJSONObject(0).getString("LATITUDE");
            }
        } catch (Exception e) {
            System.out.println("Exception: " +e.getMessage());
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private void distanceSort() {
        afterSort = new ArrayList<>();
        List<Long> distanceList = new ArrayList<>();
        // Map index to distance
        for (int i = 0; i < beforeSort.size(); i ++) {
            long dist = getDistance(beforeSort.get(i).getLatitude(),beforeSort.get(i).getLongitude());
            int j;
            for (j = 0; j < i; j ++) {
                if (dist < distanceList.get(j))
                    break;
            }
            afterSort.add(j,beforeSort.get(i));
            distanceList.add(j,dist);
        }
    }

    private long getDistance(double lat, double lng) {
      String distanceAPI = "https://maps.googleapis.com/maps/api/distancematrix/json?&origins={start}&destinations={end}&key={token}&mode=transit";
        String start = latitude + "," + longitude;
        String end = Double.toString(lat) + "," + Double.toString(lng);
        distanceAPI = distanceAPI.replace("{token}", "AIzaSyA5qr9oHTgIYBhz5BHTSs0CMTIg__S0Rdk");
        distanceAPI = distanceAPI.replace("{start}",start);
        distanceAPI = distanceAPI.replace("{end}",end);

        long distance = -1;
        try {
            URL url = new URL(distanceAPI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = convertStreamToString(in);

            JSONObject jsonObject = new JSONObject(response);
            distance = Long.parseLong(jsonObject.getJSONArray("rows")
                            .getJSONObject(0)
                            .getJSONArray("elements")
                            .getJSONObject(0)
                            .getJSONObject("duration")
                            .getString("value"));
        } catch (Exception e) {
            System.out.println("Exception: " +e.getMessage());
        }
        return distance;
    }

}
