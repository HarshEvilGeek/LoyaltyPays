package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by akhil on 3/14/15.
 */
@ParseClassName("LoyaltyCardPurchases")
public class LoyaltyCardPurchases extends ParseObject {

    public String getPurchaseId() {
        return getString(LoyaltyConstants.KEY_PURCHASE_ID);
    }

    public void setPurchaseId(String purchaseId) {
        put(LoyaltyConstants.KEY_PURCHASE_ID, purchaseId);
    }

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

    public String getPurchaseName() {
        return getString(LoyaltyConstants.KEY_PURCHASE_NAME);
    }

    public void setPurchaseName(String purchaseName) {
        put(LoyaltyConstants.KEY_PURCHASE_NAME, purchaseName);
    }

    public String getPurchaseLocation() {
        return getString(LoyaltyConstants.KEY_PURCHASE_LOCATION);
    }

    public void setPurchaseLocation(String purchaseLocation) {
        put(LoyaltyConstants.KEY_PURCHASE_LOCATION, purchaseLocation);
    }

    public String getPurchaseCurrency() {
        return getString(LoyaltyConstants.KEY_PURCHASE_CURRENCY);
    }

    public void setPurchaseCurrency(String purchaseCurrency) {
        put(LoyaltyConstants.KEY_PURCHASE_CURRENCY, purchaseCurrency);
    }
}
