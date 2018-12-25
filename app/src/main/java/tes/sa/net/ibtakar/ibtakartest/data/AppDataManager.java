package tes.sa.net.ibtakar.ibtakartest.data;

import android.content.Context;

import tes.sa.net.ibtakar.ibtakartest.data.network.Api;

public class AppDataManager {

    private static AppDataManager ourInstance;
    private final Api api;

    public static AppDataManager getInstance(Context context, Api api) {
        if (ourInstance == null) {
            ourInstance = new AppDataManager(context, api);
        }
        return ourInstance;
    }

    private AppDataManager(Context context, Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }
}
