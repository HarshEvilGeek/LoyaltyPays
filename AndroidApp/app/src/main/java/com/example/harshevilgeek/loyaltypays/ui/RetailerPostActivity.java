package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardType;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyPromotionsAndDeals;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 3/14/15.
 */

public class RetailerPostActivity extends Activity {

    public static final String ACTION = "ACTION";
    public static final String PROMOTION_ACTION = "PROMOTION_ACTION";
    public static final String CARD_TYPE_ACTION = "CARD_TYPE_ACTION";
    public static final String ADD_NEW_ITEM = "ADD_NEW_ITEM";
    public static final String EDIT_ITEM = "EDIT_ITEM";
    public static final String OBJECT_ID = "OBJECT_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_LOCATIONS = "ITEM_LOCATIONS";
    public static final String ITEM_TERMS = "ITEM_TERMS";


    private static final int MAX_CHARACTER_COUNT = 20;

    String mPostAction = CARD_TYPE_ACTION;

    // UI references.
    private RadioGroup modeGroup;
    private EditText nameEditText;
    private AutoCompleteTextView locationTextView;
    private EditText termsEditText;
    private Button addButton;
    private String action;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retailer_post);

        modeGroup = (RadioGroup) findViewById(R.id.post_action_radio);
        nameEditText = (EditText) findViewById(R.id.post_name);
        termsEditText = (EditText) findViewById(R.id.post_terms);

        ParseUser user = ParseUser.getCurrentUser();
        List<String> locations = (List<String>) user.get(LoyaltyConstants.KEY_COMPANY_LOCATIONS);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, locations);
        locationTextView = (AutoCompleteTextView) findViewById(R.id.company_locations_spinner);
        locationTextView.setAdapter(adapter);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateAddButtonState();
            }
        });

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
            locationTextView.setText(intent.getStringExtra(ITEM_LOCATIONS));
            termsEditText.setText(intent.getStringExtra(ITEM_TERMS));
            objectId = intent.getStringExtra(OBJECT_ID);
            addButton.setText(getString(R.string.post_card_type));
        } else {
            action = ADD_NEW_ITEM;
        }
    }

    private void post() {
        String name = nameEditText.getText().toString().trim();
        String location = locationTextView.getText().toString();
        String terms = termsEditText.getText().toString().trim();

        List<String> locationList = new ArrayList<String>();
        locationList.add(location);
        ParseUser user = ParseUser.getCurrentUser();
        List<String> locations = (List<String>) user.get(LoyaltyConstants.KEY_COMPANY_LOCATIONS);

        if (!locations.contains(location)) {
            Toast.makeText(this, "Invalid location", Toast.LENGTH_LONG).show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(RetailerPostActivity.this);
        dialog.setMessage(getString(R.string.progress_posting_card_type));
        dialog.show();

        // Create a post.
        if (mPostAction.equals(CARD_TYPE_ACTION)) {
            LoyaltyCardType loyaltyCardType = new LoyaltyCardType();

            // Set the location to the current user's location
            if (EDIT_ITEM.equals(action)) {
                loyaltyCardType.setObjectId(objectId);
            }
            loyaltyCardType.setCardName(name);
            loyaltyCardType.setCardLocations(locationList.toString());
            loyaltyCardType.setCardTerms(terms);
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
        } else {
            LoyaltyPromotionsAndDeals loyaltyPromotionsAndDeals = new LoyaltyPromotionsAndDeals();

            // Set the location to the current user's location
            if (EDIT_ITEM.equals(action)) {
                loyaltyPromotionsAndDeals.setObjectId(objectId);
            }
            loyaltyPromotionsAndDeals.setPromotionName(name);
            loyaltyPromotionsAndDeals.setPromotionLocations(locationList);
            loyaltyPromotionsAndDeals.setPromotionText(terms);
            loyaltyPromotionsAndDeals.setUser(ParseUser.getCurrentUser());
            ParseACL acl = new ParseACL();

            // Give public read access
            acl.setPublicReadAccess(true);
            acl.setPublicWriteAccess(true);
            loyaltyPromotionsAndDeals.setACL(acl);

            // Save the post
            loyaltyPromotionsAndDeals.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    dialog.dismiss();
                    finish();
                }
            });

        }
    }

    private String getNameEditTextText() {
        return nameEditText.getText().toString().trim();
    }

    private void updateAddButtonState() {
        int length = getNameEditTextText().length();
        boolean enabled = length > 0 && length < MAX_CHARACTER_COUNT;
        addButton.setEnabled(enabled);
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_card_type:
                if (checked)
                    mPostAction = CARD_TYPE_ACTION;
                break;
            case R.id.radio_promotion:
                if (checked)
                    mPostAction = PROMOTION_ACTION;
                break;
            default:
                mPostAction = CARD_TYPE_ACTION;
        }
    }
}
