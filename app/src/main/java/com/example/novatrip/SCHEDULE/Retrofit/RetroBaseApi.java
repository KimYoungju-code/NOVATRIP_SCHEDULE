package com.example.novatrip.SCHEDULE.Retrofit;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.OlympicGame;
import com.example.novatrip.SCHEDULE.Unit.Place;
import com.example.novatrip.SCHEDULE.Unit.TravelPlan;
import com.example.novatrip.SCHEDULE.Unit.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroBaseApi {

    // 요청할 url(기본 앞에 url은 레트로핏 생성할 때 지정하고, 그 뒤에 url을
    String scheduleBaseURL = "http://15.164.214.29/novatrip/schedule/";

    @FormUrlEncoded
    @POST("login.php")
    Call<User> postLogin (@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("searchKeyWord.php")
    Call<List<Local>> postSearchLoaclKeyWord (@Field("keyword") String keyword  );

    @FormUrlEncoded
    @POST("loadTravelDetail.php")
    Call<TravelPlan> postLoadTravelDetail (@Field("idx_travel_plan") int idx_travel_plan);

    //올림픽 일정에 대한 내용 받아옴
    @FormUrlEncoded
    @POST("saveTravelPlanDetail.php")
    Call<Integer> postSaveTravelPlanDetail (@Field("idx_travel_plan") int idx_travel_plan , @Field("unixtime_travel_plan_detail") String unixtime_travel_plan_detail
            ,@Field("travel_route_order") int travel_route_order , @Field("idx_place_or_olympic") int idx_place_or_olympic
            ,@Field("category_travel_plan_detail") int category_travel_plan_detail
            ,@Field("start_time_place_detail") String start_time_place_detail , @Field("end_time_place_detail") String end_time_place_detail
             );


    @FormUrlEncoded
    @POST("saveTravelPlan.php")
    Call<Integer> postSaveTravelPlan (@Field("idx_user") int idx_user,
                                  @Field("name_travel") String name_travel, @Field("time_travel_start") String time_travel_start
            ,@Field("time_travel_end") String time_travel_end  ,@Field("travel_local_list") String travel_local_list
            ,@Field("travel_priod_list") String travel_priod_list);


    @FormUrlEncoded
    @POST("loadPlace.php")
    Call<List<Place>> postLoadPlace (@Field("idx_local") int idx_local ,  @Field("category_place") int category_place  );



    //사용자가 선택한 올림픽 일정을 저장한다.

    @FormUrlEncoded
    @POST("loadOlympicDailyPlan.php")
    Call<List<OlympicGame>> postLoadOlympicDailyPlan (@Field("date") String  date);

    interface RetroCallback<T> {
        void onError(Throwable t);
        void onSuccess(int code, T receivedData);
        void onFailure(int code);
    }







}






