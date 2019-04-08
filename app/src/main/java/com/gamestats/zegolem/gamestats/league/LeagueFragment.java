package com.gamestats.zegolem.gamestats.league;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gamestats.zegolem.gamestats.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LeagueFragment extends Fragment {

    static View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        inflatedView = inflater.inflate(R.layout.league_fragment, container, false);
        return inflatedView;

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        DataDownloader dd = new DataDownloader();
        dd.execute(LeagueData.AccountName);

    }

    static public void doStuffWithStats(String rawJsonFromApi) throws JSONException {
        JSONObject AccountInfo = new JSONObject(rawJsonFromApi);

        final String Name = AccountInfo.getString("name");
        final int level = AccountInfo.getInt("summonerLevel");

        TextView userName = inflatedView.findViewById(R.id.lol_username_display);
        TextView levelDisplay = inflatedView.findViewById((R.id.lol_level_display));

        userName.setText(Name);
        levelDisplay.setText(String.valueOf(level));

        LeagueData.accountID = AccountInfo.getString("accountId");

        MatchHistoryDownloader mhd = new MatchHistoryDownloader();
        mhd.execute(LeagueData.accountID);
    }

    static public void doStuffWithMatches(String rawJSONFromAPI) throws JSONException {
        JSONObject Matches = new JSONObject(rawJSONFromAPI);

        final int matchesPlayed = Matches.getInt("totalGames");

        TextView playedGames = inflatedView.findViewById(R.id.lol_games_played);

        playedGames.setText(String.valueOf(matchesPlayed));

        LinearLayout scrollView = inflatedView.findViewById(R.id.lol_gameinfo);

        scrollView.addView(createCardView(scrollView.getContext(), 123), 1);



    }

    static public CardView createCardView(Context c, int gameID) {
        CardView cv = new CardView(c);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity = Gravity.CENTER;
        lp.topMargin = 100;
        // lp.width -= 50;
        cv.setLayoutParams(new ViewGroup.LayoutParams(lp));

        cv.setRadius(25);
        // cv.setPadding(1000, 25, 1000, 25);
        cv.setCardBackgroundColor(Color.GRAY);
        cv.setMaxCardElevation(6);


        TextView gameIDdisp = new TextView(c);
        gameIDdisp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gameIDdisp.setTextColor(Color.BLACK);
        gameIDdisp.setPadding(25, 25, 25, 25);
        gameIDdisp.setGravity(Gravity.CENTER);
        gameIDdisp.setText(String.valueOf(gameID));

        cv.addView(gameIDdisp);

        return cv;
    }
}
