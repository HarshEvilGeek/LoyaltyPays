package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;

/**
 * Activity which displays a registration screen to the user.
 */
public class WelcomeActivity extends Activity {

    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(savedInstanceState != null) {
            populateOnRecreate(savedInstanceState);
        }
        // Log in button click handler
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(mode)) {
                    Toast.makeText(WelcomeActivity.this, "Please select a user type before continuing...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent loginActivityIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    loginActivityIntent.putExtra(LoyaltyConstants.KEY_USER_TYPE, mode);
                    // Starts an intent of the log in activity
                    startActivity(loginActivityIntent);
                }
            }
        });

        // Sign up button click handler
        Button signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mode)) {
                    Toast.makeText(WelcomeActivity.this, "Please select a user type before continuing...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent signupActivityIntent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                    signupActivityIntent.putExtra(LoyaltyConstants.KEY_USER_TYPE, mode);
                    // Starts an intent for the sign up activity
                    startActivity(signupActivityIntent);
                }
            }
        });

    }

    private void populateOnRecreate(Bundle savedInstanceState)
    {
        mode = savedInstanceState.getString(LoyaltyConstants.KEY_USER_TYPE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LoyaltyConstants.KEY_USER_TYPE, mode);
        super.onSaveInstanceState(outState);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_customer:
                if (checked)
                    mode = LoyaltyConstants.VALUE_CUSTOMER_TYPE_CONSUMER;
                break;
            case R.id.radio_female:
                if (checked)
                    mode = LoyaltyConstants.VALUE_CUSTOMER_TYPE_RETAILER;
                break;

        }
    }
}
