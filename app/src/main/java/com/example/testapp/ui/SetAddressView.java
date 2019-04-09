package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.R;
import com.example.testapp.viewmodel.AddressViewModel;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Set user's address view.
 */
public class SetAddressView extends AppCompatActivity {
    /**
     * Generate the UI display of which the user must input their postal code.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address_view);

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel",-1);
        String schoolLvl;

        switch(schoolLevel){
            case 1:
                schoolLvl = "Primary School";
                break;
            case 2:
                schoolLvl = "Secondary School";
                break;
            case 3 :
                schoolLvl = "Junior College";
                break;
            default:
                schoolLvl = "we";
                break;
        }

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Set Postal code(Step 2 of 4)");

        EditText setAddress = (EditText) findViewById(R.id.set_address);
        Button submitAddress = (Button) findViewById(R.id.submit_address);
        TextView viewLvl = (TextView) findViewById(R.id.view_lvl);

        String viewLvlText= "School Level: "+schoolLvl+"\n \n \n";
        viewLvl.setText(viewLvlText);

        submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Need to validate address
                String address = setAddress.getText().toString();
                if (address.length() != 6 | !address.matches("[0-9]+")) {
                    Toast.makeText(getApplicationContext(),"Please input a 6 digit postal code",Toast.LENGTH_LONG).show();
                } else {
                    AddressViewModel model = new AddressViewModel(address);
                    try {
                        model.execute().get();
                    } catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }
                    if (model.getValid()) {
                        Intent openSubject = new Intent(getApplicationContext(), SubjectView.class);
                        openSubject.putExtra("schoolLevel", schoolLevel);
                        openSubject.putExtra("address", address);
                        startActivity(openSubject);
                    } else {
                        Toast.makeText(getApplicationContext(),"Please input valid postal code",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    /**
     * Return to the previous page when the back button in the header is selected.
     * @param item header menu button
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}