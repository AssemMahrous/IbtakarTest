package tes.sa.net.ibtakar.ibtakartest.ui.main;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.PopularResponse;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.base.BasePresenter;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    public MainPresenter(AppDataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getData(int position) {
        getDataManager()
                .getApi()
                .fetchPopularActors(position)
                .enqueue(new Callback<PopularResponse>() {
                    @Override
                    public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                PopularResponse response1 = response.body();
                                if (position == response1.getTotalPages())
                                    stopPagination();
                                else incrementPagination();
                                setData(response1.getResults());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<PopularResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void setData(List<Result> results) {
        getMvpView().setData(results);
    }

    @Override
    public void incrementPagination() {
        getMvpView().incrementPagination();
    }

    @Override
    public void stopPagination() {
        getMvpView().endPagination();
    }
}
