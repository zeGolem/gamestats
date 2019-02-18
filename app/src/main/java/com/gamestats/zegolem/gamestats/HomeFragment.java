package com.gamestats.zegolem.gamestats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.logging.ConsoleHandler;

public class HomeFragment extends Fragment {
    View inflatedView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        inflatedView = inflater.inflate(R.layout.home_fragment, container, false);

        Button OW_Apply = inflatedView.findViewById(R.id.overwatch_apply);
        OW_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("\n\n\n\nClicked button");
                EditText OW_battleTag = inflatedView.findViewById(R.id.overwatch_battletag);
                Spinner OW_Region = inflatedView.findViewById(R.id.overwatch_region);
                Spinner OW_Platform = inflatedView.findViewById(R.id.overwatch_platfrom);
                OverwatchData.BattleTag = OW_battleTag.getText().toString();
                switch (OW_Region.getSelectedItem().toString()){
                    case "Europe":
                        OverwatchData.Region = OverwatchData.Regions.Europe;
                        break;
                    case "Amérique":
                        OverwatchData.Region = OverwatchData.Regions.America;
                        break;
                    case "Asie":
                        OverwatchData.Region = OverwatchData.Regions.Asia;
                        break;
                }
                switch (OW_Platform.getSelectedItem().toString()){
                    case "PC":
                        OverwatchData.Platform = OverwatchData.Platforms.PC;
                        break;
                    case "PlayStation 4":
                        OverwatchData.Platform = OverwatchData.Platforms.PlayStation;
                        break;
                    case "XBox":
                        OverwatchData.Platform = OverwatchData.Platforms.XBox;
                        break;
                }
                System.out.println(OverwatchData.Platform.toString());
            }
        });

        return inflatedView;
    }
    public void setOW_ClickListener() {

    }



}
