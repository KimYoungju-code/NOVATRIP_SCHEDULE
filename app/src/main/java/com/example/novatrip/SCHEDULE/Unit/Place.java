package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Place  implements Parcelable {

    //PLACE table
    private int idx_place; //OLYMPIC_DETAIL table에idx (테이블 join 기준)
    private int idx_local; //어떤 지역인지  LOCAL 의 idx  , 도쿄의 여행지만 불러올 때 이 idx가 필요핟.
    private Double lat_place;
    private Double lon_place;
    private String start_time_place; // 원래는 Time type인데.. 서버에서 받아올 때 09:00:00값을 인식할 수 없어서 일단 string type으로 받음.
    private String end_time_place; // 원래는 Time type인데.. 서버에서 받아올 때 09:00:00값을 인식할 수 없어서 일단 string type으로 받음.
    private int category_place; // 음식점인지 관광지인지...
    private String img_url_place;// 이미지
    private String place_id;


    //PLACE_DETAIL table
    private int idx_place_detail;
    private String name_place_detail;
    private String address_place_detail;
    private String info_place_detail;


    protected Place(Parcel in) {
        idx_place = in.readInt();
        idx_local = in.readInt();
        if (in.readByte() == 0) {
            lat_place = null;
        } else {
            lat_place = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon_place = null;
        } else {
            lon_place = in.readDouble();
        }
        start_time_place = in.readString();
        end_time_place = in.readString();
        category_place = in.readInt();
        img_url_place = in.readString();
        place_id = in.readString();
        idx_place_detail = in.readInt();
        name_place_detail = in.readString();
        address_place_detail = in.readString();
        info_place_detail = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public int getIdx_place() {
        return idx_place;
    }

    public void setIdx_place(int idx_place) {
        this.idx_place = idx_place;
    }

    public int getIdx_local() {
        return idx_local;
    }

    public void setIdx_local(int idx_local) {
        this.idx_local = idx_local;
    }

    public Double getLat_place() {
        return lat_place;
    }

    public void setLat_place(Double lat_place) {
        this.lat_place = lat_place;
    }

    public Double getLon_place() {
        return lon_place;
    }

    public void setLon_place(Double lon_place) {
        this.lon_place = lon_place;
    }

    public String getStart_time_place() {
        return start_time_place;
    }

    public void setStart_time_place(String start_time_place) {
        this.start_time_place = start_time_place;
    }

    public String getEnd_time_place() {
        return end_time_place;
    }

    public void setEnd_time_place(String end_time_place) {
        this.end_time_place = end_time_place;
    }

    public int getCategory_place() {
        return category_place;
    }

    public void setCategory_place(int category_place) {
        this.category_place = category_place;
    }

    public String getImg_url_place() {
        return img_url_place;
    }

    public void setImg_url_place(String img_url_place) {
        this.img_url_place = img_url_place;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }


    public int getIdx_place_detail() {
        return idx_place_detail;
    }

    public void setIdx_place_detail(int idx_place_detail) {
        this.idx_place_detail = idx_place_detail;
    }

    public String getName_place_detail() {
        return name_place_detail;
    }

    public void setName_place_detail(String name_place_detail) {
        this.name_place_detail = name_place_detail;
    }

    public String getAddress_place_detail() {
        return address_place_detail;
    }

    public void setAddress_place_detail(String address_place_detail) {
        this.address_place_detail = address_place_detail;
    }

    public String getInfo_place_detail() {
        return info_place_detail;
    }

    public void setInfo_place_detail(String info_place_detail) {
        this.info_place_detail = info_place_detail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx_place);
        dest.writeInt(idx_local);
        if (lat_place == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat_place);
        }
        if (lon_place == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon_place);
        }
        dest.writeString(start_time_place);
        dest.writeString(end_time_place);
        dest.writeInt(category_place);
        dest.writeString(img_url_place);
        dest.writeString(place_id);
        dest.writeInt(idx_place_detail);
        dest.writeString(name_place_detail);
        dest.writeString(address_place_detail);
        dest.writeString(info_place_detail);
    }
}
