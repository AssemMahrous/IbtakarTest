package tes.sa.net.ibtakar.ibtakartest.ui.base;


import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;

/**
 * Created by assem on 1/28/2018.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    AppDataManager mDataManager;


    public BasePresenter(AppDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach(V mvpView) {
        mvpView = null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public AppDataManager getDataManager() {
        return mDataManager;
    }


}
