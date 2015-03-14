package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

    public List<String> getDiscountLocations() {
        return getList(LoyaltyConstants.KEY_DISCOUNT_LOCATIONS);
    }

    public void setDiscountLocations(List<String> discountLocations) {
        put(LoyaltyConstants.KEY_DISCOUNT_LOCATIONS, discountLocations);
    }

    public static ParseQuery<LoyaltyDiscountsAndCoupons> getQuery() {
        return ParseQuery.getQuery(LoyaltyDiscountsAndCoupons.class);
    }
}
