package com.example.novatrip.SCHEDULE.Unit;

public class ItemTravelDetail {

    private int idx_travel_plan;
    private String unixtime_travel_plan_detail;
    private int route_order_travel;
    private String  category_travel_plan_detail;
    private OlympicGame olympicGame;


    public static String OlympicGameIDX = "OLYMPIC";


    //TODO:여행 일정중 올림픽 제외한 장소 추가해야함.
    private Place place;

    public int getRoute_order_travel() {
        return route_order_travel;
    }

    public void setRoute_order_travel(int route_order_travel) {
        this.route_order_travel = route_order_travel;
    }





    public int getIdx_travel_plan() {
        return idx_travel_plan;
    }

    public void setIdx_travel_plan(int idx_travel_plan) {
        this.idx_travel_plan = idx_travel_plan;
    }

    public String getUnixtime_travel_plan_detail() {
        return unixtime_travel_plan_detail;
    }

    public void setUnixtime_travel_plan_detail(String unixtime_travel_plan_detail) {
        this.unixtime_travel_plan_detail = unixtime_travel_plan_detail;
    }



    public String getCategory_travel_plan_detail() {
        return category_travel_plan_detail;
    }

    public void setCategory_travel_plan_detail(String category_travel_plan_detail) {
        this.category_travel_plan_detail = category_travel_plan_detail;
    }

    public OlympicGame getOlympicGame() {
        return olympicGame;
    }

    public void setOlympicGame(OlympicGame olympicGame) {
        this.olympicGame = olympicGame;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }



}
