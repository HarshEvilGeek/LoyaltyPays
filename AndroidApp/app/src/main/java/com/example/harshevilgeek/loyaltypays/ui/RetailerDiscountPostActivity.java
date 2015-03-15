package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardItem;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardPurchases;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyDiscountsAndCoupons;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by akhil on 3/14/15.
 */
public class RetailerDiscountPostActivity extends Activity {
    public static final String CARD_ID_KEY = "CARD_ID_KEY";

    private String cardTypeID;
    private EditText discountNameEditText;
    private EditText discountTermsEditText;
    private EditText minLoyaltyEditText;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discount);

        if (getIntent() != null) {
            cardTypeID = getIntent().getStringExtra(CARD_ID_KEY);
        }

        if (cardTypeID == "") {
            Toast.makeText(this, "Invalid card", Toast.LENGTH_LONG).show();
            finish();
        }
        discountNameEditText = (EditText) findViewById(R.id.discount_name_edit_text);
        discountTermsEditText = (EditText) findViewById(R.id.discount_terms_edit_text);
        minLoyaltyEditText = (EditText) findViewById(R.id.discount_min_loyalty_edit_text);

        addButton = (Button) findViewById(R.id.purchase_post_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                post();
            }
        });
    }

    private void post() {
        final String discountName = discountNameEditText.getText().toString().trim();
        final String discountTerms = discountTermsEditText.getText().toString().trim();
        String minPoints = minLoyaltyEditText.getText().toString().trim();
        int mPoints;

        if (discountName.isEmpty() || discountTerms.isEmpty()) {
            Toast.makeText(this, "Invalid discount", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            mPoints = Integer.valueOf(minPoints);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid discount", Toast.LENGTH_LONG).show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(RetailerDiscountPostActivity.this);
        dialog.setMessage(getString(R.string.progress_posting_card_type));
        dialog.show();

        LoyaltyDiscountsAndCoupons loyaltyDiscountsAndCoupons = new LoyaltyDiscountsAndCoupons();

        // Set the location to the current user's location
        String companyName = (String) ParseUser.getCurrentUser().get(LoyaltyConstants.KEY_COMPANY_NAME);
        loyaltyDiscountsAndCoupons.setLoyaltyCardTypeId(cardTypeID);
        loyaltyDiscountsAndCoupons.setDiscountName(discountName);
        loyaltyDiscountsAndCoupons.setDiscountText(discountTerms);
        loyaltyDiscountsAndCoupons.setDiscountMinPoints(mPoints);
        loyaltyDiscountsAndCoupons.setUser(ParseUser.getCurrentUser());
        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        loyaltyDiscountsAndCoupons.setACL(acl);

        loyaltyDiscountsAndCoupons.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                finish();
            }
        });

    }

}
