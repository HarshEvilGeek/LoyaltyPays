package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Data model for a post.
 */
@ParseClassName("Posts")
public class LoyaltyCardItem extends ParseObject {
    public void setCompanyName(String companyName) {
        put(LoyaltyConstants.KEY_COMPANY_NAME, companyName);
    }

    public String getCompanyName() {
        return getString(LoyaltyConstants.KEY_COMPANY_NAME);
    }

    public String getCompanyId() {
        return getString("companyID");
    }

    public long getExpiryTime() {
        return getLong("expiryTime");
    }

    public int getLoyaltyPoints() {
        return getInt("loyaltyPoints");
    }

    public ParseFile getCompanyImage() {
        return getParseFile("companyImage");
    }

    public ParseFile getCardQRCode() {
        return getParseFile("loyaltyQRCode");
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser value) {
        put("user", value);
    }

    public static ParseQuery<LoyaltyCardItem> getQuery() {
        return ParseQuery.getQuery(LoyaltyCardItem.class);
    }
}
