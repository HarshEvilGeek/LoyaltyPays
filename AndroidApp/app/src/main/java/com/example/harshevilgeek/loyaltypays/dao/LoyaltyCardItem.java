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

    public String getCardLocation() {
        return getString(LoyaltyConstants.KEY_CARD_LOCATION);
    }

    public void setCardLocation(String cardLocation) {
        put(LoyaltyConstants.KEY_CARD_LOCATION, cardLocation);
    }

    public ParseFile getQRCode() {
        return getParseFile(LoyaltyConstants.KEY_LOYALTY_QR_CODE);
    }

    public void setQRCode(ParseFile qrCode) {
        put(LoyaltyConstants.KEY_LOYALTY_QR_CODE, qrCode);
    }

    public String getLoyaltyCardId() {
        return getString(LoyaltyConstants.KEY_LOYALTY_CARD_ID);
    }

    public void setLoyaltyCardId(String loyaltyCardId) {
        put(LoyaltyConstants.KEY_LOYALTY_CARD_ID, loyaltyCardId);
    }

    public int getLoyaltyPoints() {
        return getInt(LoyaltyConstants.KEY_LOYALTY_POINTS);
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        put(LoyaltyConstants.KEY_LOYALTY_POINTS, loyaltyPoints);
    }

    public long getExpiryTime() {
        return getInt(LoyaltyConstants.KEY_EXPIRY_TIME);
    }

    public void setExpiryTime(long expiryTime) {
        put(LoyaltyConstants.KEY_EXPIRY_TIME, expiryTime);
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
