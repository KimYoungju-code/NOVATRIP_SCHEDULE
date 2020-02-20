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

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ItemListener_chatList;
import com.example.novatrip.SCHEDULE.Unit.Local;

import java.util.ArrayList;
import java.util.List;


/**
 * 지역 목록을 보여주는 세로 리사이클러뷰
 * */
public class AdapterSchedulAddLocal  extends RecyclerView.Adapter<AdapterSchedulAddLocal.holer> {
    public String TAG  = "AdapterSchedulAddLocal";
    ArrayList<Local> localList = new ArrayList<>() ;
    Context context;
    ClickLisenerSchedulLocalItem clickLisenerSchedulLocalItem;

    public AdapterSchedulAddLocal (Context context_){
        this.context = context_;
    }

    public void setLocalList (ArrayList<Local> localList) {
        this.localList = localList;
    }

    public class  holer extends RecyclerView.ViewHolder {

        TextView   tv_add_local ,tv_localName ;

        public holer(@NonNull View itemView) {
            super(itemView);
            tv_add_local = itemView.findViewById(R.id.tv_add_local);
            tv_localName = itemView.findViewById(R.id.tv_localName);
        }
    }


    @NonNull
    @Override
    public holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_addtravel_local, viewGroup, false);
        return new holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holer holder, final int position) {
        final Local local= localList.get(position);

        holder.tv_localName.setText(local.getName());

        // 만약 선택하기 버튼을 클릭한다면,
        holder.tv_add_local.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLisenerSchedulLocalItem.OnItemClick( local , position );
            }
        });

    }



    @Override
    public int getItemCount() {
        return localList.size();
    }



//    //이 메소드는 ChattingRoomList액티비티에서 호출해서 이벤트를 정의할 예정.
    public void ClickListener_clickLisenerSchedulLocalItem(ClickLisenerSchedulLocalItem clickLisenerSchedulLocalItem) {
        this.clickLisenerSchedulLocalItem = clickLisenerSchedulLocalItem;
    }


}
