package com.example.harshevilgeek.loyaltypays.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;

import com.example.harshevilgeek.loyaltypays.R;
import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardItem;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardPurchases;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardType;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyPromotionsAndDeals;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerMainActivity extends FragmentActivity {

    // Maximum results returned from a Parse query
    private static final int MAX_LOYALTY_CARD_RESULTS = 30;

    private static final String MODE = "MODE";

    private static final String MODE_CARD_ITEMS = "MODE_CARD_ITEMS";
    private static final String MODE_CARD_TYPES = "MODE_CARD_TYPES";
    private static final String MODE_CARD_PURCHASES = "MODE_CARD_PURCHASES";
    private static final String MODE_CARD_PROMOTIONS = "MODE_CARD_PROMOTIONS";

    private String mMode;

    private GridView landingGridView;

    private ParseQueryAdapter<? extends ParseObject> mAdapter;

    private String[] landingOptions = null;
    private DrawerLayout optionsDrawer = null;
    private ListView drawerList = null;

    //http://stackoverflow.com/questions/22116715/action-bar-button-not-opening-drawer
    private LandingOptionsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mMode = savedInstanceState.getString(MODE, MODE_CARD_ITEMS);
        }
        else if (getIntent() != null) {
            mMode = getIntent().getStringExtra(MODE);
        }

        if (mMode == null) {
            mMode = MODE_CARD_ITEMS;
        }

        setContentView(R.layout.activity_customer_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.home_icon);
        optionsDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.main_options_list);
        drawerList.setOnItemClickListener(new DrawerItemClickListener(this));

        drawerList.setAdapter(getAdapter());
        populateData();

        setAdapterAndFactory();

        landingGridView = (GridView) findViewById(R.id.loyalty_grid);

        landingGridView.setAdapter(mAdapter);

        // Set up the handler for an item's selection
        landingGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ParseObject item = mAdapter.getItem(position);
                handleItemClick(item);
            }
        });
    }

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener
    {
        private Context context;
        public DrawerItemClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {
            String mode = MODE_CARD_ITEMS;
            if(position == 0) {
                mode = MODE_CARD_ITEMS;
            }
            else if(position == 1) {
                mode = MODE_CARD_TYPES;
            }
            else if(position == 2) {
                mode = MODE_CARD_PROMOTIONS;
            }
            else if(position == 3) {
                mode = MODE_CARD_PURCHASES;
            }
            Intent activityIntent = new Intent(context, CustomerMainActivity.class );
            activityIntent.putExtra(MODE, mode);
            context.startActivity(activityIntent);
        }
    }

    private void populateData()
    {
        landingOptions = getResources().getStringArray(
                R.array.landing_page_options);
        List<LandingOptionsItem> optionsList = new ArrayList<LandingOptionsItem>();
        if(landingOptions != null) {
            for(String landingOptionName : landingOptions) {
                LandingOptionsItem optionsItem = new LandingOptionsItem();
                optionsItem.setOptionName(landingOptionName);
                optionsItem.setIconId(getIconId(landingOptionName));
                optionsList.add(optionsItem);
            }
        }

        if(optionsList != null && !optionsList.isEmpty()) {

            ((LandingOptionsAdapter)adapter).setData(optionsList);
            adapter.notifyDataSetChanged();
        }
    }

    private void setAdapterAndFactory ()
    {
        if (MODE_CARD_ITEMS.equals(mMode)) {
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
            mAdapter = new ParseQueryAdapter<LoyaltyCardItem>(this, factory) {
                @Override
                public View getItemView(LoyaltyCardItem item, View view, ViewGroup parent) {
                    if (view == null) {
                        view = View.inflate(getContext(), R.layout.customer_loyalty_card_item, null);
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
            mAdapter.setAutoload(false);

            // Disable pagination, we'll manage the query limit ourselves
            mAdapter.setPaginationEnabled(false);

        }
        else if (MODE_CARD_TYPES.equals(mMode)) {
            ParseQueryAdapter.QueryFactory<LoyaltyCardType> factory =
                    new ParseQueryAdapter.QueryFactory<LoyaltyCardType>() {
                        public ParseQuery<LoyaltyCardType> create() {
                            ParseQuery<LoyaltyCardType> query = LoyaltyCardType.getQuery();
                            query.orderByDescending("createdAt");
                            //ParseUser currentUser = ParseUser.getCurrentUser();
                            //List<String> locations = (List<String>) currentUser.get(LoyaltyConstants.KEY_CUSTOMER_LOCATIONS);
                            //String location = "";
                            //if (!locations.isEmpty()) {
                                //location = locations.get(0);
                            //}
                            //query.whereContains(LoyaltyConstants.KEY_CARD_LOCATION,location);
                            query.setLimit(MAX_LOYALTY_CARD_RESULTS);
                            return query;
                        }
                    };

            // Set up the query adapter
            mAdapter = new ParseQueryAdapter<LoyaltyCardType>(this, factory) {
                @Override
                public View getItemView(LoyaltyCardType item, View view, ViewGroup parent) {
                    if (view == null) {
                        view = View.inflate(getContext(), R.layout.customer_loyalty_card_type_item, null);
                    }
                    ImageView imageView = (ImageView) view.findViewById(R.id.loyalty_card_type_image);
                    TextView cardNameView = (TextView) view.findViewById(R.id.card_type_item_name);
                    TextView cardLocationView = (TextView) view.findViewById(R.id.card_type_item_location);

                    cardNameView.setText(item.getCardName());
                    try {
                        cardLocationView.setText(item.getCardLocations().toString());
                    }
                    catch (Exception e) {

                    }
                    return view;
                }
            };

            // Disable automatic loading when the adapter is attached to a view.
            mAdapter.setAutoload(false);

            // Disable pagination, we'll manage the query limit ourselves
            mAdapter.setPaginationEnabled(false);

        }
        else if (MODE_CARD_PURCHASES.equals(mMode)) {
            ParseQueryAdapter.QueryFactory<LoyaltyCardPurchases> factory =
                    new ParseQueryAdapter.QueryFactory<LoyaltyCardPurchases>() {
                        public ParseQuery<LoyaltyCardPurchases> create() {
                            ParseQuery<LoyaltyCardPurchases> query = LoyaltyCardPurchases.getQuery();
                            query.include(LoyaltyConstants.KEY_USER);
                            query.orderByDescending("createdAt");
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            query.whereEqualTo(LoyaltyConstants.KEY_USER, currentUser);
                            query.setLimit(MAX_LOYALTY_CARD_RESULTS);
                            return query;
                        }
                    };

            // Set up the query adapter
            mAdapter = new ParseQueryAdapter<LoyaltyCardPurchases>(this, factory) {
                @Override
                public View getItemView(LoyaltyCardPurchases item, View view, ViewGroup parent) {
                    if (view == null) {
                        view = View.inflate(getContext(), R.layout.customer_loyalty_purchase_item, null);
                    }
                    ImageView imageView = (ImageView) view.findViewById(R.id.purchase_company_image);
                    TextView purchaseAmount = (TextView) view.findViewById(R.id.purchase_amount);
                    TextView purchaseDate = (TextView) view.findViewById(R.id.purchase_date);

                    purchaseAmount.setText(item.getPurchaseAmount() + item.getPurchaseCurrency());
                    Date date = new Date(item.getPurchaseTime());
                    purchaseDate.setText(date.toString());
                    return view;
                }
            };

            // Disable automatic loading when the adapter is attached to a view.
            mAdapter.setAutoload(false);

            // Disable pagination, we'll manage the query limit ourselves
            mAdapter.setPaginationEnabled(false);

        }
        else if (MODE_CARD_PROMOTIONS.equals(mMode)) {
            ParseQueryAdapter.QueryFactory<LoyaltyPromotionsAndDeals> factory =
                    new ParseQueryAdapter.QueryFactory<LoyaltyPromotionsAndDeals>() {
                        public ParseQuery<LoyaltyPromotionsAndDeals> create() {
                            ParseQuery<LoyaltyPromotionsAndDeals> query = LoyaltyPromotionsAndDeals.getQuery();
                            query.include(LoyaltyConstants.KEY_USER);
                            query.orderByDescending("createdAt");
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            List<String> locations = (List<String>) currentUser.get(LoyaltyConstants.KEY_CUSTOMER_LOCATIONS);
                            String location = "";
                            if (!locations.isEmpty()) {
                                location = locations.get(0);
                            }
                            query.whereContains(LoyaltyConstants.KEY_CARD_LOCATION, location);
                            query.setLimit(MAX_LOYALTY_CARD_RESULTS);
                            return query;
                        }
                    };

            // Set up the query adapter
            mAdapter = new ParseQueryAdapter<LoyaltyPromotionsAndDeals>(this, factory) {
                @Override
                public View getItemView(LoyaltyPromotionsAndDeals item, View view, ViewGroup parent) {
                    if (view == null) {
                        view = View.inflate(getContext(), R.layout.customer_loyalty_promotion_item, null);
                    }
                    ImageView imageView = (ImageView) view.findViewById(R.id.loyalty_promotion_image);
                    TextView promotionNameView = (TextView) view.findViewById(R.id.promotion_item_name);
                    TextView promotionLocationView = (TextView) view.findViewById(R.id.promotion_item_location);

                    promotionNameView.setText(item.getPromotionName());
                    promotionLocationView.setText(item.getPromotionLocations().toString());
                    return view;
                }
            };

            // Disable automatic loading when the adapter is attached to a view.
            mAdapter.setAutoload(false);

            // Disable pagination, we'll manage the query limit ourselves
            mAdapter.setPaginationEnabled(false);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(optionsDrawer.isDrawerOpen(drawerList)) {
                    optionsDrawer.closeDrawer(drawerList);
                }
                else {
                    optionsDrawer.openDrawer(drawerList);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getIconId(String optionName) {

        int resId = -1;
        if(getResources().getString(R.string.option_register_with_retailer).equals(optionName)) {
            return R.drawable.register_retailer;
        }
        else if(getResources().getString(R.string.option_usage_history).equals(optionName)) {
            return R.drawable.my_history;
        }
        else if(getResources().getString(R.string.option_view_promotions).equals(optionName)) {
            return R.drawable.deals_promotions;
        }
        else if(getResources().getString(R.string.option_view_registered_retailers).equals(optionName)) {
            return R.drawable.my_loyalty_cards;
        }
        else if(getResources().getString(R.string.option_my_deals).equals(optionName)) {
            return R.drawable.my_deals;
        }
        return resId;
    }

    private LandingOptionsAdapter getAdapter() {
        if(adapter == null) {
            adapter = new LandingOptionsAdapter(null , this);
        }
        return adapter;
    }

    private void doListQuery() {
        mAdapter.loadObjects();
    }

    private void handleItemClick (ParseObject item) {

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

    private class LandingOptionsAdapter extends BaseAdapter {

        private List<LandingOptionsItem> optionsList;
        private Context context;

        public LandingOptionsAdapter(List<LandingOptionsItem> optionsList,
                                     Context context) {
            this.optionsList = optionsList;
            this.context = context;
        }

        public void setData(List<LandingOptionsItem> optionsList) {
            this.optionsList = optionsList;

        }

        @Override
        public int getCount() {
            if (optionsList != null) {
                return optionsList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (position >= 0 && position < optionsList.size()) {
                return optionsList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LandingOptionsItem optionItem = (LandingOptionsItem) getItem(position);
            if(optionItem == null) {
                return null;
            }
            View rowView = layoutInflater.inflate(
                    R.layout.landing_drawer_list_item, parent, false);

            TextView textView = (TextView) rowView
                    .findViewById(R.id.option_name);
            textView.setLines(1);
            ImageView imageView = (ImageView) rowView
                    .findViewById(R.id.option_icon);

            textView.setText(optionItem.getOptionName());
            imageView.setImageResource(optionItem.getIconId());

            //set item click listener

            return rowView;
        }

    }

    private class LandingOptionsItem {

        private String optionName;
        private int iconId;

        public LandingOptionsItem() {
            this.optionName = optionName;
            this.iconId = iconId;

        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MODE, mMode);
    }
}
