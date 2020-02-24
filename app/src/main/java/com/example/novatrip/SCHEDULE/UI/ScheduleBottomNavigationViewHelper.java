package com.example.novatrip.SCHEDULE.UI;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED;

public class ScheduleBottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view ) {

        Log.d(TAG, "disableShiftMode: "+view);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");

            Log.d(TAG, "disableShiftMode: shiftingMode "+shiftingMode);

            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                //안드로이드 x부터는 Label(하단 버튼 클릭시 글자 보이는 효과)를 제거하기 위해선 item.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED); 이 사용법을 사용해야 한다.
//                item.setShiftingMode(false);
                item.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);

                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", ""+ e);

        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}

