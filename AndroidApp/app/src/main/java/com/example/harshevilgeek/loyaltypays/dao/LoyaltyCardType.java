package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by akhil on 3/14/15.
 */
@ParseClassName("LoyaltyCardType")
public class LoyaltyCardType extends ParseObject {

    public String getCompanyId() {
        return getString(LoyaltyConstants.KEY_COMPANY_ID);
    }

    public void setCompanyId(String companyId) {
        put(LoyaltyConstants.KEY_COMPANY_ID, companyId);
    }

    public String getCardName() {
        return getString(LoyaltyConstants.KEY_CARD_NAME);
    }

    public void setCardName(String cardName) {
        put(LoyaltyConstants.KEY_CARD_NAME, cardName);
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

    public List<String> getCardLocations() {
        return getList(LoyaltyConstants.KEY_COMPANY_LOCATIONS);
    }

    public void setCardLocations(List<String> companyLocations) {
        put(LoyaltyConstants.KEY_COMPANY_LOCATIONS, companyLocations);
    }

    public String getCardTerms() {
        return getString(LoyaltyConstants.KEY_CARD_TERMS);
    }

    public void setCardTerms(String cardTerms) {
        put(LoyaltyConstants.KEY_CARD_TERMS, cardTerms);
    }

    public double getCardRatio() {
        return getDouble(LoyaltyConstants.KEY_CARD_RATIO);
    }

    public void setCardRatio(double cardRatio) {
        put(LoyaltyConstants.KEY_CARD_RATIO, cardRatio);
    }

    public static ParseQuery<LoyaltyCardType> getQuery() {
        return ParseQuery.getQuery(LoyaltyCardType.class);
    }

    public ParseUser getUser() {
        return getParseUser(LoyaltyConstants.KEY_USER);
    }

    public void setUser(ParseUser value) {
        put(LoyaltyConstants.KEY_USER, value);
    }
}
