package com.gamestats.zegolem.gamestats.overwatch;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class OverwatchData {


    public static String BattleTag = "";
    public static Regions Region;
    public static Platforms Platform;

    public static boolean APIRequestSucessfull = false;
    public static String APIResponse;


    public enum Regions {
        Europe ("eu"), America ("us"), Asia ("asia");
        private String name = "";
        Regions(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    public enum  Platforms {
        PC ("pc"), XBox ("xbox"), PlayStation ("playstation");
        private String name = "";
        Platforms(String name){
            this.name = name;
        }
        @Override
        public String toString() {
            return this.name;
        }
    }


}
