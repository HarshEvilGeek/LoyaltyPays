package com.example.harshevilgeek.loyaltypays.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardItem;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class CustomerMainActivity extends FragmentActivity {

    // Maximum results returned from a Parse query
    private static final int MAX_LOYALTY_CARD_RESULTS = 30;

    private GridView loyaltyCardGridView;

    private LinearLayout modifyLayout;

    // Adapter for the Parse query
    private ParseQueryAdapter<LoyaltyCardItem> postsQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_main);


        ParseQueryAdapter.QueryFactory<LoyaltyCardItem> factory =
                new ParseQueryAdapter.QueryFactory<LoyaltyCardItem>() {
                    public ParseQuery<LoyaltyCardItem> create() {
                        ParseQuery<LoyaltyCardItem> query = LoyaltyCardItem.getQuery();
                        query.include(LoyaltyConstants.KEY_USER);
                        query.orderByDescending("createdAt");
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        query.whereEqualTo(LoyaltyConstants.KEY_USER, currentUser);
                        query.setLimit(MAX_LOYALTY_CARD_RESULTS);
                        return query;
                    }
                };

        // Set up the query adapter
        postsQueryAdapter = new ParseQueryAdapter<LoyaltyCardItem>(this, factory) {
            @Override
            public View getItemView(LoyaltyCardItem item, View view, ViewGroup parent) {
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.loyalty_card_item, null);
                }
                ImageView imageView = (ImageView) view.findViewById(R.id.loyalty_card_image);
                TextView companyNameView = (TextView) view.findViewById(R.id.company_name);
                TextView loyaltyPointsView = (TextView) view.findViewById(R.id.loyalty_points);

                final String id = item.getObjectId();

                final String companyName = item.getCompanyName();
                final int loyaltyPoints = item.getLoyaltyPoints();

                companyNameView.setText(companyName);
                loyaltyPointsView.setText(loyaltyPoints);
                return view;
            }
        };

        // Disable automatic loading when the adapter is attached to a view.
        postsQueryAdapter.setAutoload(false);

        // Disable pagination, we'll manage the query limit ourselves
        postsQueryAdapter.setPaginationEnabled(false);

        loyaltyCardGridView = (GridView) findViewById(R.id.loyalty_grid);
        
        loyaltyCardGridView.setAdapter(postsQueryAdapter);

        // Set up the handler for an item's selection
        loyaltyCardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LoyaltyCardItem item = postsQueryAdapter.getItem(position);
                handleItemClick(item);
            }
        });
    }

    private void doListQuery() {
        postsQueryAdapter.loadObjects();
    }

    private void handleItemClick (LoyaltyCardItem item) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        doListQuery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(CustomerMainActivity.this, SettingsActivity.class));
                return true;
            }
        });
        return true;
    }
}
