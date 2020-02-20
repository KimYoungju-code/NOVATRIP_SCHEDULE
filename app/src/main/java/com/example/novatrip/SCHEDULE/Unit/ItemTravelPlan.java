package com.example.novatrip.SCHEDULE.Unit;

import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulTravelPlanAndDetail;

public class ItemTravelPlan {


    private String unixtime;//날짜를 밀리초로 나타낸것
    private String date;//날짜
    private RecyclerView recyclerView_child;
    private AdapterSchedulTravelPlanAndDetail.AdapterSchedulTravelDetail adapter_child;


    public AdapterSchedulTravelPlanAndDetail.AdapterSchedulTravelDetail getAdapter_child() {
        return adapter_child;
    }

    public void setAdapter_child(AdapterSchedulTravelPlanAndDetail.AdapterSchedulTravelDetail adapterSchedulTravelDetail) {
        this.adapter_child = adapterSchedulTravelDetail;
    }

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RecyclerView getRecyclerView_child() {
        return recyclerView_child;
    }

    public void setRecyclerView_child(RecyclerView recyclerView_child) {
        this.recyclerView_child = recyclerView_child;
    }


}
