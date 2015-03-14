package com.example.harshevilgeek.loyaltypays;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardItem;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyCardPurchases;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyDiscountsAndCoupons;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyFeedback;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyPromotionsAndDiscounts;
import com.example.harshevilgeek.loyaltypays.dao.LoyaltyUser;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Application extends android.app.Application {
    // Debugging switch
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "LoyaltyPays";

    // Used to pass location from MainActivity to PostActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

    private static SharedPreferences preferences;

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ParseUser.registerSubclass(LoyaltyUser.class);
        ParseObject.registerSubclass(LoyaltyCardItem.class);
        ParseObject.registerSubclass(LoyaltyCardPurchases.class);
        ParseObject.registerSubclass(LoyaltyDiscountsAndCoupons.class);
        ParseObject.registerSubclass(LoyaltyFeedback.class);
        ParseObject.registerSubclass(LoyaltyPromotionsAndDiscounts.class);
        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_key));

        preferences = getSharedPreferences("com.example.harshevilgeek.loyaltypays", Context.MODE_PRIVATE);
    }

}
