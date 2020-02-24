package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulPlaceItem;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.Place;

import java.util.ArrayList;

public class AdapterScheduleAddPlace extends RecyclerView.Adapter<AdapterScheduleAddPlace.holer> {


    public String TAG  = "AdapterScheduleAddPlace";
    ArrayList<Place> placeArrayList = new ArrayList<>() ;
    Context context;
    ClickLisenerSchedulPlaceItem clickLisenerSchedulPlaceItem;

    public AdapterScheduleAddPlace(Context context_){
        this.context = context_;
    }

    public void setPlaceArrayList ( ArrayList<Place> placeArrayList) {
        this.placeArrayList = null;
        this.placeArrayList = placeArrayList;
    }

    public class  holer extends RecyclerView.ViewHolder {

        //TODO: 별점이랑 평가점수랑 평가한 개수 나중에 추가해야함. ! 지금은 더미데이터 넣어둠.
        TextView tv_placeName ,tv_businesshours ;
        ImageView iv_profile;
        Button btn_choice_place;

        public holer(@NonNull View itemView) {
            super(itemView);
            tv_placeName = itemView.findViewById(R.id.tv_placeName);
            tv_businesshours = itemView.findViewById(R.id.tv_businesshours);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            btn_choice_place = itemView.findViewById(R.id.btn_choice_place);
        }
    }


    @NonNull
    @Override
    public AdapterScheduleAddPlace.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_place_vertical, viewGroup, false);
        return new AdapterScheduleAddPlace.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterScheduleAddPlace.holer holder, final int position) {
        final Place place = placeArrayList.get(position);
        //TODO: 별점이랑 평가점수랑 평가한 개수 나중에 추가해야함. ! 지금은 더미데이터 넣어둠.

        holder.tv_placeName.setText(place.getName_place_detail());
        Log.d(TAG, "onBindViewHolder: "+position+" 번째 위치 ,  이름 set "+ place.getName_place_detail());

        String businesshours = place.getStart_time_place()+" ~ "+ place.getEnd_time_place();
        Log.d(TAG, "onBindViewHolder: "+position+" 번째 위치 ,  영업시간 set "+ businesshours);
        holder.tv_businesshours.setText(businesshours);


        Glide.with(context)
                .load(place.getImg_url_place())
                .thumbnail(0.01f)
                .into(holder.iv_profile);

        // 만약 선택하기 버튼을 클릭한다면,
        holder.btn_choice_place.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: place " +place);
                Log.d(TAG, "onClick: place " +position);
                Log.d(TAG, "onClick: clickLisenerSchedulPlaceItem " +clickLisenerSchedulPlaceItem);
                clickLisenerSchedulPlaceItem.OnItemClick( place , position );
            }
        });

    }



    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }



    //    //이 메소드는 ChattingRoomList액티비티에서 호출해서 이벤트를 정의할 예정.
    public void ClickListener_clickLisenerSchedulePlaceItem(ClickLisenerSchedulPlaceItem ClickLisenerSchedulPlaceItem) {
        this.clickLisenerSchedulPlaceItem = ClickLisenerSchedulPlaceItem;
    }


}
