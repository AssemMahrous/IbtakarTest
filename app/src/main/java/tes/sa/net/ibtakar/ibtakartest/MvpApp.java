package tes.sa.net.ibtakar.ibtakartest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;
import tes.sa.net.ibtakar.ibtakartest.data.network.ApiModule;


/**
 * Created by assem on 2/8/2018.
 */

public class MvpApp extends Application {
    AppDataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        dataManager = new AppDataManager(getApplicationContext(),
                ApiModule.provideApiService());
    }

    public AppDataManager getDataManager() {
        return dataManager;
    }
}
