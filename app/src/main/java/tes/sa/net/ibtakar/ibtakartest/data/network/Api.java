package tes.sa.net.ibtakar.ibtakartest.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.PopularResponse;

public interface Api {

    @GET("person/popular")
    Call<PopularResponse> fetchPopularActors(@Query("page") int page);

}
