package com.marotech.vending.util;

import com.marotech.vending.model.*;

import java.math.BigDecimal;


public class ActivityBuilder {
    private final Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity();
    }


    public ActivityBuilder withTitle(String title) {
        activity.setTitle(title);
        return this;
    }

    public ActivityBuilder withActivityType(ActivityType type) {
        activity.setActivityType(type);
        return this;
    }

    public ActivityBuilder withRecording(Recording recording) {
        activity.setRecording(recording);
        return this;
    }

    public Activity build() {
        return activity;
    }
}
