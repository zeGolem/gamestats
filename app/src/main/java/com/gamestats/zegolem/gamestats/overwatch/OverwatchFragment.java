package com.gamestats.zegolem.gamestats.overwatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gamestats.zegolem.gamestats.R;

import org.json.JSONException;
import org.json.JSONObject;

public class OverwatchFragment extends Fragment {

    static View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){



        inflatedView = inflater.inflate(R.layout.overwatch_fragment, container, false);
        return inflatedView;

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(OverwatchData.Platform == null || OverwatchData.Region == null){
            Toast.makeText(inflatedView.getContext(), R.string.error_private, Toast.LENGTH_LONG).show();
        }else {
            final String BattleTag = OverwatchData.BattleTag;
            final String Platform = OverwatchData.Platform.toString();
            final String Region = OverwatchData.Region.toString();

            DataDownloader dd = new DataDownloader();
            dd.execute(Platform, Region, BattleTag);
        }

    }

    static public void doStuffWithStats(String rawJsonFromApi) throws JSONException {
        JSONObject stats = new JSONObject(rawJsonFromApi);
        if (stats.getJSONObject("quickplay").getJSONObject("global").length() == 0) {
            Toast.makeText(inflatedView.getContext(), R.string.error_data, Toast.LENGTH_LONG).show();
            return;
        }
        final String Name = stats.getJSONObject("profile").getString("nick");
        final int Level = stats.getJSONObject("profile").getInt("level");
        final int QuickPlayWins = stats.getJSONObject("quickplay").getJSONObject("global").getInt("games_won");
        final double QuickPlayElimsAvg = stats.getJSONObject("quickplay").getJSONObject("global").getDouble("eliminations_avg_per_10_min");
        final int QuickPlayElims = stats.getJSONObject("quickplay").getJSONObject("global").getInt("eliminations");
        final String QuickPlayMain = stats.getJSONObject("quickplay").getJSONObject("global").getString("masteringHeroe");
        final int QuickPlayDeaths = stats.getJSONObject("quickplay").getJSONObject("global").getInt("deaths");
        final int QuickPlayTime = stats.getJSONObject("quickplay").getJSONObject("global").getInt("time_played");

        TextView battleTag = inflatedView.findViewById(R.id.lol_username_display);
        TextView QPWins = inflatedView.findViewById(R.id.overwatch_quick_games_won_display);
        TextView level = inflatedView.findViewById(R.id.overwatch_level_display);
        TextView QPElimsAVG = inflatedView.findViewById(R.id.overwatch_quick_elims_avg);
        TextView QPElims = inflatedView.findViewById(R.id.overwatch_quick_elims);
        TextView QPMain = inflatedView.findViewById((R.id.overwatch_quick_main));
        TextView QPDeaths = inflatedView.findViewById(R.id.overwatch_quick_deaths);
        TextView QPTime = inflatedView.findViewById(R.id.overwatch_quick_time);

        battleTag.setText(Name);
        QPWins.setText(String.valueOf(QuickPlayWins));
        level.setText(String.valueOf(Level));
        QPElimsAVG.setText(String.valueOf(QuickPlayElimsAvg));
        QPElims.setText(String.valueOf(QuickPlayElims));
        QPMain.setText(QuickPlayMain);
        QPDeaths.setText(String.valueOf(QuickPlayDeaths));
        QPTime.setText(String.valueOf(QuickPlayTime));

    }

}
