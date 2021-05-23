package com.example.cepc;

import android.app.Application;
import android.os.Handler;

public class MyApp extends Application {
    private Handler mHandler = null;

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

}