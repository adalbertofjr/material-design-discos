package com.adalbertofjr.discos.singleton;

import android.app.Application;

import com.squareup.otto.Bus;

/**
 * Created by AdalbertoF on 03/02/2016.
 */
public class DiscoApp extends Application {
    private Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus();
    }

    public Bus getBus(){
        return bus;
    }
}
