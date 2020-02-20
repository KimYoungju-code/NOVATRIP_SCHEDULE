package com.example.novatrip.SCHEDULE.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulAddLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulTravelPlanAndDetail;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulAddTravelLocation;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelPlan;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.OlympicGame;
import com.example.novatrip.SCHEDULE.Unit.TravelPlan;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail.OlympicGameIDX;

/**
 * 사용자가 선택한 여행기간을 부모 리사이클러뷰의 아이템으로 보여줌.
 *
 * */

public class ScheduleTravelDetailActivity extends AppCompatActivity {

    //큰 여행일정
    private int idx_travel_plan;
    TravelPlan travelPlan;
    RecyclerView recyclerview_TravelParent;
    AdapterSchedulTravelPlanAndDetail adapterSchedulTravelPlanAndDetail;
    RetroBaseApi retroBaseApi;
    public String TAG = "ScheduleTravelDetailActivity";
    TextView tv_travelName, tv_travelPriod;

    //일정추가하기 버튼 클릭시 하단에서 올라올 여행일정 추가 관련 카테고리
    ArrayList<Local> localArrayList;
    AdapterSchedulAddLocal adapterSchedulAddLocal;
    //사용자가 올림픽 경기일정 추가하는 엑티비티에서 선택한 올림픽 경기 리스트 .
    ArrayList<OlympicGame> olympicGameArrayList;
    BottomSheetDialog dialog;

    final static int REQUST_ADD_OLYMPIC_DAILY_PLAN = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_travel_detail);

        Log.d(TAG, "onCreate:  ");

        //초기화
        tv_travelName = findViewById(R.id.tv_travelName);
        tv_travelPriod = findViewById(R.id.tv_travelPriod);
        olympicGameArrayList= new ArrayList<>();
        //여행 일정에 부모 리사이클러뷰를 init
        recyclerview_TravelParent = findViewById(R.id.recyclerview_TravelParent);
        initRecyclerviewTravelParent();
        //여행일정 정보를 db에서 받아온 후 받아온 정보를 리사이클러뷰의 어뎁터에 set
        loadTravelPlan ();

        //큰 일정 아이템의 일정추가하기 버튼 클릭시 일어날 이벤트
        //선택할 수 있는 지역을 보여줄 리사이클러뷰 init
        //arraylist에 지역 추가.

        adapterSchedulTravelPlanAndDetail.ClickListener_clickLisenerSchedulAddTravelDetailItem(new ClickLisenerSchedulAddTravelLocation() {
            @Override
            public void OnItemClick(String unixTime, int position) {

                localArrayList = new ArrayList<>();

//                // 만약 unixTime이 올림픽 시작일(7/22일)보다 크고 종료일(8/?)일 보다 작으면 올림픽 기간으로 생각해서 일정 추갈할 때 올림픽 관련 일정 추가.
                //TODO:올림픽 경기일정이 아닌데도 올림픽 일정 추가하는 버튼이 뜸.
                Log.d(TAG, "OnItemClick: 사용자가 선택한 여행 시작 날짜 "+unixTime);
                Log.d(TAG, "OnItemClick: 비교하는 날짜   " +Long.valueOf("1595549443037") );

                if(Long.valueOf(unixTime)> Long.valueOf("1595549443037") && Long.valueOf(unixTime)< Long.valueOf("1596931843037")){
                    Log.d(TAG, "OnItemClick: 올림픽 일정 추가! ");
                    Local olympic = new Local();
                    olympic.setIdx_local(2020);
                    olympic.setName("도쿄 올림픽");
                    localArrayList.add(olympic);
                }else{
                    Log.d(TAG, "OnItemClick: 올림픽 일정이 아님!");
                }

                //일반 여행 지역 추가.
                String[] toColum = travelPlan.getTravel_local().split(",");
                for (int i = 0 ; i< toColum.length; i++){
                    Local local = new Local();
                    //idx.
                    local.setIdx_local(Integer.valueOf(toColum[i]));
                    //name
                    SharedPreferences sharedPreferences = getSharedPreferences("LOCAL", MODE_PRIVATE);
                    local.setName( sharedPreferences.getString(toColum[i],null));
                    Log.d(TAG, "OnItemClick: 추가한 지역 "+ sharedPreferences.getString(toColum[i],null));
                    //여행지 추가.
                    localArrayList.add(local);
                }

                Log.d(TAG, "OnItemClick: 여행지 잘 추가되었나 확인 .");
                for(int i=0 ;  i<localArrayList.size(); i++){
                    Log.d(TAG, "OnItemClick: 지역이름  " +localArrayList.get(i).getName());
                }

                //리사이클러뷰 init
                initBottomRecyclerviewLocalList (  unixTime,   position);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    // 여행일정 등록 클릭시 하단에 추가할 일정의 지역을 선택하게 할 때 보여지는 리사이클러뷰.
        public void initBottomRecyclerviewLocalList (final String unixTime, final int position_travel_plan){

            View dialogView = getLayoutInflater().inflate(R.layout.item_schedule_bottom_local_dialog, null);

            //리사이클러뷰 init 
            RecyclerView recyclerView_addTravelLocalList = (RecyclerView) dialogView.findViewById(R.id.recyclerview_bottom_local_daily);
            LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
            LayoutManager.setReverseLayout(true);
            LayoutManager.setStackFromEnd(true);
            recyclerView_addTravelLocalList.setLayoutManager(LayoutManager);
            adapterSchedulAddLocal = new AdapterSchedulAddLocal(getApplicationContext() );
            recyclerView_addTravelLocalList.setAdapter(adapterSchedulAddLocal);
            adapterSchedulAddLocal.setLocalList(localArrayList);
            adapterSchedulAddLocal.notifyDataSetChanged();
            
            //아이템 클릭시
            adapterSchedulAddLocal.ClickListener_clickLisenerSchedulLocalItem(new ClickLisenerSchedulLocalItem() {

                @Override
                public void OnItemClick(Local local , int position) {

                    // idx가 2020인 올림픽을 제외하고 나머지는 다 지역.
                    // idx가 2020인 경우 올림픽 시간표를 보여주는 엑티비티로 이동, 그 외는 지역 관광지를 보여주는 엑티비티로 이동.
                    if(local.getIdx_local() == 2020){
                        Log.d(TAG, "OnItemClick: 도쿄 올림픽 선택");
                        dialog.dismiss();
                        //도쿄 올림픽 시간표를 보여주는 엑티비티로 이동해서 보고싶은 경기의 정보를 담아 back한다.
                        Intent intent = new Intent(getApplicationContext(),ScheduleOlympicDailyScedule.class);
                        intent.putExtra("unixTime", unixTime);
                        intent.putExtra("position", position_travel_plan);
                        intent.putExtra("idx_travel_plan", idx_travel_plan);

                        Log.d(TAG, "OnItemClick: 올림픽 경기일정 페이지로 보내는 정보 position " + position);
                        Log.d(TAG, "OnItemClick: 올림픽 경기일정 페이지로 보내는 정보 unixTime " + unixTime);
                        Log.d(TAG, "OnItemClick: 올림픽 경기일정 페이지로 보내는 정보 idx_travel_plan " + idx_travel_plan);

                        startActivityForResult(intent, REQUST_ADD_OLYMPIC_DAILY_PLAN);

                    }else{
                        Log.d(TAG, "OnItemClick: 지역 선택");
                        //TODO: 지역 선택

                    }
                }
            });

            dialog = new BottomSheetDialog(this);
            dialog.setContentView(dialogView);
            dialog.show();
        }

/**
 * Intent intent_result = new Intent();
 *                 intent_result.putParcelableArrayListExtra("olympicGameArrayList",   olympicGameArrayList);
 *                 intent_result.putExtra("unixTime", unixtime_travel_plan_detail);
 *                 intent_result.putExtra("position",position);
 *                 */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUST_ADD_OLYMPIC_DAILY_PLAN  && resultCode == RESULT_OK && null != data) {

            //사용자가 일정에 추가할 올림픽 경기 리스트
            olympicGameArrayList   =  data.getParcelableArrayListExtra("olympicGameArrayList");
            Log.d(TAG, "onActivityResult: 올림픽 경기 몇개 추가? "+ olympicGameArrayList.size());
            //일정추가 버튼이 속한 리사이클러뷰의 position
            int postionRecyclerViewParent = data.getIntExtra("position",10000);
             String unixTime =  data.getStringExtra("unixTime" );


            //TravelPlanList에서 해당 position에 있는 itemTravelPlan을 가져오고 해당 아이템의 어뎁터를 꺼낸다.
             ItemTravelPlan itemTravelPlan =  adapterSchedulTravelPlanAndDetail.getTravelPlanList().get(postionRecyclerViewParent);
             //TravelPlanDetail 을 보여주는 리사이클러뷰의 list를 가져온 후
             ArrayList<ItemTravelDetail> itemTravelDetails =  itemTravelPlan.getAdapter_child().getItemTravelDetailArrayList();

             //새로 추가할 데이터를 list에 add한다.
             if(itemTravelDetails.size() == 0) {
                 Log.d(TAG, "onActivityResult: 첫 데이터 추가");
                 //추가하기로한 올림픽 일정을 이 itemTrevelDetails에 추가해준다.
                 for(int i=olympicGameArrayList.size()-1; i>=0; i--){
                     ItemTravelDetail itemTravelDetail = new ItemTravelDetail();

                     //첫 일정 추가기 때문에 일정 추가하는 i 값이 route(TravelPlanDetail이 나열되는 순서) 값이 된다.
                     Log.d(TAG, "onActivityResult: 자식 리사이클러뷰에 들어갈 item 만듦");
                     itemTravelDetail.setRoute_order_travel(i);
                     itemTravelDetail.setOlympicGame(olympicGameArrayList.get(i));
                     itemTravelDetail.setCategory_travel_plan_detail(OlympicGameIDX);
                     itemTravelDetail.setUnixtime_travel_plan_detail(unixTime);
                     itemTravelDetail.setIdx_travel_plan(idx_travel_plan);

                     Log.d(TAG, "onActivityResult: "+itemTravelDetail.getOlympicGame().getName_olympic_game());

                     itemTravelDetails.add(itemTravelDetail);                     
                 }

                 Log.d(TAG, "onActivityResult: 올림픽 관련 정보 모두 arraylist에 담음");
                 itemTravelPlan.getAdapter_child().setItemTravelDetailArrayList(itemTravelDetails);
                 itemTravelPlan.getAdapter_child().notifyDataSetChanged();
                 Log.d(TAG, "onActivityResult: 추가한 리사이클러뷰로 data set change");
                 
             }




        }
    }
    //여행 일정 부모 리사이클러뷰
    public void initRecyclerviewTravelParent() {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerview_TravelParent.setLayoutManager(LayoutManager);
        adapterSchedulTravelPlanAndDetail = new AdapterSchedulTravelPlanAndDetail(getApplicationContext() );
        recyclerview_TravelParent.setAdapter(adapterSchedulTravelPlanAndDetail);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");




    }



    public void loadTravelPlan (){
        idx_travel_plan = getIntent().getIntExtra("idx_travel_plan", 10000);
        //TODO:TEST중...
//        idx_travel_plan =   83;
        Log.d(TAG, "onResume: 사용자가 선택한 여행의 idx "+idx_travel_plan);

        // 해당 idx 의 일정 정보를 서버에 요청
        if (idx_travel_plan != 10000) {
            Log.d(TAG, "onResume: 통신! ");
            retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
            retroBaseApi.postLoadTravelDetail(idx_travel_plan)
                    .enqueue(new Callback<TravelPlan>() {
                        @Override
                        public void onResponse(Call<TravelPlan> call, Response<TravelPlan> response) {

                            //서버에서 큰 여행일정에 대한 정보를 받음.
                            travelPlan = response.body();

                            //각 뷰에 그 전 페이지에서 입력한 여행이름 , 기간 정보를 textview에 set한다.
                            Log.d(TAG, "onResponse: " + travelPlan.getTravel_name());

                            //이름
                            tv_travelName.setText(travelPlan.getTravel_name());

                            //기간
                            String startDay = UnixTimeToDate(travelPlan.getTravel_start());
                            String endDay = UnixTimeToDate(travelPlan.getTravel_end());
                            String period = startDay + " ~ " + endDay;
                            tv_travelPriod.setText(period);

                            //여행일자 하나 하나 출력해서 부모 리사이클러뷰 아이템에 넣기.
                            String priod_list_str =  travelPlan.getTravel_priod_list();
                            Log.d(TAG, "onResponse:  "+priod_list_str);

                            //문자열로 나열된 여행 일자(unixTime)을 ArrayList에 날짜 형태로 담음.

                            //이  arraylist를 바로 큰 일정을 보여주는 리사이클러뷰에  set할 예정.
                            ArrayList<ItemTravelPlan> itemTravelPlanArrayList = new ArrayList<>();
                            String[] toColum = priod_list_str.split(",");

                            Log.d(TAG, "onResponse: 추가한 여행지역 idx "+ priod_list_str);

                            for (int i = 0 ; i< toColum.length; i++){
                                ItemTravelPlan itemTravelPlan = new ItemTravelPlan();
                                itemTravelPlan.setUnixtime(toColum[i]);
                                itemTravelPlanArrayList.add(i, itemTravelPlan);

                            }
                            Log.d(TAG, "onResponse: 추가한 여행지역 string을 arraylist 에 담음 " + itemTravelPlanArrayList.size());
                            //큰 일정을 보여주는 리사이클러뷰에 priod_list를 set한다.
                            adapterSchedulTravelPlanAndDetail.setTravelPlanList(itemTravelPlanArrayList);
                            adapterSchedulTravelPlanAndDetail.notifyDataSetChanged();
                            Log.d(TAG, "onResponse: 자식 어뎁터의 리사이클러뷰 초기화 ");
                        }

                        @Override
                        public void onFailure(Call<TravelPlan> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });

        } else {
            Toast.makeText(this, "문제 발생!", Toast.LENGTH_SHORT).show();
        }
    }

    public static String UnixTimeToDate(String unixTime) {
        Date date = new Date(Long.valueOf(unixTime));
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        return time.format(date);
    }


}
