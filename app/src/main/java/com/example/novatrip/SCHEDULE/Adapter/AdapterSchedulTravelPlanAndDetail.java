package com.example.novatrip.SCHEDULE.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulAddTravelLocation;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelPlan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * 큰 일정 목록
 * 날짜별 일정을 추가하기 위해 내부에 리사이클러뷰가 있다.
 * **/

public class AdapterSchedulTravelPlanAndDetail extends RecyclerView.Adapter<AdapterSchedulTravelPlanAndDetail.holer>   {
    public String TAG = "AdapterSchedulTravelPlanAndDetail";

    public Context context;
    public ArrayList<ItemTravelPlan> itemTravelPlanArrayList;//(일자 일자를 unixTime(string type)으로 바꿔서 담은 arraylist로 시간을 표현한다. )
    public ClickLisenerSchedulAddTravelLocation clickLisenerSchedulAddTravelLocation;



    //자식 리사이클러뷰 어뎁터
    public AdapterSchedulTravelDetail adapterSchedulTravelDetail;


    public AdapterSchedulTravelPlanAndDetail(Context context_ ){
        this.context = context_;
        itemTravelPlanArrayList = new ArrayList<>();
    }

    public void setTravelPlanList (ArrayList<ItemTravelPlan> travelPriodList_) {
        this.itemTravelPlanArrayList = travelPriodList_;
        Log.d(TAG, "setLocalList: 일정 리스트 초기화 " + itemTravelPlanArrayList.size());
    }
    public ArrayList<ItemTravelPlan>   getTravelPlanList ( ) {
        return itemTravelPlanArrayList;
    }
    public class  holer extends RecyclerView.ViewHolder {

        TextView tv_travelDay,tv_travelDate,tv_addTravelDetailPlan;
        RecyclerView recyclerview_TravelChild;


        public holer(@NonNull View itemView) {
            super(itemView);
            tv_travelDay = itemView.findViewById(R.id.tv_travelDay);
            tv_travelDate = itemView.findViewById(R.id.tv_travelDate);
            tv_addTravelDetailPlan= itemView.findViewById(R.id.tv_addTravelDetailPlan);
            recyclerview_TravelChild= itemView.findViewById(R.id.recyclerview_TravelChild);


        }
    }

    @NonNull
    @Override
    public AdapterSchedulTravelPlanAndDetail.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_travelparent, viewGroup, false);
        return new AdapterSchedulTravelPlanAndDetail.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSchedulTravelPlanAndDetail.holer holder, final int position) {

        String travelDate =  UnixTimeToDay( itemTravelPlanArrayList.get(position).getUnixtime());
        Log.d(TAG, "onBindViewHolder:  일정 추가 "+travelDate + "/ D-day : "+ position) ;
        //D day
        holder.tv_travelDay.setText(String.valueOf(position+1));
        // date
        holder.tv_travelDate.setText(travelDate);
        Log.d(TAG, "onBindViewHolder: 각 일자별 세부 일정을 담을 리사이클러뷰 init ");
        initRecyclerViewTravelChild(holder , position);

        // 일정추가 버튼 클릭시
        holder.tv_addTravelDetailPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 클릭한 일정의 position "+position);
                Log.d(TAG, "onClick: 일정 추가하기 버튼 클릭 " + itemTravelPlanArrayList.get(position) );
                clickLisenerSchedulAddTravelLocation.OnItemClick(itemTravelPlanArrayList.get(position).getUnixtime() , position);
            }
        });

    }



 // 상세일정 리사이클러뷰. 자식 리사이클러뷰,
    public void initRecyclerViewTravelChild(AdapterSchedulTravelPlanAndDetail.holer holder ,int  position){
        Log.d(TAG, "initRecyclerViewTravelChild:  자식 리사이클러뷰 init ");
        LinearLayoutManager LayoutManager = new LinearLayoutManager(context);
        holder.recyclerview_TravelChild.setLayoutManager(LayoutManager);
        adapterSchedulTravelDetail = new AdapterSchedulTravelDetail();
        holder.recyclerview_TravelChild.setAdapter(adapterSchedulTravelDetail);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);

        //itemTrevelPlan 객체에 세팅한 recyclerview를 set해준다.
        itemTravelPlanArrayList.get(position).setRecyclerView_child(holder.recyclerview_TravelChild);
        itemTravelPlanArrayList.get(position).setAdapter_child(adapterSchedulTravelDetail);
    }




    @Override
    public int getItemCount() {
        return itemTravelPlanArrayList.size();
    }

    //unixTime을 일자로 바꿈.
    public static String UnixTimeToDay(String unixTime) {
        Date date = new Date(Long.valueOf(unixTime));
        SimpleDateFormat time = new SimpleDateFormat("MM/dd", Locale.KOREA);
        return time.format(date);

    }


//    //    //이 메소드는 ChattingRoomList액티비티에서 호출해서 이벤트를 정의할 예정.
    public void ClickListener_clickLisenerSchedulAddTravelDetailItem(ClickLisenerSchedulAddTravelLocation clickLisenerSchedulAddTravelLocation) {
        this.clickLisenerSchedulAddTravelLocation = clickLisenerSchedulAddTravelLocation;
    }

    /**
     *  자식 리사이클러뷰 어뎁터 클래스
     * **/
    public class AdapterSchedulTravelDetail extends RecyclerView.Adapter<AdapterSchedulTravelDetail.holder> {

        //아이템 viewType
        final int FIRST = 0;
        final int MIDDLE = 1;
        final int FINISH = 2;


        ArrayList<ItemTravelDetail> itemTravelDetailArrayList;

        public AdapterSchedulTravelDetail (){
            itemTravelDetailArrayList = new ArrayList<>();
        }

        public void setItemTravelDetailArrayList(ArrayList<ItemTravelDetail> itemTravelDetailArrayList){
            Log.d(TAG, "setItemTravelDetailArrayList: 자식 리사이클러뷰의 어뎁터의 list를 set한다.");
            this.itemTravelDetailArrayList = itemTravelDetailArrayList;
        }

        public ArrayList<ItemTravelDetail> getItemTravelDetailArrayList() {
            return itemTravelDetailArrayList;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 ) {
                return FIRST;
                //TODO: 날짜 표시하는거 해야함
            } else if (position  == itemTravelDetailArrayList.size()-1  ) {
                Log.d(TAG, "일반 메세지" );
                return FINISH;
            } else  {
                return MIDDLE;
            }
        }


        public class holder extends RecyclerView.ViewHolder{
            int viewType;

            TextView tv_km , tv_travelDetailPlanName , tv_TravelDetailPlanInfo , tv_timeTravelDetailPlan ,  tv_travelRouteNumber;

            public holder(@NonNull View itemView , int type) {
                super(itemView);
                this.viewType = type;

                switch (type){
                    case FIRST :
                        tv_travelDetailPlanName = itemView.findViewById(R.id.tv_travelDetailPlanName);
                        tv_TravelDetailPlanInfo = itemView.findViewById(R.id.tv_categoryTravelDetailPlan);
                        tv_timeTravelDetailPlan = itemView.findViewById(R.id.tv_timeTravelDetailPlan);
                        tv_travelRouteNumber = itemView.findViewById(R.id.tv_travelRouteNumber);

                        break;
                    case MIDDLE :
                        tv_km = itemView.findViewById(R.id.tv_km);
                        tv_travelDetailPlanName = itemView.findViewById(R.id.tv_travelDetailPlanName);
                        tv_TravelDetailPlanInfo = itemView.findViewById(R.id.tv_categoryTravelDetailPlan);
                        tv_timeTravelDetailPlan = itemView.findViewById(R.id.tv_timeTravelDetailPlan);
                        tv_travelRouteNumber = itemView.findViewById(R.id.tv_travelRouteNumber);


                        break;
                    case FINISH :
                        tv_travelDetailPlanName = itemView.findViewById(R.id.tv_travelDetailPlanName);
                        tv_TravelDetailPlanInfo = itemView.findViewById(R.id.tv_categoryTravelDetailPlan);
                        tv_timeTravelDetailPlan = itemView.findViewById(R.id.tv_timeTravelDetailPlan);
                        tv_travelRouteNumber = itemView.findViewById(R.id.tv_travelRouteNumber);
                        tv_km = itemView.findViewById(R.id.tv_km);
                        break;
                    default:
                        Log.d(TAG, "holder: ???  default ");
                        break;
                }
            }




        }

        @NonNull
        @Override
        public AdapterSchedulTravelDetail.holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

            if(viewType == FIRST){
                Log.d(TAG, "onCreateViewHolder: 아이템 1번 출력");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_travelchild_first, viewGroup, false);
                return new AdapterSchedulTravelDetail.holder(view , FIRST );

            }else if(viewType == MIDDLE){
                Log.d(TAG, "onCreateViewHolder: 아이템 중간 출력");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_travelchile_middle, viewGroup, false);
                return new AdapterSchedulTravelDetail.holder(view,MIDDLE);
            }else if(viewType == FINISH) {
                Log.d(TAG, "onCreateViewHolder: 아이템 마지막 출력");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_travelchile_final, viewGroup, false);
                return new AdapterSchedulTravelDetail.holder(view, FINISH);
            }else{
                Log.d(TAG, "onCreateViewHolder: ???? ");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_travelchild_first, viewGroup, false);
                return new AdapterSchedulTravelDetail.holder(view  ,FIRST );
            }

        }

        @Override
        public void onBindViewHolder(@NonNull holder holder, int position) {

             ItemTravelDetail itemTravelDetail =  itemTravelDetailArrayList.get(position);


            switch (holder.viewType){
                case FIRST :
                           holder.tv_travelDetailPlanName.setText(itemTravelDetail.getOlympicGame().getName_olympic_stadium());
                           Log.d(TAG, "onBindViewHolder: 올림픽 게임 title set " + itemTravelDetail.getOlympicGame().getName_olympic_stadium());
                           holder.tv_travelRouteNumber.setText(String.valueOf(position));
                           String start_end_time_FIRST = itemTravelDetail.getOlympicGame().getStart_time_olympic() + " ~ " +  itemTravelDetail.getOlympicGame().getEnd_time_olympic();
                           holder.tv_timeTravelDetailPlan.setText(start_end_time_FIRST);
                           holder.tv_TravelDetailPlanInfo.setText(itemTravelDetail.getOlympicGame().getName_olympic_game());
                    break;
                case MIDDLE :
                            holder.tv_km.setText("미정");
                            holder.tv_travelDetailPlanName.setText(itemTravelDetail.getOlympicGame().getName_olympic_stadium());
                            Log.d(TAG, "onBindViewHolder: 올림픽 게임 title set " + itemTravelDetail.getOlympicGame().getName_olympic_game());
                            holder.tv_travelRouteNumber.setText(String.valueOf(position));
                            String start_end_time_MIDDLE = itemTravelDetail.getOlympicGame().getStart_time_olympic() + " ~ " +  itemTravelDetail.getOlympicGame().getEnd_time_olympic();
                            holder.tv_timeTravelDetailPlan.setText(start_end_time_MIDDLE);
                            holder.tv_TravelDetailPlanInfo.setText(itemTravelDetail.getOlympicGame().getName_olympic_game());

                    break;
                case FINISH :
                            holder.tv_km.setText("미정");
                            holder.tv_travelDetailPlanName.setText(itemTravelDetail.getOlympicGame().getName_olympic_stadium());
                            Log.d(TAG, "onBindViewHolder: 올림픽 게임 title set " + itemTravelDetail.getOlympicGame().getName_olympic_game());
                            holder.tv_travelRouteNumber.setText(String.valueOf(position));
                            String start_end_time_MIDDLE_FINISH = itemTravelDetail.getOlympicGame().getStart_time_olympic() + " ~ " +  itemTravelDetail.getOlympicGame().getEnd_time_olympic();
                            holder.tv_timeTravelDetailPlan.setText(start_end_time_MIDDLE_FINISH);
                            holder.tv_TravelDetailPlanInfo.setText(itemTravelDetail.getOlympicGame().getName_olympic_game());
                    break;
                default:
                    break;
            }
        }


        @Override
        public int getItemCount() {
            return itemTravelDetailArrayList.size();
        }
    }


}


