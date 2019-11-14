package com.example.drivingstyleassistant.common;

import android.app.Activity;
import android.app.Application;

public class DrivingStyleAssistant extends Application {
    public void onCreate() {
        super.onCreate();
    }
    private Activity currentActivity = null;

    public Activity getCurrentActivity(){
        return currentActivity;
    }
    public void setCurrentActivity(Activity _currentActivity){
        this.currentActivity = _currentActivity;
    }

    public void clearReferences(){
        Activity currActivity = getCurrentActivity();
        if (this.equals(currActivity))
            setCurrentActivity(null);
    }
}
