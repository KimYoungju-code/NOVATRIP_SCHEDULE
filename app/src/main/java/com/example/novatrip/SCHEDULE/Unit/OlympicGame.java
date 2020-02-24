package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.Date;

public class OlympicGame implements Parcelable {

    /**
     * OLYMPIC + OLYMPIC_DETAIL table 합친 정보
     * 올림픽 경기에 대한 모든 내용
     **/

    //OLYMPIC table
    private int idx_olympic; /**OLYMPIC table에idx (테이블 join 기준)***/
    private int  categorey_olympic;

    private Date date_olympic;
    private String start_time_olympic; // 원래는 Time type인데.. 서버에서 받아올 때 09:00:00값을 인식할 수 없어서 일단 string type으로 받음.
    private String end_time_olympic; // 원래는 Time type인데.. 서버에서 받아올 때 09:00:00값을 인식할 수 없어서 일단 string type으로 받음.
    private Double lon_olympic_stadium;
    private Double lat_olympic_stadium;

    //OLYMPIC_DETAIL table

    private int idx_olympic_detail;
    private String address_olympic_stadium;
    private String name_olympic_stadium;
    private String name_olympic_game;
    private String info_olympic_game;
    private String info_olympic_game_event;

//TODO:올림픽 게임 정보중에 이미지 URL도 추가해야함.


    public void setIdx_olympic(int idx_olympic) {
        this.idx_olympic = idx_olympic;
    }

    public void setCategorey_olympic(int categorey_olympic) {
        this.categorey_olympic = categorey_olympic;
    }

    public void setDate_olympic(Date date_olympic) {
        this.date_olympic = date_olympic;
    }

    public void setStart_time_olympic(String start_time_olympic) {
        this.start_time_olympic = start_time_olympic;
    }

    public void setEnd_time_olympic(String end_time_olympic) {
        this.end_time_olympic = end_time_olympic;
    }

    public void setLon_olympic_stadium(Double lon_olympic_stadium) {
        this.lon_olympic_stadium = lon_olympic_stadium;
    }

    public void setLat_olympic_stadium(Double lat_olympic_stadium) {
        this.lat_olympic_stadium = lat_olympic_stadium;
    }

    public void setIdx_olympic_detail(int idx_olympic_detail) {
        this.idx_olympic_detail = idx_olympic_detail;
    }

    public void setAddress_olympic_stadium(String address_olympic_stadium) {
        this.address_olympic_stadium = address_olympic_stadium;
    }

    public void setName_olympic_stadium(String name_olympic_stadium) {
        this.name_olympic_stadium = name_olympic_stadium;
    }

    public void setName_olympic_game(String name_olympic_game) {
        this.name_olympic_game = name_olympic_game;
    }

    public void setInfo_olympic_game(String info_olympic_game) {
        this.info_olympic_game = info_olympic_game;
    }

    public void setInfo_olympic_game_event(String info_olympic_game_event) {
        this.info_olympic_game_event = info_olympic_game_event;
    }



    public int getCategorey_olympic() {
        return categorey_olympic;
    }

    public static Creator<OlympicGame> getCREATOR() {
        return CREATOR;
    }



    protected OlympicGame(Parcel in) {
        idx_olympic = in.readInt();
        start_time_olympic = in.readString();
        end_time_olympic = in.readString();
        if (in.readByte() == 0) {
            lon_olympic_stadium = null;
        } else {
            lon_olympic_stadium = in.readDouble();
        }
        if (in.readByte() == 0) {
            lat_olympic_stadium = null;
        } else {
            lat_olympic_stadium = in.readDouble();
        }
        idx_olympic_detail = in.readInt();
        address_olympic_stadium = in.readString();
        name_olympic_stadium = in.readString();
        name_olympic_game = in.readString();
        info_olympic_game = in.readString();
        info_olympic_game_event = in.readString();
    }

    public static final Creator<OlympicGame> CREATOR = new Creator<OlympicGame>() {
        @Override
        public OlympicGame createFromParcel(Parcel in) {
            return new OlympicGame(in);
        }

        @Override
        public OlympicGame[] newArray(int size) {
            return new OlympicGame[size];
        }
    };

    public int getIdx_olympic() {
        return idx_olympic;
    }

    public Date getDate_olympic() {
        return date_olympic;
    }

    public String getStart_time_olympic() {
        return start_time_olympic;
    }

    public String getEnd_time_olympic() {
        return end_time_olympic;
    }

    public Double getLon_olympic_stadium() {
        return lon_olympic_stadium;
    }

    public Double getLat_olympic_stadium() {
        return lat_olympic_stadium;
    }

    public int getIdx_olympic_detail() {
        return idx_olympic_detail;
    }

    public String getAddress_olympic_stadium() {
        return address_olympic_stadium;
    }

    public String getName_olympic_stadium() {
        return name_olympic_stadium;
    }

    public String getName_olympic_game() {
        return name_olympic_game;
    }

    public String getInfo_olympic_game() {
        return info_olympic_game;
    }

    public String getInfo_olympic_game_event() {
        return info_olympic_game_event;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx_olympic);
        dest.writeString(start_time_olympic);
        dest.writeString(end_time_olympic);
        if (lon_olympic_stadium == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon_olympic_stadium);
        }
        if (lat_olympic_stadium == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat_olympic_stadium);
        }
        dest.writeInt(idx_olympic_detail);
        dest.writeString(address_olympic_stadium);
        dest.writeString(name_olympic_stadium);
        dest.writeString(name_olympic_game);
        dest.writeString(info_olympic_game);
        dest.writeString(info_olympic_game_event);
    }
}
