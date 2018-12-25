package tes.sa.net.ibtakar.ibtakartest.ui.main;

import java.util.List;

import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void initRecyclerView();

    void setData(List<Result> results);

    void getData();

    void endPagination();

    void incrementPagination();
}
