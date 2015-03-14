package com.example.harshevilgeek.loyaltypays.dao;

import com.example.harshevilgeek.loyaltypays.constants.LoyaltyConstants;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by akhil on 3/14/15.
 */
@ParseClassName("LoyaltyFeedback")
public class LoyaltyFeedback extends ParseObject {

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

    public String getFeedbackText() {
        return getString(LoyaltyConstants.KEY_FEEDBACK_TEXT);
    }

    public void setFeedbackText(String feedbackText) {
        put(LoyaltyConstants.KEY_FEEDBACK_TEXT, feedbackText);
    }

    public ParseFile getFeedbackAudio() {
        return getParseFile(LoyaltyConstants.KEY_FEEDBACK_AUDIO);
    }

    public void setFeedbackAudio(ParseFile feedbackAudio) {
        put(LoyaltyConstants.KEY_FEEDBACK_AUDIO, feedbackAudio);
    }

    public List<ParseFile> getFeedbackImages() {
        return getList(LoyaltyConstants.KEY_FEEDBACK_IMAGES);
    }

    public void setFeedbackImages(List<ParseFile> feedbackImages) {
        put(LoyaltyConstants.KEY_FEEDBACK_IMAGES, feedbackImages);
    }

    public String getFeedbackLocation() {
        return getString(LoyaltyConstants.KEY_FEEDBACK_LOCATION);
    }

    public void setFeedbackLocation(List<String> feedbackLocation) {
        put(LoyaltyConstants.KEY_FEEDBACK_LOCATION, feedbackLocation);
    }

}
