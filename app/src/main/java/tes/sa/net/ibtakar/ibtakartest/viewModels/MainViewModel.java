package tes.sa.net.ibtakar.ibtakartest.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import tes.sa.net.ibtakar.ibtakartest.MvpApp;
import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;

public class MainViewModel extends AndroidViewModel {
    AppDataManager dataManager;

    public MainViewModel(@NonNull Application application) {
        super(application);

        dataManager = ((MvpApp) getApplication()).getDataManager();
    }
}
