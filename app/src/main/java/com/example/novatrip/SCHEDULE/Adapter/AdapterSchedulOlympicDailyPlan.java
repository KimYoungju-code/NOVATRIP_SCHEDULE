package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Unit.OlympicGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class AdapterSchedulOlympicDailyPlan  extends RecyclerView.Adapter<AdapterSchedulOlympicDailyPlan.holer>  {
    public String TAG = "AdapterSchedulOlympicDailyPlan";
    Context context;
    ArrayList<OlympicGame> olympicGameArrayList = new ArrayList<>();

    //체크한 올림픽 경기 일정을 담을 list
    Hashtable<Integer, OlympicGame> integerOlympicGameHashtable = new Hashtable<>();

    public AdapterSchedulOlympicDailyPlan (Context context_ ){
        this.context = context_;

    }

    public void setLocalList (ArrayList<OlympicGame> olympicGameArrayList) {
        this.olympicGameArrayList = olympicGameArrayList;
    }
    public class  holer extends RecyclerView.ViewHolder {

        TextView tv_olympic_gameTIme,tv_olympic_gameTitle;
        ImageView iv_showOlympicGameLocation;
        CheckBox check_choiceDailyPlanOlympicGame;

        public holer(@NonNull View itemView) {
            super(itemView);
            tv_olympic_gameTIme = itemView.findViewById(R.id.tv_olympic_gameTIme);
            tv_olympic_gameTitle = itemView.findViewById(R.id.tv_olympic_gameTitle);
            iv_showOlympicGameLocation = itemView.findViewById(R.id.iv_showOlympicGameLocation);
            check_choiceDailyPlanOlympicGame = itemView.findViewById(R.id.check_choiceDailyPlanOlympicGame);
        }
    }

    @NonNull
    @Override
    public AdapterSchedulOlympicDailyPlan.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_olympic_daily, viewGroup, false);
        return new AdapterSchedulOlympicDailyPlan.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSchedulOlympicDailyPlan.holer holder, final int position) {
        final OlympicGame olympicGame  = olympicGameArrayList.get(position);
        holder.tv_olympic_gameTitle.setText(olympicGame.getName_olympic_game());
        final String olympic_time = olympicGame.getStart_time_olympic() +" ~ "+ olympicGame.getEnd_time_olympic();
        holder.tv_olympic_gameTIme.setText(olympic_time);

        //체크박스 클릭 리스너.
        holder.check_choiceDailyPlanOlympicGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Boolean check = cb.isChecked();
                Log.d(TAG, "onClick: check "+check);
                Log.d(TAG, "onClick: 체크한 올림픽 "+  olympicGame.getName_olympic_game());
                //체크박스에 체크하면 해당 위치에 있는 올림픽 경기 내용을 저장한다.(해쉬테이블에 저장한 이유? 중복되어 체크된 올림픽 경기 내용에 대한 예외처리 때문.
                if(check){
                    integerOlympicGameHashtable.put(position, olympicGame);
                }
            }
        });

    }

    public ArrayList<OlympicGame> getcheckOlympicGameArrayList(){


        //오름차순으로 정렬한 해쉬테이블을 arraylist로 변경

        ArrayList<OlympicGame> olympicGameArrayList = new ArrayList<OlympicGame>(  integerOlympicGameHashtable.values() );
        Log.d(TAG, "getcheckOlympicGameArrayList: " + olympicGameArrayList.size());


        return olympicGameArrayList;
    }


    //해쉬테이블의 key값을 오름차순으로 정렬  효과없다 ;
//    public Hashtable<Integer, OlympicGame> reloadDescHashTable (Hashtable<Integer, OlympicGame> hashtable){
//        SortedMap m = Collections.synchronizedSortedMap(new TreeMap(hashtable));
//        Set s = m.keySet();
//        synchronized(m) { // Synchronizing on m, not s!
//            Iterator i = s.iterator(); // Must be in synchronized block
//            while (i.hasNext()){
//                Log.d(TAG, "getcheckOlympicGameArrayList: "+i.next() + "<BR>");
//            }
//        }
//        return hashtable;
//    }

    @Override
    public int getItemCount() {
        return olympicGameArrayList.size();
    }




}

