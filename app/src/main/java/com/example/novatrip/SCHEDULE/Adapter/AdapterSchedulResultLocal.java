package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Unit.Local;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdapterSchedulResultLocal extends RecyclerView.Adapter<AdapterSchedulResultLocal.holer>  {
    Context context;
    ArrayList<Local> localArrayList = new ArrayList<>();



    public AdapterSchedulResultLocal (Context context_ ){
        this.context = context_;
    }

    public void setLocalList (ArrayList<Local> localList) {
        this.localArrayList = localList;
    }
    public class  holer extends RecyclerView.ViewHolder {

        TextView tv_resultLocalName;
        ImageView iv_delete_resultLocal;


        public holer(@NonNull View itemView) {
            super(itemView);
            tv_resultLocalName = itemView.findViewById(R.id.tv_resultLocalName);
            iv_delete_resultLocal = itemView.findViewById(R.id.iv_delete_resultLocal);
        }
    }

    @NonNull
    @Override
    public AdapterSchedulResultLocal.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_result_local_item, viewGroup, false);
        return new AdapterSchedulResultLocal.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSchedulResultLocal.holer holder, final int position) {
        String name  = localArrayList.get(position).getName();
        holder.tv_resultLocalName.setText(name);
        holder.iv_delete_resultLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 삭제 ");
                localArrayList.remove(position);
                notifyItemChanged(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return localArrayList.size();
    }
}

