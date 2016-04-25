package com.soldiersofmobile.tumblrviewer;

import android.app.Application;

import com.soldiersofmobile.tumblrviewer.di.DaggerTumblrComponent;
import com.soldiersofmobile.tumblrviewer.di.TumblrComponent;
import com.soldiersofmobile.tumblrviewer.di.TumblrModule;

/**
 * Created by madejs on 25.04.16.
 */
public class App extends Application {

    private static TumblrComponent component;

    public static TumblrComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerTumblrComponent.builder()
                .tumblrModule(new TumblrModule(getApplicationContext()))
                .build();
    }
}
