package com.example.novatrip.SCHEDULE.Unit;

import java.util.Date;

public class Travel {

    private int idx;
    private int idx_user;
    private String name_travel;
    private String travel_local_list;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

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

    public String getTravel_priod() {
        return travel_priod_list;
    }

    public void setTravel_priod(String travel_priod) {
        this.travel_priod_list = travel_priod;
    }

    public Date getTravel_startDay() {
        return time_travel_start;
    }

    public void setTravel_startDay(Date travel_startDay) {
        this.time_travel_start = travel_startDay;
    }

    public Date getTravel_endDay() {
        return time_travel_end;
    }

    public void setTravel_endDay(Date travel_endDay) {
        this.time_travel_end = travel_endDay;
    }

    private String travel_priod_list;
    private Date time_travel_start;
    private Date time_travel_end;


}
