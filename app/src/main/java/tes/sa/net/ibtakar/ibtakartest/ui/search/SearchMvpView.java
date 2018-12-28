package tes.sa.net.ibtakar.ibtakartest.ui.search;

import java.util.List;

import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpView;

public interface SearchMvpView extends MvpView {

    void setViews();

    void displayResult(List<Result> results);

    void displayError(String s);

    void concatResult(List<Result> results);

    void incrementPagination();

    void stopPagination();

    void initializePagination();

}
