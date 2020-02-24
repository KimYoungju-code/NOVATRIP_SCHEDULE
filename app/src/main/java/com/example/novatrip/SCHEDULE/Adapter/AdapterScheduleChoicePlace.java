package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulPlaceItem;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.Place;

import java.util.ArrayList;

public class AdapterScheduleChoicePlace extends RecyclerView.Adapter<AdapterScheduleChoicePlace.holer> {

    String TAG = "AdapterScheduleChoicePlace";
    Context context;

    public ArrayList<Place> getPlaceArrayList() {
        return placeArrayList;
    }

    ArrayList<Place> placeArrayList = new ArrayList<>();


    public AdapterScheduleChoicePlace(Context context_ ){
        this.context = context_;
    }

    public void setLocalList (ArrayList<Place> placeArrayList ) {
        this.placeArrayList = placeArrayList;
    }


    public void addChoicePlaceItemAndNotify(Place place){
        placeArrayList.add(place);
        notifyItemChanged(placeArrayList.size());
    }
    public class  holer extends RecyclerView.ViewHolder {

        LinearLayout layout_item_choice_place;
        ImageView iv_choice_place;
        TextView tv_choice_place_name ;

        public holer(@NonNull View itemView) {
            super(itemView);
            tv_choice_place_name = itemView.findViewById(R.id.tv_choice_place_name);
            iv_choice_place = itemView.findViewById(R.id.iv_choice_place);
            layout_item_choice_place = itemView.findViewById(R.id.layout_item_choice_place);
        }
    }

    @NonNull
    @Override
    public AdapterScheduleChoicePlace.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_choice_place, viewGroup, false);
        return new AdapterScheduleChoicePlace.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterScheduleChoicePlace.holer holder, final  int position) {
        String name  = placeArrayList.get(position).getName_place_detail();
        holder.tv_choice_place_name.setText(name);
        Glide.with(context)
                .load(placeArrayList.get(position).getImg_url_place())
                .thumbnail(0.01f)
                .into(holder.iv_choice_place);

        holder.layout_item_choice_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyItemRemoved(position);
                placeArrayList.remove(position);
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }



}
