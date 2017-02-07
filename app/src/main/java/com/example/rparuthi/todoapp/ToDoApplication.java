package com.example.rparuthi.todoapp;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 Application startup
 */

public class ToDoApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());

    }
}

