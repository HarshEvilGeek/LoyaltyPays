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
@ParseClassName("LoyaltyCardItem")
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

    public String getCardLocations() {
        return getString(LoyaltyConstants.KEY_CARD_LOCATION);
    }

    public void setCardLocations(String cardLocations) {
        put(LoyaltyConstants.KEY_CARD_LOCATION, cardLocations);
    }

    public ParseFile getQRCode() {
        return getParseFile(LoyaltyConstants.KEY_LOYALTY_QR_CODE);
    }

    public void setQRCode(ParseFile qrCode) {
        put(LoyaltyConstants.KEY_LOYALTY_QR_CODE, qrCode);
    }

    public String getLoyaltyCardTypeId() {
        return getString(LoyaltyConstants.KEY_LOYALTY_CARD_TYPE_ID);
    }

    public void setLoyaltyCardTypeId(String loyaltyCardTypeId) {
        put(LoyaltyConstants.KEY_LOYALTY_CARD_TYPE_ID, loyaltyCardTypeId);
    }

    public int getLoyaltyPoints() {
        return getInt(LoyaltyConstants.KEY_LOYALTY_POINTS);
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        put(LoyaltyConstants.KEY_LOYALTY_POINTS, loyaltyPoints);
    }

    public double getCardRatio() {
        return getDouble(LoyaltyConstants.KEY_CARD_RATIO);
    }

    public void setCardRatio(double cardRatio) {
        put(LoyaltyConstants.KEY_CARD_RATIO, cardRatio);
    }

    public long getExpiryTime() {
        return getInt(LoyaltyConstants.KEY_EXPIRY_TIME);
    }

    public void setExpiryTime(long expiryTime) {
        put(LoyaltyConstants.KEY_EXPIRY_TIME, expiryTime);
    }

    public String getCardTerms() {
        return getString(LoyaltyConstants.KEY_CARD_TERMS);
    }

    public void setCardTerms(String cardTerms) {
        put(LoyaltyConstants.KEY_CARD_TERMS, cardTerms);
    }

    public ParseUser getUser() {
        return getParseUser(LoyaltyConstants.KEY_USER);
    }

    public void setUser(ParseUser value) {
        put(LoyaltyConstants.KEY_USER, value);
    }

    public static ParseQuery<LoyaltyCardItem> getQuery() {
        return ParseQuery.getQuery(LoyaltyCardItem.class);
    }
}
