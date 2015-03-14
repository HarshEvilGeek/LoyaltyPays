package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseUser;

/**
 * Created by akhil on 3/14/15.
 */
public class LoyaltyUser extends ParseUser {

    public void setUserType(String userType) {
        put(LoyaltyConstants.KEY_USER_TYPE, userType);
    }

    public String getUserType() {
        return getString(LoyaltyConstants.KEY_USER_TYPE);
    }
}
