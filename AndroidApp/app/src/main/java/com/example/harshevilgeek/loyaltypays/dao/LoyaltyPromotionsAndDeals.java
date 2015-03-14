package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by akhil on 3/14/15.
 */
@ParseClassName("LoyaltyPromotionsAndDeals")
public class LoyaltyPromotionsAndDeals extends ParseObject {

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

    public String getPromotionText() {
        return getString(LoyaltyConstants.KEY_PROMOTION_TEXT);
    }

    public void setPromotionText(String promotionText) {
        put(LoyaltyConstants.KEY_PROMOTION_TEXT, promotionText);
    }

    public List<String> getPromotionLocations() {
        return getList(LoyaltyConstants.KEY_PROMOTION_LOCATIONS);
    }

    public void setPromotionLocations(List<String> promotionLocations) {
        put(LoyaltyConstants.KEY_PROMOTION_LOCATIONS, promotionLocations);
    }

    public static ParseQuery<LoyaltyPromotionsAndDeals> getQuery() {
        return ParseQuery.getQuery(LoyaltyPromotionsAndDeals.class);
    }

    public ParseFile getPromotionImage() {
        return getParseFile(LoyaltyConstants.KEY_PROMOTION_IMAGE);
    }

    public void setPromotionImage(ParseFile promotionImage) {
        put(LoyaltyConstants.KEY_PROMOTION_IMAGE, promotionImage);
    }
}
