package com.offersbull.network;



import com.offersbull.model.ResponsReal;
import com.offersbull.model.ResponsTravel;
import com.offersbull.model.Response;
import com.offersbull.model.ResponseAutomobile;
import com.offersbull.model.ResponseAutomobileSingle;
import com.offersbull.model.ResponseHotel;
import com.offersbull.model.ResponseHotelSingle;
import com.offersbull.model.ResponseOffer;
import com.offersbull.model.ResponseMore;
import com.offersbull.model.ResponseOther;
import com.offersbull.model.ResponseOtherSingle;
import com.offersbull.model.ResponsePopuler;
import com.offersbull.model.ResponseRealSingle;
import com.offersbull.model.ResponseTravalSingle;
import com.offersbull.model.ResponseTution;
import com.offersbull.model.ResponseTutionSingle;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

/*    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
  */

    @GET("home/home")
    Observable<Response> getHome();

    @GET("realEstate/feed")
    Observable<ResponsReal> getRealEstate(@Query("page") int page);

    @GET("tutions/feed")
    Observable<ResponseTution> getTutions(@Query("page") int page);

    @GET("hotel/feed")
    Observable<ResponseHotel> getHotel(@Query("page") int page);

    @GET("travelling/feed")
    Observable<ResponsTravel> getTravelling(@Query("page") int page);

    @GET("automobile/feed")
    Observable<ResponseAutomobile> getAutomobile(@Query("page") int page);

    @GET("other/feed")
    Observable<ResponseOther> getOther(@Query("page") int page);

    @FormUrlEncoded
    @POST("realEstate/data")
    Observable<ResponseRealSingle> getRealEstateById(@Field("id") int id);

    @FormUrlEncoded
    @POST("tutions/data")
    Observable<ResponseTutionSingle> getTutionById(@Field("id") int id);

    @FormUrlEncoded
    @POST("hotel/data")
    Observable<ResponseHotelSingle> getHotelById(@Field("id") int id);

    @FormUrlEncoded
    @POST("travelling/data")
    Observable<ResponseTravalSingle> getTravellingById(@Field("id") int id);

    @FormUrlEncoded
    @POST("automobile/data")
    Observable<ResponseAutomobileSingle> getAutomobileById(@Field("id") int id);

    @FormUrlEncoded
    @POST("other/data")
    Observable<ResponseOtherSingle> getOtherById(@Field("id") int id);

    @GET("home/latest")
    Observable<ResponseOffer> getLatest(@Query("page") int page);

    @GET("home/populer")
    Observable<ResponseOffer> getPopuler(@Query("page") int page);

    @GET("home/more")
    Observable<ResponseOffer> getMore(@Query("page") int page);

}
