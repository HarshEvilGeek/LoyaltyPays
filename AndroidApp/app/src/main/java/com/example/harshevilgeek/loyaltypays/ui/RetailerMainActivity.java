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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardType;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class RetailerMainActivity extends FragmentActivity {

    // Maximum results returned from a Parse query
    private static final int MAX_POST_SEARCH_RESULTS = 20;

    private String selectedTimeZone;
    private String selectedItem = null;
    private String selectedItemName;
    private String selectedItemTimeZone;

    private ListView loyaltyCardTypeListView;
    private Button addNewButton;

    private Button editButton;
    private Button deleteButton;

    private LinearLayout modifyLayout;

    // Adapter for the Parse query
    private ParseQueryAdapter<LoyaltyCardType> postsQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retailer_main);

        loyaltyCardTypeListView = (ListView) findViewById(R.id.loyalty_card_type_list_view);
        addNewButton = (Button) findViewById(R.id.add_new_button);

        modifyLayout = (LinearLayout) findViewById(R.id.modify_layout);
        editButton = (Button) findViewById(R.id.edit_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        addNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(RetailerMainActivity.this, RetailerPostActivity.class);
                intent.putExtra(RetailerPostActivity.ACTION, RetailerPostActivity.ADD_NEW_ITEM);
                startActivity(intent);
            }
        });


        ParseQueryAdapter.QueryFactory<LoyaltyCardType> factory =
                new ParseQueryAdapter.QueryFactory<LoyaltyCardType>() {
                    public ParseQuery<LoyaltyCardType> create() {
                        ParseQuery<LoyaltyCardType> query = LoyaltyCardType.getQuery();
                        query.include("user");
                        query.orderByDescending("createdAt");
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        query.whereEqualTo("user", currentUser);
                        query.setLimit(MAX_POST_SEARCH_RESULTS);
                        return query;
                    }
                };

        // Set up the query adapter
        postsQueryAdapter = new ParseQueryAdapter<LoyaltyCardType>(this, factory) {
            @Override
            public View getItemView(LoyaltyCardType cardType, View view, ViewGroup parent) {
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.loyalty_card_type_item, null);
                }
                TextView cardTypeName = (TextView) view.findViewById(R.id.card_type_name);
                TextView cardTypeLocation = (TextView) view.findViewById(R.id.card_type_location);
                CheckBox cardTypeCheckBox = (CheckBox) view.findViewById(R.id.loyalty_card_type_checkbox);

                final String id = cardType.getObjectId();
                if (id.equals(selectedItem)) {
                    cardTypeCheckBox.setChecked(true);
                }
                else {
                    cardTypeCheckBox.setChecked(false);
                }

                final String name = cardType.getCompanyName();
                final String locationList = cardType.getCompanyLocations().toString();

                cardTypeName.setText(name);
                cardTypeLocation.setText(locationList);

                cardTypeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (selectedItem != null && !selectedItem.equals(id)) {
                            selectedItem = null;
                        } else if (isChecked) {
                            selectedItem = id;
                            selectedItemName = name;
                        } else {
                            selectedItem = null;
                        }
                        updateModifiable();
                        notifyDataSetChanged();
                    }
                });

                return view;
            }
        };

        // Disable automatic loading when the adapter is attached to a view.
        postsQueryAdapter.setAutoload(false);

        // Disable pagination, we'll manage the query limit ourselves
        postsQueryAdapter.setPaginationEnabled(false);

        loyaltyCardTypeListView.setAdapter(postsQueryAdapter);

        // Set up the handler for an item's selection
        loyaltyCardTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LoyaltyCardType item = postsQueryAdapter.getItem(position);
                selectedItem = item.getObjectId();
                handleItemClick(item);
            }
        });
    }

    private void handleItemClick (LoyaltyCardType item) {

    }

    private void doListQuery() {
        postsQueryAdapter.loadObjects();
    }

    private void updateModifiable() {
        if (selectedItem != null) {
            modifyLayout.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RetailerMainActivity.this, RetailerPostActivity.class);
                    intent.putExtra(RetailerPostActivity.ACTION, RetailerPostActivity.EDIT_ITEM);
                    intent.putExtra(RetailerPostActivity.ITEM_NAME, selectedItemName);
                    intent.putExtra(RetailerPostActivity.ITEM_LOCATIONS, selectedItemTimeZone);
                    intent.putExtra(RetailerPostActivity.OBJECT_ID, selectedItem);
                    selectedItem = null;
                    updateModifiable();
                    startActivity(intent);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set up a progress dialog
                    final ProgressDialog dialog = new ProgressDialog(RetailerMainActivity.this);
                    dialog.setMessage(getString(R.string.progress_card_type_delete));
                    dialog.show();

                    LoyaltyCardType post = new LoyaltyCardType();
                    post.setObjectId(selectedItem);

                    // Save the post
                    post.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            dialog.dismiss();
                            selectedItem = null;
                            doListQuery();
                            updateModifiable();
                        }
                    });
                }
            });
        }
        else {
            modifyLayout.setVisibility(View.GONE);
        }
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
                startActivity(new Intent(RetailerMainActivity.this, SettingsActivity.class));
                return true;
            }
        });
        return true;
    }
}
