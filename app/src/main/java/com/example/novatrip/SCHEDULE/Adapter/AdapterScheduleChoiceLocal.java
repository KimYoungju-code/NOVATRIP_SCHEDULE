package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Unit.Local;

import java.util.ArrayList;

public class AdapterScheduleChoiceLocal extends RecyclerView.Adapter<AdapterScheduleChoiceLocal.holer> {

    Context context;
    ArrayList<Local>  localArrayList = new ArrayList<>();

    public AdapterScheduleChoiceLocal(Context context_ ){
        this.context = context_;
    }

    public void setLocalList (ArrayList<Local> localList) {
        this.localArrayList = localList;
    }
    public class  holer extends RecyclerView.ViewHolder {

        TextView tv_choice_list ;

        public holer(@NonNull View itemView) {
            super(itemView);
            tv_choice_list = itemView.findViewById(R.id.tv_choice_list);
        }
    }

    @NonNull
    @Override
    public AdapterScheduleChoiceLocal.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_choice_local, viewGroup, false);
        return new AdapterScheduleChoiceLocal.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterScheduleChoiceLocal.holer holder, int position) {
        String name  = localArrayList.get(position).getName();
        holder.tv_choice_list.setText(name);
    }

    @Override
    public int getItemCount() {
        return localArrayList.size();
    }
}
