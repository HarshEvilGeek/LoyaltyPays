package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseUser;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class DispatchActivity extends Activity {

    public DispatchActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null) {
            // Start an intent for the logged in activity
            if (user.get(LoyaltyConstants.KEY_USER_TYPE).equals(LoyaltyConstants.VALUE_CUSTOMER_TYPE_RETAILER)) {
                startActivity(new Intent(this, RetailerMainActivity.class));
            } else {
                startActivity(new Intent(this, CustomerMainActivity.class));

            }
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

}
