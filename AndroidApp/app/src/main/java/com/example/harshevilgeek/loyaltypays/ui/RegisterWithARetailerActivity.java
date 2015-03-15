package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardItem;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by averghese on 14-Mar-15.
 */
public class RegisterWithARetailerActivity  extends Activity{

    private String userEmail;
    private String companyName;
    private String termsAndCond;
    private int age;
    private String gender;
    private String userFullName;
    private String loyaltyCardId;

    private EditText fullNameET;
    private EditText emailET;
    private EditText ageET;
    private EditText genderET;
    private AutoCompleteTextView locationTV;
    private Button registerButton;
    private CheckBox termsAndConditionsCB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_with_retailer_layout);

        fullNameET = (EditText) findViewById(R.id.reg_with_name);
        emailET = (EditText) findViewById(R.id.user_email);
        ageET = (EditText) findViewById(R.id.user_calculated_age);
        genderET = (EditText) findViewById(R.id.user_gender);
        locationTV = (AutoCompleteTextView) findViewById(R.id.user_location);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.asList(getResources().getStringArray(R.array.country_array)));
        locationTV.setAdapter(locationAdapter);
        registerButton = (Button) findViewById(R.id.register);
        termsAndConditionsCB = (CheckBox) findViewById(R.id.termsAndCondCheckBox);

        termsAndConditionsCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {

                    showTermsAndConditions();

                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 registerUserForCard();
            }
        });

        if(savedInstanceState != null) {
            populateData(savedInstanceState);
        }
        else {
            populateData(getIntent().getExtras());
        }

        initialiseUI();

        getActionBar().setTitle(companyName);

    }

    private void showTermsAndConditions()
    {

        new AlertDialog.Builder(this)
                .setTitle("Terms & Cond...")
                .setMessage(termsAndCond)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void initialiseUI()
    {

        if(!TextUtils.isEmpty(userFullName)) {
            fullNameET.setText(userFullName);
        }
        if(!TextUtils.isEmpty(userEmail)) {
            emailET.setText(userEmail);
            emailET.setEnabled(false);
        }
        if(age > 0) {
            ageET.setText(String.valueOf(age));
        }

        if(!TextUtils.isEmpty(gender)) {
            genderET.setText(gender);
        }
    }

    private void registerUserForCard() {

        userFullName = fullNameET.getText().toString().trim();
        userEmail = emailET.getText().toString().trim();
        gender = genderET.getText().toString().trim();
        age = Integer.parseInt(ageET.getText().toString().trim());
        String location = locationTV.getText().toString().trim();

        ParseUser user = ParseUser.getCurrentUser();

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(RegisterWithARetailerActivity.this);
        dialog.setMessage(getString(R.string.progress_posting_card_type));
        dialog.show();

        LoyaltyCardItem loyaltyCardItem = new LoyaltyCardItem();
        loyaltyCardItem.setUser(user);
        loyaltyCardItem.setCustomerName(userFullName);
        loyaltyCardItem.setCustomerId(user.getObjectId());
        loyaltyCardItem.setLoyaltyCardTypeId(loyaltyCardId);
        loyaltyCardItem.setCompanyName(companyName);
        loyaltyCardItem.setCardLocation(location);
        loyaltyCardItem.setLoyaltyPoints(100);
        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        loyaltyCardItem.setACL(acl);

        // Save the post
        loyaltyCardItem.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                finish();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LoyaltyConstants.KEY_COMPANY_NAME, companyName);
        outState.putString(LoyaltyConstants.KEY_CARD_TERMS, termsAndCond);
        outState.putString(LoyaltyConstants.KEY_LOYALTY_CARD_TYPE_ID, loyaltyCardId);
    }

    private void populateData(Bundle bundle) {
        ParseUser user = ParseUser.getCurrentUser();
        userEmail = user.getUsername();
        userFullName = (String)user.get(LoyaltyConstants.KEY_CUSTOMER_NAME);
        String dob = (String)user.get(LoyaltyConstants.KEY_CUSTOMER_DOB);
        gender = (String)user.get(LoyaltyConstants.KEY_CUSTOMER_GENDER);

        String[] dobParts = dob.split("/");
        int birthYear = Integer.valueOf(dobParts[2]);

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        age = currentYear - birthYear;

        companyName = bundle.getString(LoyaltyConstants.KEY_COMPANY_NAME);
        termsAndCond = bundle.getString(LoyaltyConstants.KEY_CARD_TERMS);
        loyaltyCardId = bundle.getString(LoyaltyConstants.KEY_LOYALTY_CARD_TYPE_ID);

    }
}
