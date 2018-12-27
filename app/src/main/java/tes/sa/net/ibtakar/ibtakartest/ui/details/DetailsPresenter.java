package tes.sa.net.ibtakar.ibtakartest.ui.details;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Person;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Profile;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.ProfileImage;
import tes.sa.net.ibtakar.ibtakartest.ui.base.BasePresenter;

public class DetailsPresenter<V extends DetailsMvpView> extends BasePresenter<V> implements DetailsMvpPresenter<V> {

    public DetailsPresenter(AppDataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getData(int personId) {
        getDataManager()
                .getApi()
                .getPersonDetails(personId)
                .enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        try {
                            if (response.isSuccessful())
                                setData(response.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });
    }

    @Override
    public void personImages(int personId) {
        getDataManager()
                .getApi()
                .getPersonImages(personId)
                .enqueue(new Callback<ProfileImage>() {
                    @Override
                    public void onResponse(Call<ProfileImage> call, Response<ProfileImage> response) {
                        try {
                            if (response.isSuccessful())
                                setImages(response.body().getProfiles());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileImage> call, Throwable t) {

                    }
                });
    }

    @Override
    public void setData(Person person) {
        getMvpView().setData(person);
    }

    @Override
    public void setImages(List<Profile> profiles) {
        getMvpView().setImages(profiles);
    }
}
