package tes.sa.net.ibtakar.ibtakartest.ui.main;

import java.util.List;

import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void getData(int position);

    void setData(List<Result> results);

    void incrementPagination();

    void stopPagination();
}
