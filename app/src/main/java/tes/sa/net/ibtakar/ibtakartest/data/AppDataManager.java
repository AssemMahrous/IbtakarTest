package tes.sa.net.ibtakar.ibtakartest.data;

import android.content.Context;

import tes.sa.net.ibtakar.ibtakartest.data.network.Api;

public class AppDataManager {

    Api api;


    public AppDataManager(Context context, Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }
}
