package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyUser;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Activity which displays a login screen to the user.
 */
public class SignUpActivity extends Activity {
    // UI references.
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private EditText companyNameEditText;
    private EditText dobEditText;
    private RadioGroup genderRadioButtonGroup;
    private String gender;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        // Set up the signup form.
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        companyNameEditText = (EditText)findViewById(R.id.company_name_edit_text);
        dobEditText = (EditText)findViewById(R.id.dob_edit_text);
        genderRadioButtonGroup = (RadioGroup) findViewById(R.id.gender_radio);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        passwordAgainEditText = (EditText) findViewById(R.id.password_again_edit_text);
        passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.edittext_action_signup ||
                        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    signup();
                    return true;
                }
                return false;
            }
        });

        // Set up the submit button click handler
        Button mActionButton = (Button) findViewById(R.id.action_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signup();
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
            companyNameEditText.setVisibility(View.GONE);

        }
        else if(LoyaltyConstants.VALUE_CUSTOMER_TYPE_RETAILER.equals(userType)) {
            dobEditText.setVisibility(View.GONE);
            genderRadioButtonGroup.setVisibility(View.GONE);
            companyNameEditText.setVisibility(View.VISIBLE);

        }
    }

    private void signup() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();

        // Validate the sign up data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        if (!password.equals(passwordAgain)) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage(getString(R.string.progress_signup));
        dialog.show();

        // Set up a new Parse user
        LoyaltyUser user = new LoyaltyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserType(LoyaltyConstants.VALUE_CUSTOMER_TYPE_CONSUMER);

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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = "M";
                break;
            case R.id.radio_female:
                if (checked)
                    gender = "F";
                break;
            default:
                gender = "U";
        }
    }
}
