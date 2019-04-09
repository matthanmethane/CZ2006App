package com.example.testapp.viewmodel;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ViewModel for SetAddressView to process the postal code inputted by the user.
 */
public class AddressViewModel extends AsyncTask<Void, Void, Void> {
    String postalCode;
    boolean valid = false;

    /**
     * Class constructor.
     * @param postalCode the user's input postal code
     */
    public AddressViewModel(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Run the asynchronous function as in order to validate the postal code, a json request is required.
     * @param voids void
     * @return void
     */
    @Override
    protected Void doInBackground(Void... voids) {
        valid = validPostalCode(postalCode);
        return null;
    }

    /**
     * Get the confirmation on whether the user's postal code is an actual location.
     * @return the validity of the postal code
     */
    public boolean getValid() {
        return valid;
    }

    private boolean validPostalCode(String postalCode) {
        String reqUrl = "https://developers.onemap.sg/commonapi/search?searchVal={postalCode}&returnGeom=Y&getAddrDetails=N&pageNum=1";
        reqUrl = reqUrl.replace("{postalCode}",postalCode);

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = convertStreamToString(in);

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getJSONArray("results").length() != 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception: " +e.getMessage());
            return false;
        }
        return false;
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
}
