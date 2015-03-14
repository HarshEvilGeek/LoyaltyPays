package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardItem;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class PostActivity extends Activity {

    public static final String ACTION = "ACTION";
    public static final String ADD_NEW_ITEM = "ADD_NEW_ITEM";
    public static final String EDIT_ITEM = "EDIT_ITEM";
    public static final String OBJECT_ID = "OBJECT_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_TIME_ZONE = "ITEM_TIME_ZONE";


    private static final int MAX_CHARACTER_COUNT = 20;

    // UI references.
    private EditText nameEditText;
    private AutoCompleteTextView timeZoneSpinner;
    private Button addButton;
    private String action;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        nameEditText = (EditText) findViewById(R.id.item_name);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, TimeZone.getAvailableIDs());
        timeZoneSpinner = (AutoCompleteTextView) findViewById(R.id.item_timezone);
        timeZoneSpinner.setAdapter(adapter);

        addButton = (Button) findViewById(R.id.post_button);
        addButton.setOnClickListener(new OnClickListener() {
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
            timeZoneSpinner.setText(intent.getStringExtra(ITEM_TIME_ZONE));
            objectId = intent.getStringExtra(OBJECT_ID);
            addButton.setText(getString(R.string.edit_button_text));
        }
        else {
            action = ADD_NEW_ITEM;
        }
    }

    private void post() {
        String name = nameEditText.getText().toString().trim();
        String timezone = timeZoneSpinner.getText().toString();

        if (!Arrays.asList(TimeZone.getAvailableIDs()).contains(timezone)) {
            Toast.makeText(this, "Invalid time zone", Toast.LENGTH_LONG).show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(PostActivity.this);
        dialog.setMessage(getString(R.string.progress_add));
        dialog.show();

        // Create a post.
        LoyaltyCardItem post = new LoyaltyCardItem();

        // Set the location to the current user's location
        if (EDIT_ITEM.equals(action)) {
            post.setObjectId(objectId);
        }
        post.setCustomerName(name);
        post.setCardLocation(timezone);
        post.setUser(ParseUser.getCurrentUser());
        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        post.setACL(acl);

        // Save the post
        post.saveInBackground(new SaveCallback() {
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
