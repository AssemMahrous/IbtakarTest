package tes.sa.net.ibtakar.ibtakartest.ui.search;

import androidx.appcompat.widget.SearchView;
import io.reactivex.Observable;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.PopularResponse;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpPresenter;

public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {

    void getResultsQuery(SearchView searchView);

    void incrementPagination();

    void initializePagination();

    void stopPagination();

    void getDataPaginated(String query, int page);
}
