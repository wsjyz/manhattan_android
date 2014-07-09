package com.ivan.android.manhattanenglish.app.core;

import android.app.Application;
import android.content.Context;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-24
 * Time: AM10:32
 */
public class MainApplication extends Application {
    public static Context GLOBAL_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        GLOBAL_CONTEXT = this;
    }
}
