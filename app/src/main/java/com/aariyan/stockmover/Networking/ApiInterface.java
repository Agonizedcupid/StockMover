package com.aariyan.stockmover.Networking;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("GrvApp/users.php")
    Observable<ResponseBody> loggedIn(@Field("username") String userName, @Field("pincode") int pinCode);

    @GET("StockMover/items.php")
    Observable<ResponseBody> syncProduct();
}
