package com.example.novatrip.SCHEDULE.Unit;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Unit {

    public static Unit myUnit;
    Context context;

    public  Unit getUnit (Context context){
        if(myUnit == null ){
            myUnit = new Unit();
        }
        myUnit.context = context;
        return myUnit;
    }

    public void claerShardpreference(){


    }


}
