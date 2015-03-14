package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyUser;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity which displays a login screen to the user.
 */
public class SignUpActivity extends Activity {
    // UI references.
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private EditText nameEditText;
    private EditText dobEditText;
    private RadioGroup genderRadioButtonGroup;
    private Spinner countrySpinner;
    private EditText locationsEditText;
    private String gender = "U";
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        // Set up the signup form.
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        nameEditText = (EditText)findViewById(R.id.name_edit_text);
        dobEditText = (EditText)findViewById(R.id.dob_edit_text);
        genderRadioButtonGroup = (RadioGroup) findViewById(R.id.gender_radio);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        locationsEditText = (EditText) findViewById(R.id.locations_edit_text);
        passwordAgainEditText = (EditText) findViewById(R.id.password_again_edit_text);
        countrySpinner.setOnItemSelectedListener(new CountrySpinnerListener());
        passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.edittext_action_signup ||
                        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    validateAndSignup();
                    return true;
                }
                return false;
            }
        });

        // Set up the submit button click handler
        Button mActionButton = (Button) findViewById(R.id.action_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validateAndSignup();
            }
        });

        changeUIBasedOnUserType();
    }

    private void changeUIBasedOnUserType()
    {
        userType = getIntent().getStringExtra(LoyaltyConstants.KEY_USER_TYPE);
        if(LoyaltyConstants.VALUE_CUSTOMER_TYPE_CONSUMER.equals(userType)) {
            dobEditText.setVisibility(View.VISIBLE);
            genderRadioButtonGroup.setVisibility(View.VISIBLE);
            nameEditText.setHint("Customer Name");

        }
        else if(LoyaltyConstants.VALUE_CUSTOMER_TYPE_RETAILER.equals(userType)) {
            dobEditText.setVisibility(View.GONE);
            genderRadioButtonGroup.setVisibility(View.GONE);
            nameEditText.setHint("Company Name");
        }
    }

    private boolean areDetailsValid()
    {
        String email = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();


        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain)) {
            Toast.makeText(this, "Some or all fields are blank", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!email.contains("@")) {

            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(passwordAgain)){
            Toast.makeText(this, "Passwords don't match, please try again", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(LoyaltyConstants.VALUE_CUSTOMER_TYPE_CONSUMER.equals(userType)) {
            DateFormat dobFormatter = new SimpleDateFormat("dd/mm/yyyy");
            try {
                Date dateOfBirth = dobFormatter.parse(dob);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid Date of birth", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;

    }

    private void validateAndSignup() {

        if(!areDetailsValid()) {
            return;
        }
        if(LoyaltyConstants.VALUE_CUSTOMER_TYPE_CONSUMER.equals(userType)) {
            signupCustomer();
        }
        else {
            signupRetailer();
        }
    }

    private void signUp(LoyaltyUser user) {
        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage(getString(R.string.progress_signup));
        dialog.show();

        // Call the Parse signup method
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void signupCustomer()
    {

        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String locations = locationsEditText.getText().toString().trim();
        if(locations.endsWith(",")) {
            locations = locations.substring(0, locations.length() - 1);
        }

        String[] locationsArray = locations.split(",");

        // Set up a new Parse user
        LoyaltyUser user = new LoyaltyUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setUserType(LoyaltyConstants.VALUE_CUSTOMER_TYPE_CONSUMER);
        user.setCustomerName(name);
        user.setCustomerDOB(dob);
        user.setCustomerLocations(Arrays.asList(locationsArray));
        user.setCustomerGender(gender);
        signUp(user);

    }

    private void signupRetailer()
    {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String locations = locationsEditText.getText().toString().trim();
        if(locations.endsWith(",")) {
            locations = locations.substring(0, locations.length() - 1);
        }

        String[] locationsArray = locations.split(",");

        // Set up a new Parse user
        LoyaltyUser user = new LoyaltyUser();
        user.setCompanyName(name);
        user.setPassword(password);
        user.setUsername(email);
        user.setCompanyLocations(Arrays.asList(locationsArray));
        user.setUserType(LoyaltyConstants.VALUE_CUSTOMER_TYPE_RETAILER);
        signUp(user);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = LoyaltyConstants.VALUE_CUSTOMER_GENDER_MALE;
                break;
            case R.id.radio_female:
                if (checked)
                    gender = LoyaltyConstants.VALUE_CUSTOMER_GENDER_FEMALE;
                break;
            default:
                gender = "U";
        }
    }

    private class CountrySpinnerListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            String location = parent.getItemAtPosition(pos).toString();
            StringBuilder locationString = new StringBuilder(locationsEditText.getText().toString().trim());
            locationString.append(location).append(",");
            locationsEditText.setText(locationString);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            locationsEditText.setEnabled(false);
        }
    }

 }
