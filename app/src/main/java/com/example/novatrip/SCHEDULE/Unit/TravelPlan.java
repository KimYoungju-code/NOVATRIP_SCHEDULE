package com.example.novatrip.SCHEDULE.Unit;

public class TravelPlan {

    /**
     *    Call<Integer> postSaveTravelPlan (@Field("idx_user") int idx_user,
     *                                            @Field("travel_name") String travel_name, @Field("travel_local") String travel_local
     *             ,@Field("travel_start") String travel_start  ,@Field("travel_end") String travel_end
     *             ,@Field("travel_priod_list") String travel_priod_list);
     *
     *
     *             {"idx_travel_plan":"11","idx_user":"1","name_travel":"","time_travel_start":"1582632357110","time_travel_end":"1582977957110","travel_local_list":"2,1",
     *             "travel_priod_list":"1582632357110,1582718757110,1582805157110,1582891557110,1582977957110"}
     */

    private int idx_user;
    private String name_travel;
    private String travel_local_list;
    private  String time_travel_start;
    private String time_travel_end;
    private String travel_priod_list;


    public int getIdx_user() {
        return idx_user;
    }

    public void setIdx_user(int idx_user) {
        this.idx_user = idx_user;
    }

    public String getTravel_name() {
        return name_travel;
    }

    public void setTravel_name(String travel_name) {
        this.name_travel = travel_name;
    }

    public String getTravel_local() {
        return travel_local_list;
    }

    public void setTravel_local(String travel_local) {
        this.travel_local_list = travel_local;
    }

    public String getTravel_start() {
        return time_travel_start;
    }

    public void setTravel_start(String travel_start) {
        this.time_travel_start = travel_start;
    }

    public String getTravel_end() {
        return time_travel_end;
    }

    public void setTravel_end(String travel_end) {
        this.time_travel_end = travel_end;
    }

    public String getTravel_priod_list() {
        return travel_priod_list;
    }

    public void setTravel_priod_list(String travel_priod_list) {
        this.travel_priod_list = travel_priod_list;
    }



}
