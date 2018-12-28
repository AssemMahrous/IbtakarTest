package tes.sa.net.ibtakar.ibtakartest.ui.search;

import androidx.appcompat.widget.SearchView;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpPresenter;

public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {

    void getResultsQuery(SearchView searchView);
}
