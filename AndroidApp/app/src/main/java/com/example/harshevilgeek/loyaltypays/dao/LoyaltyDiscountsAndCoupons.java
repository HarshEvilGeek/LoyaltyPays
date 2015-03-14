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
@ParseClassName("LoyaltyDiscountsAndCoupons")
public class LoyaltyDiscountsAndCoupons extends ParseObject {

    public String getCompanyId() {
        return getString(LoyaltyConstants.KEY_COMPANY_ID);
    }

    public void setCompanyId(String companyId) {
        put(LoyaltyConstants.KEY_COMPANY_ID, companyId);
    }

    public String getCustomerId() {
        return getString(LoyaltyConstants.KEY_CUSTOMER_ID);
    }

    public void setCustomerId(String customerId) {
        put(LoyaltyConstants.KEY_CUSTOMER_ID, customerId);
    }

    public String getDiscountText() {
        return getString(LoyaltyConstants.KEY_DISCOUNT_TEXT);
    }

    public void setDiscountText(String discountText) {
        put(LoyaltyConstants.KEY_DISCOUNT_TEXT, discountText);
    }

    public int getDiscountMinPoints() {
        return getInt(LoyaltyConstants.KEY_DISCOUNT_MIN_POINTS);
    }

    public void setDiscountMinPoints(int discountMinPoints) {
        put(LoyaltyConstants.KEY_DISCOUNT_MIN_POINTS, discountMinPoints);
    }

    public ParseFile getDiscountImage() {
        return getParseFile(LoyaltyConstants.KEY_DISCOUNT_IMAGE);
    }

    public void setDiscountImage(ParseFile discountImage) {
        put(LoyaltyConstants.KEY_DISCOUNT_IMAGE, discountImage);
    }

    public static ParseQuery<LoyaltyDiscountsAndCoupons> getQuery() {
        return ParseQuery.getQuery(LoyaltyDiscountsAndCoupons.class);
    }

    public ParseUser getUser() {
        return getParseUser(LoyaltyConstants.KEY_USER);
    }

    public void setUser(ParseUser value) {
        put(LoyaltyConstants.KEY_USER, value);
    }
}
