package tes.sa.net.ibtakar.ibtakartest.data.network;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Person;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.PopularResponse;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.ProfileImage;

public interface Api {

    @GET("person/popular")
    Call<PopularResponse> fetchPopularActors(@Query("page") int page);

    @GET("person/{person_id}")
    Call<Person> getPersonDetails(@Path("person_id") int personId);

    @GET("person/{person_id}/images")
    Call<ProfileImage> getPersonImages(@Path("person_id") int personId);

    @GET("search/person")
    Observable<PopularResponse> searchPeople(@Query("query") String query,@Query("page") int page);



}
