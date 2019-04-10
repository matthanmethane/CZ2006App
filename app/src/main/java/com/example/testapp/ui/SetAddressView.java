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
                int result = validatePostalCode(address);
                if (result == 1)
                    Toast.makeText(getApplicationContext(),"Please input a 6 digit postal code",Toast.LENGTH_LONG).show();
                else if (result == 2)
                    Toast.makeText(getApplicationContext(),"Please input valid postal code",Toast.LENGTH_LONG).show();
                else {
                    Intent openSubject = new Intent(getApplicationContext(), SubjectView.class);
                    openSubject.putExtra("schoolLevel", schoolLevel);
                    openSubject.putExtra("address", address);
                    startActivity(openSubject);
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

    /**
     * Check whether the input postal code is valid.
     * <p>
     * If the output is 1, it means that the postal code format is wrong.
     * If the output is 2, it means that the postal code doesnt exist.
     * If the output is 3, it means that the postal code is valid.
     * </p>
     * @param address the postal code
     * @return confirmation on whether the postal code is valid
     */
    public int validatePostalCode(String address) {
        if (address.length() != 6 | !address.matches("[0-9]+")) {
            return 1;
        }
        AddressViewModel model = new AddressViewModel(address);
        try {
            model.execute().get();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        if (model.getValid()) {
            return 3;
        }
        return 2;
    }
}