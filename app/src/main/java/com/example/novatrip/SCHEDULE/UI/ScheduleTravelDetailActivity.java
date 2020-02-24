package com.example.novatrip.SCHEDULE.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleAddLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulTravelPlanAndDetail;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulAddTravelLocation;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulTravelDetailitem;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelPlan;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.OlympicGame;
import com.example.novatrip.SCHEDULE.Unit.Place;
import com.example.novatrip.SCHEDULE.Unit.TravelPlan;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail.OlympicGameIDX;
import static com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail.PlaceIDX;

/**
 * 사용자가 선택한 여행기간을 부모 리사이클러뷰의 아이템으로 보여줌.
 *
 * */

public class ScheduleTravelDetailActivity extends AppCompatActivity implements View.OnClickListener {

    //큰 여행일정
    private int idx_travel_plan;
    TravelPlan travelPlan;
    RecyclerView recyclerview_TravelParent;
    AdapterSchedulTravelPlanAndDetail adapterSchedulTravelPlanAndDetail;
    RetroBaseApi retroBaseApi;
    public String TAG = "ScheduleTravelDetailActivity";
    TextView tv_travelName, tv_travelPriod, tv_editTravelPlan, tv_saveTravelPlan;

    //일정추가하기 버튼 클릭시 하단에서 올라올 여행일정 추가 관련 카테고리
    ArrayList<Local> localArrayList_choice;
    AdapterScheduleAddLocal adapterScheduleAddLocal;
    //사용자가 올림픽 경기일정 추가하는 엑티비티에서 선택한 올림픽 경기 리스트 .
    ArrayList<OlympicGame> olympicGameArrayList;
    ArrayList<Place> placeArrayList;
    BottomSheetDialog dialog;
    Context context;


    final static int REQUST_ADD_OLYMPIC_DAILY_PLAN = 200;
    final static int REQUST_ADD_PLACE_DAILY_PLAN = 300;
    final static int CATEGORY_OLYMPIC = 2;
    final static int CATEGORY_PLACE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_travel_detail);

        Log.d(TAG, "onCreate:  ");

        //초기화
        tv_travelName = findViewById(R.id.tv_travelName);
        tv_travelPriod = findViewById(R.id.tv_travelPriod);
        tv_editTravelPlan = findViewById(R.id.tv_editTravelPlan);
        tv_saveTravelPlan = findViewById(R.id.tv_saveTravelPlan);
        context = this;
        olympicGameArrayList= new ArrayList<>();

        //첫 화면에선 저장버튼이 안보임
        tv_saveTravelPlan.setVisibility(View.GONE);
        Log.d(TAG, "onStart: 첫 화면에선 저장버튼이 안보임 ");


        //클릭 리스터
        tv_editTravelPlan.setOnClickListener(this);
        tv_saveTravelPlan.setOnClickListener(this);

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
                localArrayList_choice = new ArrayList<>();

//                // 만약 unixTime이 올림픽 시작일(7/22일)보다 크고 종료일(8/?)일 보다 작으면 올림픽 기간으로 생각해서 일정 추갈할 때 올림픽 관련 일정 추가.
                //TODO:올림픽 경기일정이 아닌데도 올림픽 일정 추가하는 버튼이 뜸.
                Log.d(TAG, "OnItemClick: 사용자가 선택한 여행 시작 날짜 "+unixTime);
                Log.d(TAG, "OnItemClick: 비교하는 날짜   " +Long.valueOf("1595549443037") );

                if(Long.valueOf(unixTime)> Long.valueOf("1595549443037") && Long.valueOf(unixTime)< Long.valueOf("1596931843037")){
                    Log.d(TAG, "OnItemClick: 올림픽 일정 추가! ");
                    Local olympic = new Local();
                    olympic.setIdx_local(2020);
                    olympic.setName("도쿄 올림픽");
                    localArrayList_choice.add(olympic);
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
                    localArrayList_choice.add(local);
                }

                Log.d(TAG, "OnItemClick: 여행지 잘 추가되었나 확인 .");
                for(int i=0 ;  i<localArrayList_choice.size(); i++){
                    Log.d(TAG, "OnItemClick: 지역이름  " +localArrayList_choice.get(i).getName());
                }

                //리사이클러뷰 init
                initBottomRecyclerviewLocalList (  unixTime,   position);
            }
        });




    }


    public void setTravelDetailItemClickLisner(){
        if(adapterSchedulTravelPlanAndDetail.getTravelPlanList().size() == 0 || adapterSchedulTravelPlanAndDetail.getTravelPlanList() ==null){
            Log.d(TAG, "setTravelDetailItemClickLisner: 리스트 비어있음 ");
            return;
        }
        for(int i =0 ; i<adapterSchedulTravelPlanAndDetail.getTravelPlanList().size() ; i++){
            if(  adapterSchedulTravelPlanAndDetail.getTravelPlanList().get(i).getAdapter_child() ==null){
                Log.d(TAG, "setTravelDetailItemClickLisner: 리스트 비어있음 ");
                return;
            }

            Log.d(TAG, "setTravelDetailItemClickLisner:  adapterSchedulTravelPlanAndDetail.getTravelPlanList().get(i).getAdapter_child() "+adapterSchedulTravelPlanAndDetail.getTravelPlanList().get(i).getAdapter_child());
            adapterSchedulTravelPlanAndDetail.getTravelPlanList().get(i).getAdapter_child().ClickListener_clickLisenerSchedulTravelDetailItem(new ClickLisenerSchedulTravelDetailitem() {
                @Override
                public void OnItemClick(final ItemTravelDetail itemTravelDetail,final int position_) {
                    if(itemTravelDetail.getOlympicGame() == null){

                        Log.d(TAG, "OnItemClick: 일반 여행 ");

                        View dialogView  = getLayoutInflater().inflate(R.layout.dialog_bottom_place_info, null);

                        ImageView iv_profile = dialogView.findViewById(R.id.iv_profile);

                        Glide.with(getApplicationContext())
                                .load(itemTravelDetail.getPlace().getImg_url_place())
                                .thumbnail(0.01f)
                                .into(iv_profile);

                        TextView tv_big_title = dialogView.findViewById(R.id.tv_big_title);
                        tv_big_title.setText(itemTravelDetail.getPlace().getName_place_detail());

                        TextView tv_address = dialogView.findViewById(R.id.tv_address);
                        tv_address.setText(itemTravelDetail.getPlace().getAddress_place_detail());

                        TextView tv_phonNumber = dialogView.findViewById(R.id.tv_phonNumber);
                        tv_phonNumber.setText("준비중...");

                        TextView tv_businesshours = dialogView.findViewById(R.id.tv_businesshours);
                        String businesshours = itemTravelDetail.getPlace().getStart_time_place() +" ~ " +itemTravelDetail.getPlace().getEnd_time_place();
                        tv_businesshours.setText(businesshours);


                        TextView tv_showPlaceDetail = dialogView.findViewById(R.id.tv_showPlaceDetail);
                        tv_showPlaceDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "onClick: 상세보기 클릭. ");
                                Intent intent = new Intent(getApplicationContext(), SchedulePlaceDetailActivity.class);
                                intent.putExtra("itemTravelDetail", itemTravelDetail );
                                startActivity(intent);
                            }
                        });

                        dialog = new BottomSheetDialog(context);
                        dialog.setContentView(dialogView);
                        dialog.show();

                    }else if(itemTravelDetail.getOlympicGame() !=null){
                        Log.d(TAG, "OnItemClick: 올림픽 여행 ");

                        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bottom_olympic_info, null);

                        ImageView iv_profile = dialogView.findViewById(R.id.iv_profile);
//TODO:올림픽 경기에 관한 이미지 컬럼 만들고 정보 넣어서 보여줘야함.
                        Glide.with(getApplicationContext())
                                .load("https://upload.wikimedia.org/wikipedia/ko/thumb/b/b6/2020%EB%85%84_%ED%95%98%EA%B3%84_%EC%98%AC%EB%A6%BC%ED%94%BD_%EB%A1%9C%EA%B3%A0.svg/1200px-2020%EB%85%84_%ED%95%98%EA%B3%84_%EC%98%AC%EB%A6%BC%ED%94%BD_%EB%A1%9C%EA%B3%A0.svg.png")
                                .thumbnail(0.01f)
                                .into(iv_profile);

                        TextView tv_big_title = dialogView.findViewById(R.id.tv_big_title);
                        tv_big_title.setText(itemTravelDetail.getOlympicGame().getName_olympic_game());

                        TextView tv_olympic_stadiumName = dialogView.findViewById(R.id.tv_olympic_stadiumName);
                        tv_olympic_stadiumName.setText(itemTravelDetail.getOlympicGame().getName_olympic_stadium());

                        TextView tv_address = dialogView.findViewById(R.id.tv_address);
                        tv_address.setText(itemTravelDetail.getOlympicGame().getAddress_olympic_stadium());

                        TextView tv_businesshours = dialogView.findViewById(R.id.tv_businesshours);
                        String businesshours = itemTravelDetail.getOlympicGame().getStart_time_olympic() +" ~ " +itemTravelDetail.getOlympicGame().getEnd_time_olympic();
                        tv_businesshours.setText(businesshours);


                        TextView tv_showPlaceDetail = dialogView.findViewById(R.id.tv_showPlaceDetail);
                        tv_showPlaceDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "onClick: 상세보기 클릭. ");
                                Intent intent = new Intent(getApplicationContext(), SchedulePlaceDetailActivity.class);
                                intent.putExtra("itemTravelDetail", itemTravelDetail );
                                startActivity(intent);
                            }
                        });
                        dialog = new BottomSheetDialog(context);
                        dialog.setContentView(dialogView);
                        dialog.show();
                    }

                }
            });
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_saveTravelPlan:
                Log.d(TAG, "onClick: 여행일정 서버에 저장! ");
                ArrayList<ItemTravelPlan> itemTravelPlans =  adapterSchedulTravelPlanAndDetail.getTravelPlanList();
                Log.d(TAG, "onClick: ");

                for(int i=0; i<itemTravelPlans.size(); i++){
                     ArrayList<ItemTravelDetail> itemTravelDetails =  itemTravelPlans.get(i).getAdapter_child().getItemTravelDetailArrayList();
                     for(int j=0; j<itemTravelDetails.size(); j++){
                      ItemTravelDetail itemTravelDetail = itemTravelDetails.get(i);
                      if(itemTravelDetail.getOlympicGame() == null){

                          /***
                           Call<Integer> postSaveTravelPlanDetail (@Field("idx_travel_plan") int idx_travel_plan , @Field("unixtime_travel_plan_detail") long unixtime_travel_plan_detail
                           ,@Field("travel_route_order") int travel_route_order , @Field("idx_place_or_olympic") int idx_place_or_olympic
                           ,@Field("category_travel_plan_detail") int category_travel_plan_detail
                           ,@Field("start_time_place_detail") String start_time_place_detail , @Field("end_time_place_detail") String end_time_place_detail
                           );
                           *
                           * ***/
                          Log.d(TAG, "onClick: 일반일정 아이템");
                          Place place = itemTravelDetail.getPlace();
                          Log.d(TAG, "onClick: place "+place);

                          int idx_travel_plan = itemTravelDetail.getIdx_travel_plan();
                          String unixtime_travel_plan_detail =  itemTravelDetails.get(i).getUnixtime_travel_plan_detail() ;
                          int travel_route_order = itemTravelDetail.getRoute_order_travel();
                          int idx_place_or_olympic = place.getIdx_local();
                          int category_travel_plan_detail = CATEGORY_PLACE;
                          String start_time_place_detail = place.getStart_time_place();
                          String end_time_place_detail = place.getEnd_time_place();

                          Log.d(TAG, "onClick: 서버로 보내는 정보 idx_travel_plan " +idx_travel_plan);
                          Log.d(TAG, "onClick: 서버로 보내는 정보 unixtime_travel_plan_detail " +unixtime_travel_plan_detail);
                          Log.d(TAG, "onClick: 서버로 보내는 정보 travel_route_order " +travel_route_order);
                          Log.d(TAG, "onClick: 서버로 보내는 정보 idx_place_or_olympic " +idx_place_or_olympic);
                          Log.d(TAG, "onClick: 서버로 보내는 정보 category_travel_plan_detail " +category_travel_plan_detail);
                          Log.d(TAG, "onClick: 서버로 보내는 정보 start_time_place_detail " +start_time_place_detail);
                          Log.d(TAG, "onClick: 서버로 보내는 정보 end_time_place_detail " +end_time_place_detail);

                          retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
                          retroBaseApi.postSaveTravelPlanDetail(idx_travel_plan , unixtime_travel_plan_detail
                                  , travel_route_order , idx_place_or_olympic
                                  , category_travel_plan_detail , start_time_place_detail , end_time_place_detail)
                                  .enqueue(new Callback<Integer>() {
                                      @Override
                                      public void onResponse(Call<Integer> call, Response<Integer> response) {
                                          Log.d(TAG, "onResponse: 서버 결과  " + response.body());
                                      }

                                      @Override
                                      public void onFailure(Call<Integer> call, Throwable t) {
                                          Log.d(TAG, "onFailure: "+ t.getMessage());
                                      }
                                  });
                      }
                     }
                }

                break;
        }

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
            adapterScheduleAddLocal = new AdapterScheduleAddLocal(getApplicationContext() );
            recyclerView_addTravelLocalList.setAdapter(adapterScheduleAddLocal);
            adapterScheduleAddLocal.setLocalList(localArrayList_choice);
            adapterScheduleAddLocal.notifyDataSetChanged();
            
            //아이템 클릭시
            adapterScheduleAddLocal.ClickListener_clickLisenerSchedulLocalItem(new ClickLisenerSchedulLocalItem() {

                @Override
                public void OnItemClick(Local local , int p) {



                    // idx가 2020인 올림픽을 제외하고 나머지는 다 지역.
                    // idx가 2020인 경우 올림픽 시간표를 보여주는 엑티비티로 이동, 그 외는 지역 관광지를 보여주는 엑티비티로 이동.
                    //도쿄 올림픽 시간표를 보여주는 엑티비티로 이동해서 보고싶은 경기의 정보를 담아 back한다.
                    if(local.getIdx_local() == 2020){
                        Log.d(TAG, "OnItemClick: 도쿄 올림픽 선택");
                        Intent intent = new Intent(getApplicationContext(),ScheduleOlympicDailyScedule.class);
                        intent.putExtra("unixTime", unixTime);
                        intent.putExtra("position", position_travel_plan);
                        intent.putExtra("idx_travel_plan", idx_travel_plan);

                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 position " + position_travel_plan);
                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 unixTime " + unixTime);
                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 idx_travel_plan " + idx_travel_plan);
                        dialog.dismiss();

                        startActivityForResult(intent, REQUST_ADD_OLYMPIC_DAILY_PLAN);

                    }else{

                        Log.d(TAG, "OnItemClick: 지역 선택");
                        Intent intent = new Intent(getApplicationContext(),ScheduleAddPlace.class);
                        intent.putExtra("unixTime", unixTime);
                        intent.putExtra("position_travel_plan", position_travel_plan);
                        intent.putExtra("idx_travel_plan", idx_travel_plan);
                        intent.putExtra("idx_local", local.getIdx_local());


                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 position " + position_travel_plan);
                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 unixTime " + unixTime);
                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 idx_travel_plan " + idx_travel_plan);
                        Log.d(TAG, "OnItemClick: 일정 페이지로 보내는 정보 idx_local " + local.getIdx_local());
                        dialog.dismiss();
                        startActivityForResult(intent, REQUST_ADD_PLACE_DAILY_PLAN);
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

            tv_saveTravelPlan.setVisibility(View.VISIBLE);
            tv_editTravelPlan.setVisibility(View.GONE);
            Log.d(TAG, "OnItemClick: 하단에서 여행지 선택해서 추가하려고 하면, 편집버튼 사라지고 저장버튼 나타나기. ");

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

                    Log.d(TAG, "onActivityResult: 올림픽 ?? unixTime "+unixTime);

                    Log.d(TAG, "onActivityResult: "+itemTravelDetail.getOlympicGame().getName_olympic_game());

                    itemTravelDetails.add(itemTravelDetail);
                }

                Log.d(TAG, "onActivityResult: 올림픽 관련 정보 모두 arraylist에 담음");
                itemTravelPlan.getAdapter_child().setItemTravelDetailArrayList(itemTravelDetails);
                itemTravelPlan.getAdapter_child().notifyDataSetChanged();
                Log.d(TAG, "onActivityResult: 추가한 리사이클러뷰로 data set change");

        }else   if (requestCode ==REQUST_ADD_PLACE_DAILY_PLAN  && resultCode == RESULT_OK && null != data) {


            tv_editTravelPlan.setVisibility(View.GONE);
            tv_saveTravelPlan.setVisibility(View.VISIBLE);


            Log.d(TAG, "OnItemClick: 하단에서 여행지 선택해서 추가하려고 하면, 편집버튼 사라지고 저장버튼 나타나기. ");

            /**
             * intent.putParcelableArrayListExtra("choicePlaceList",   adapterScheduleChoicePlace.getPlaceArrayList());
             *                 intent.putExtra("unixTime", unixTime);
             *                 intent.putExtra("position",position);
             *                 */

            //사용자가 일정에 추가할 올림픽 경기 리스트
            placeArrayList   =  data.getParcelableArrayListExtra("choicePlaceList");
            Log.d(TAG, "onActivityResult: 올림픽 경기 몇개 추가? "+ placeArrayList.size());
            //일정추가 버튼이 속한 리사이클러뷰의 position
            int postionRecyclerViewParent = data.getIntExtra("position",10000);
            String unixTime_ =  data.getStringExtra("unixTime" );
            Log.d(TAG, "onActivityResult: unixTime ?? "+unixTime_);

            //TravelPlanList에서 해당 position에 있는 itemTravelPlan을 가져오고 해당 아이템의 어뎁터를 꺼낸다.
            ItemTravelPlan itemTravelPlan =  adapterSchedulTravelPlanAndDetail.getTravelPlanList().get(postionRecyclerViewParent);
            //TravelPlanDetail 을 보여주는 리사이클러뷰의 list를 가져온 후
            ArrayList<ItemTravelDetail> itemTravelDetails =  itemTravelPlan.getAdapter_child().getItemTravelDetailArrayList();

                Log.d(TAG, "onActivityResult: 첫 데이터 추가");
                //추가하기로한 올림픽 일정을 이 itemTrevelDetails에 추가해준다.
//                for(int i=placeArrayList.size()-1; i>=0; i--){
//                    ItemTravelDetail itemTravelDetail = new ItemTravelDetail();
//
//                    //첫 일정 추가기 때문에 일정 추가하는 i 값이 route(TravelPlanDetail이 나열되는 순서) 값이 된다.
//                    Log.d(TAG, "onActivityResult: 자식 리사이클러뷰에 들어갈 item 만듦");
//                    itemTravelDetail.setRoute_order_travel(i);
//                    itemTravelDetail.setPlace(placeArrayList.get(i));
//                    itemTravelDetail.setCategory_travel_plan_detail(PlaceIDX);
//                    itemTravelDetail.setUnixtime_travel_plan_detail(unixTime);
//                    itemTravelDetail.setIdx_travel_plan(idx_travel_plan);
//
//                    Log.d(TAG, "onActivityResult: "+itemTravelDetail.getPlace().getName_place_detail());
//
//                    itemTravelDetails.add(itemTravelDetail);
//                }
            for(int i=0; i<placeArrayList.size(); i++){
                ItemTravelDetail itemTravelDetail = new ItemTravelDetail();

                //첫 일정 추가기 때문에 일정 추가하는 i 값이 route(TravelPlanDetail이 나열되는 순서) 값이 된다.
                Log.d(TAG, "onActivityResult: 자식 리사이클러뷰에 들어갈 item 만듦");
                itemTravelDetail.setRoute_order_travel(i);
                itemTravelDetail.setPlace(placeArrayList.get(i));
                itemTravelDetail.setCategory_travel_plan_detail(PlaceIDX);
                itemTravelDetail.setUnixtime_travel_plan_detail(unixTime_);
                itemTravelDetail.setIdx_travel_plan(idx_travel_plan);

                Log.d(TAG, "onActivityResult: "+itemTravelDetail.getPlace().getName_place_detail());

                itemTravelDetails.add(itemTravelDetail);
            }

                Log.d(TAG, "onActivityResult: 올림픽 관련 정보 모두 arraylist에 담음");
                itemTravelPlan.getAdapter_child().setItemTravelDetailArrayList(itemTravelDetails);
                itemTravelPlan.getAdapter_child().notifyDataSetChanged();
                Log.d(TAG, "onActivityResult: 추가한 리사이클러뷰로 data set change");

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
        setTravelDetailItemClickLisner();
    }



    public void loadTravelPlan (){
        idx_travel_plan = getIntent().getIntExtra("idx_travel_plan", 10000);
        //TODO:TEST중...
//        idx_travel_plan =   8;
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
