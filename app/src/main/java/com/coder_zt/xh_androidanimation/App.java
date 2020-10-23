package com.coder_zt.xh_androidanimation;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context sContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext(){
        return sContext;
    }
}
