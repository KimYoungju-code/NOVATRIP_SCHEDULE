package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemTravelDetail  implements Parcelable {

    //공통
    private int idx_travel_plan;
    private String unixtime_travel_plan_detail;
    private int route_order_travel;

    //카테고리
    private String  category_travel_plan_detail;
    public static String OlympicGameIDX = "OLYMPIC";
    public static String PlaceIDX = "PLACE";

    //TODO:여행 일정중 올림픽 제외한 장소 추가해야함.
    private OlympicGame olympicGame;
    private Place place;

    public ItemTravelDetail(Parcel in) {
        idx_travel_plan = in.readInt();
        unixtime_travel_plan_detail = in.readString();
        route_order_travel = in.readInt();
        category_travel_plan_detail = in.readString();
        olympicGame = in.readParcelable(OlympicGame.class.getClassLoader());
        place = in.readParcelable(Place.class.getClassLoader());
    }

    public ItemTravelDetail( ) {

    }


    public static final Creator<ItemTravelDetail> CREATOR = new Creator<ItemTravelDetail>() {
        @Override
        public ItemTravelDetail createFromParcel(Parcel in) {
            return new ItemTravelDetail(in);
        }

        @Override
        public ItemTravelDetail[] newArray(int size) {
            return new ItemTravelDetail[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx_travel_plan);
        dest.writeString(unixtime_travel_plan_detail);
        dest.writeInt(route_order_travel);
        dest.writeString(category_travel_plan_detail);
        dest.writeParcelable(olympicGame, flags);
        dest.writeParcelable(place, flags);
    }
}
