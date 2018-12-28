package tes.sa.net.ibtakar.ibtakartest.ui.search;

import android.view.View;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.SearchView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.PopularResponse;
import tes.sa.net.ibtakar.ibtakartest.ui.base.BasePresenter;

public class SearchPresenter<V extends SearchMvpView> extends BasePresenter<V> implements SearchMvpPresenter<V> {

    public SearchPresenter(AppDataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getResultsQuery(SearchView searchView) {
        getObservableQuery(searchView)
                .filter(s -> {
                    if (s.equals("")) {
                        return false;
                    } else {
                        return true;
                    }
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<PopularResponse>>) s -> getDataManager().getApi().searchPeople(s, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());

    }

    @Override
    public void incrementPagination() {
        getMvpView().incrementPagination();
    }

    @Override
    public void initializePagination() {
        getMvpView().initializePagination();
    }

    @Override
    public void stopPagination() {
        getMvpView().stopPagination();
    }



    private Observable<String> getObservableQuery(SearchView searchView) {

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }

    public DisposableObserver<PopularResponse> getObserver() {
        return new DisposableObserver<PopularResponse>() {

            @Override
            public void onNext(@NonNull PopularResponse popularResponse) {
                getMvpView().displayResult(popularResponse.getResults());
                initializePagination();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                getMvpView().displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
            }
        };
    }


    private DisposableObserver<PopularResponse> getObserverPaginate() {
        return new DisposableObserver<PopularResponse>() {

            @Override
            public void onNext(@NonNull PopularResponse popularResponse) {
                getMvpView().concatResult(popularResponse.getResults());
                incrementPagination();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                stopPagination();
                getMvpView().displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
            }
        };
    }


    @Override
    public void getDataPaginated(String query, final int page) {
        getDataManager()
                .getApi()
                .searchPeople(query,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserverPaginate());

    }




}
