package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Data model for a Loyalty Cards.
 */
@ParseClassName("LoyaltyCards")
public class LoyaltyCardItem extends ParseObject {

    public String getCompanyId() {
        return getString(LoyaltyConstants.KEY_COMPANY_ID);
    }

    public void setCompanyId(String companyId) {
        put(LoyaltyConstants.KEY_COMPANY_ID, companyId);
    }

    public String getCompanyName() {
        return getString(LoyaltyConstants.KEY_COMPANY_NAME);
    }

    public void setCompanyName(String companyName) {
        put(LoyaltyConstants.KEY_COMPANY_NAME, companyName);
    }

    public ParseFile getCompanyImage() {
        return getParseFile(LoyaltyConstants.KEY_COMPANY_IMAGE);
    }

    public void setCompanyImage(ParseFile companyImage) {
        put(LoyaltyConstants.KEY_COMPANY_IMAGE, companyImage);
    }

    public List<String> getCompanyLocations() {
        return getList(LoyaltyConstants.KEY_COMPANY_LOCATIONS);
    }

    public void setCompanyLocations(List<String> companyLocations) {
        put(LoyaltyConstants.KEY_COMPANY_LOCATIONS, companyLocations);
    }

    public String getCustomerName() {
        return getString(LoyaltyConstants.KEY_CUSTOMER_NAME);
    }

    public void setCustomerName(String customerName) {
        put(LoyaltyConstants.KEY_CUSTOMER_NAME, customerName);
    }

    public String getCustomerId() {
        return getString(LoyaltyConstants.KEY_CUSTOMER_ID);
    }

    public void setCustomerId(String customerId) {
        put(LoyaltyConstants.KEY_CUSTOMER_ID, customerId);
    }

    public ParseFile getCustomerImage() {
        return getParseFile(LoyaltyConstants.KEY_CUSTOMER_IMAGE);
    }

    public void setCustomerImage(ParseFile customerImage) {
        put(LoyaltyConstants.KEY_CUSTOMER_IMAGE, customerImage);
    }

    public List<String> getCustomerLocations() {
        return getList(LoyaltyConstants.KEY_CUSTOMER_LOCATIONS);
    }

    public void setCustomerLocations(List<String> customerLocations) {
        put(LoyaltyConstants.KEY_CUSTOMER_LOCATIONS, customerLocations);
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
