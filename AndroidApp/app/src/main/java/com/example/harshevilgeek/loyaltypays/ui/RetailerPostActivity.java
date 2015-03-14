package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardType;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by akhil on 3/14/15.
 */

public class RetailerPostActivity extends Activity {

    public static final String ACTION = "ACTION";
    public static final String ADD_NEW_ITEM = "ADD_NEW_ITEM";
    public static final String EDIT_ITEM = "EDIT_ITEM";
    public static final String OBJECT_ID = "OBJECT_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_TIME_ZONE = "ITEM_TIME_ZONE";


    private static final int MAX_CHARACTER_COUNT = 20;

    // UI references.
    private EditText nameEditText;
    private AutoCompleteTextView locationTextView;
    private Button addButton;
    private String action;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retailer_post);

        nameEditText = (EditText) findViewById(R.id.card_type_post_name);
        ParseUser user = ParseUser.getCurrentUser();
        List<String> locations = (List<String>) user.get(LoyaltyConstants.KEY_COMPANY_LOCATIONS);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, locations);
        locationTextView = (AutoCompleteTextView) findViewById(R.id.company_locations_spinner);
        locationTextView.setAdapter(adapter);

        addButton = (Button) findViewById(R.id.post_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                post();
            }
        });

        updateAddButtonState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        action = intent.getStringExtra(ACTION);

        if (EDIT_ITEM.equals(action)) {
            nameEditText.setText(intent.getStringExtra(ITEM_NAME));
            locationTextView.setText(intent.getStringExtra(ITEM_TIME_ZONE));
            objectId = intent.getStringExtra(OBJECT_ID);
            addButton.setText(getString(R.string.post_card_type));
        }
        else {
            action = ADD_NEW_ITEM;
        }
    }

    private void post() {
        String name = nameEditText.getText().toString().trim();
        String location = locationTextView.getText().toString();

        List<String> locationList = new ArrayList<String>();
        locationList.add(location);
        ParseUser user = ParseUser.getCurrentUser();
        List<String> locations = (List<String>) user.get(LoyaltyConstants.KEY_COMPANY_LOCATIONS);

        if (!locations.contains(location)) {
            Toast.makeText(this, "Invalid time zone", Toast.LENGTH_LONG).show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(RetailerPostActivity.this);
        dialog.setMessage(getString(R.string.progress_posting_card_type));
        dialog.show();

        // Create a post.
        LoyaltyCardType loyaltyCardType = new LoyaltyCardType();

        // Set the location to the current user's location
        if (EDIT_ITEM.equals(action)) {
            loyaltyCardType.setObjectId(objectId);
        }
        loyaltyCardType.setCompanyName(name);
        loyaltyCardType.setCompanyLocations(locationList);
        loyaltyCardType.setUser(ParseUser.getCurrentUser());
        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        loyaltyCardType.setACL(acl);

        // Save the post
        loyaltyCardType.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private String getNameEditTextText() {
        return nameEditText.getText().toString().trim();
    }

    private void updateAddButtonState() {
        int length = getNameEditTextText().length();
        boolean enabled = length > 0 && length < MAX_CHARACTER_COUNT;
        addButton.setEnabled(enabled);
    }
}
