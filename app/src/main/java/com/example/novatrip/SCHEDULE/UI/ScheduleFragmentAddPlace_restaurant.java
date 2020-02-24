package com.example.novatrip.SCHEDULE.UI;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulTravelPlanAndDetail;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleAddPlace;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleChoicePlace;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulPlaceItem;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.Place;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.novatrip.SCHEDULE.UI.ScheduleAddPlace.category_place_all;
import static com.example.novatrip.SCHEDULE.UI.ScheduleAddPlace.category_place_restaurant;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link ScheduleFragmentAddPlace_restaurant.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ScheduleFragmentAddPlace_restaurant#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ScheduleFragmentAddPlace_restaurant extends Fragment {

    public String TAG = "ScheduleFragmentAddPlace_restaurant";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // private OnFragmentInteractionListener mListener;
    public  String unixTime ;
    public int position ;
    public int idx_travel_plan ;
    public int idx_local;

    // 리사이클러뷰에
    private RecyclerView recyclerView_restaurant;
    private AdapterScheduleAddPlace adapterScheduleAddRestaurant;

    private RetroBaseApi retroBaseApi;
    AdapterScheduleChoicePlace adapterScheduleChoicePlace;
    public ScheduleFragmentAddPlace_restaurant(String unixTime, int position  , int idx_travel_plan , int idx_local  , AdapterScheduleChoicePlace adapterScheduleChoicePlace) {
        this.idx_local = idx_local ;
        this.unixTime = unixTime ;
        this.position = position ;
        this.idx_travel_plan = idx_travel_plan ;
        this.adapterScheduleChoicePlace = adapterScheduleChoicePlace;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ScheduleFragmentPlace_recommendation.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ScheduleFragmentAddPlace_restaurant newInstance(String param1, String param2) {
//        ScheduleFragmentAddPlace_restaurant fragment = new ScheduleFragmentAddPlace_restaurant();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_fragment_add_place_restaurant, container, false);

        //리사이클러뷰 초기화
        recyclerView_restaurant = view.findViewById(R.id.recycler_view_restaurant);

        initRecyclerviewAddPlace();

        //서버에서 받아온 데이터 리사이클러뷰에 set
        locadPlace(idx_local ,category_place_restaurant );
        //장소 아이템 안에 있는 선택 버튼 클릭시.
        adapterScheduleAddRestaurant.ClickListener_clickLisenerSchedulePlaceItem(new ClickLisenerSchedulPlaceItem() {
            @Override
            public void OnItemClick(Place place, int position) {
                Log.d(TAG, "OnItemClick: //생성자로 받아온 선택한 아이템을 보여주는 가로 리사이클러뷰의 어뎁터의 arraylist에 아이템을 추가한 후 notiy를 해준다.");
                //생성자로 받아온 선택한 아이템을 보여주는 가로 리사이클러뷰의 어뎁터의 arraylist에 아이템을 추가한 후 notiy를 해준다.
                adapterScheduleChoicePlace.addChoicePlaceItemAndNotify(place);
            }
        });

        return view;

    }

    //세로로 보여주는 장소 정보 리사이클러뷰 init
    public void initRecyclerviewAddPlace() {
        Log.d(TAG, "initRecyclerviewAddPlace: ???");
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView_restaurant.setLayoutManager(LayoutManager);
        adapterScheduleAddRestaurant = new AdapterScheduleAddPlace( getContext() );
        recyclerView_restaurant.setAdapter(adapterScheduleAddRestaurant);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);
    }

    public void locadPlace(int idx_local , int category_place  ){
        Log.d(TAG, "locadPlace: 모든 장소 정보 가져옴. ");
        retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
        retroBaseApi.postLoadPlace(idx_local, category_place)
                .enqueue(new Callback<List<Place>>() {
                    @Override
                    public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                        Log.d(TAG, "onResponse " +response.body().size());
                        ArrayList<Place> placeArrayList= new ArrayList<>();
                        for(int i = 0; i < response.body().size() ; i++){
                            placeArrayList.add(i,response.body().get(i));
                            Log.d(TAG, "onResponse: localArrayList.get(i).getName_place_detail() "+placeArrayList.get(i).getName_place_detail());
                        }

                        //장소를 보여줄 리사이클러뷰에 장소 정보가 담긴 arraylist를 set한다.
                        adapterScheduleAddRestaurant.setPlaceArrayList(placeArrayList);
                        adapterScheduleAddRestaurant.notifyDataSetChanged();
                        Log.d(TAG, "onResponse: adapterScheduleAddPlace.notifyDataSetChanged(); ");
                    }

                    @Override
                    public void onFailure(Call<List<Place>> call, Throwable t) {

                    }
                });
    }







    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

}
