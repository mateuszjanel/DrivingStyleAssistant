package com.example.drivingstyleassistant.common;

import android.content.Context;

public class ContextService {
    public Context appContext;

    private static ContextService INSTANCE;

    public static ContextService getContextService(){
        if(INSTANCE == null) {
            INSTANCE = new ContextService();
        }
        return INSTANCE;
    }
}
