package com.example.novatrip.SCHEDULE.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link ScheduleFragmentPlace_intro.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ScheduleFragmentPlace_intro#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ScheduleFragmentPlace_intro extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ItemTravelDetail itemTravelDetail;

    String TAG = "ScheduleFragmentPlace_intro";


    public ScheduleFragmentPlace_intro(ItemTravelDetail itemTravelDetail) {
        // Required empty public constructor
        this.itemTravelDetail = itemTravelDetail;

    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ScheduleFragmentPlace_intro.
//     */
    // TODO: Rename and change types and number of parameters
//    public static ScheduleFragmentPlace_intro newInstance(String param1, String param2) {
//        ScheduleFragmentPlace_intro fragment = new ScheduleFragmentPlace_intro();
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
        View view = inflater.inflate(R.layout.fragment_schedule_fragment_place_intro, container, false);

        if(itemTravelDetail.getOlympicGame() == null){
            Log.d(TAG, "onCreateView: 일반일정 ");
            TextView tv_big_title = view.findViewById(R.id.tv_big_title);
            tv_big_title.setText(itemTravelDetail.getPlace().getName_place_detail());

            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(itemTravelDetail.getPlace().getAddress_place_detail());

            TextView tv_businesshours = view.findViewById(R.id.tv_businesshours);
            String businesshours = itemTravelDetail.getPlace().getStart_time_place() +" ~ " +itemTravelDetail.getPlace().getEnd_time_place();
            tv_businesshours.setText(businesshours);

            ImageView iv_profile = view.findViewById(R.id.iv_profile);
            Glide.with(getContext())
                    .load(itemTravelDetail.getPlace().getImg_url_place())
                    .thumbnail(0.08f)
                    .into(iv_profile);
            TextView tv_detail_info = view.findViewById(R.id.tv_detail_info);
            tv_detail_info.setText(itemTravelDetail.getPlace().getInfo_place_detail());

        }else{

            Log.d(TAG, "onCreateView: 일반일정 ");
            TextView tv_big_title = view.findViewById(R.id.tv_big_title);
            tv_big_title.setText(itemTravelDetail.getOlympicGame().getInfo_olympic_game());

            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(itemTravelDetail.getOlympicGame().getAddress_olympic_stadium());

            TextView tv_businesshours = view.findViewById(R.id.tv_businesshours);
            String businesshours = itemTravelDetail.getOlympicGame().getStart_time_olympic() +" ~ " +itemTravelDetail.getOlympicGame().getEnd_time_olympic();
            tv_businesshours.setText(businesshours);

            ImageView iv_profile = view.findViewById(R.id.iv_profile);
            Glide.with(getContext())
                    .load("https://upload.wikimedia.org/wikipedia/ko/thumb/b/b6/2020%EB%85%84_%ED%95%98%EA%B3%84_%EC%98%AC%EB%A6%BC%ED%94%BD_%EB%A1%9C%EA%B3%A0.svg/1200px-2020%EB%85%84_%ED%95%98%EA%B3%84_%EC%98%AC%EB%A6%BC%ED%94%BD_%EB%A1%9C%EA%B3%A0.svg.png")
                    .thumbnail(0.08f)
                    .into(iv_profile);
            TextView tv_detail_info = view.findViewById(R.id.tv_detail_info);
            tv_detail_info.setText(itemTravelDetail.getOlympicGame().getInfo_olympic_game_event());



        }


        return view;
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
}
