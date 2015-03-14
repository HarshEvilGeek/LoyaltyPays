package com.example.harshevilgeek.loyaltypays.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardPurchases;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by akhil on 3/14/15.
 */
public class RetailerPurchasePostActivity extends Activity {
    private EditText cardIdEditText;
    private EditText purchaseNameEditText;
    private EditText purchaseAmountEditText;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase);

        cardIdEditText = (EditText) findViewById(R.id.card_id);
        purchaseNameEditText = (EditText) findViewById(R.id.purchase_name);
        purchaseAmountEditText = (EditText) findViewById(R.id.purchase_amount_view);

        addButton = (Button) findViewById(R.id.purchase_post_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                post();
            }
        });
    }

    private void post() {
        String cardId = cardIdEditText.getText().toString().trim();
        String purchaseName = purchaseNameEditText.getText().toString().trim();
        String purchaseAmount = purchaseAmountEditText.getText().toString().trim();
        int amount;

        if (cardId.isEmpty() || purchaseName.isEmpty()) {
            Toast.makeText(this, "Invalid purchase", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            amount = Integer.valueOf(purchaseAmount);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid purchase", Toast.LENGTH_LONG).show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(RetailerPurchasePostActivity.this);
        dialog.setMessage(getString(R.string.progress_posting_card_type));
        dialog.show();

        // Create a post.
        LoyaltyCardPurchases loyaltyCardPurchases = new LoyaltyCardPurchases();

        // Set the location to the current user's location
        loyaltyCardPurchases.setPurchaseCardID(cardId);
        loyaltyCardPurchases.setPurchaseName(purchaseName);
        loyaltyCardPurchases.setPurchaseAmount(amount);
        loyaltyCardPurchases.setUser(ParseUser.getCurrentUser());
        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        loyaltyCardPurchases.setACL(acl);

        // Save the post
        loyaltyCardPurchases.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                finish();
            }
        });

    }

}
