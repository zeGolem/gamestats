package com.gamestats.zegolem.gamestats;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.ConsoleHandler;

public class OverwatchFragment extends Fragment {

    View inflatedView;
    Thread getStatsThread;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){



        inflatedView = inflater.inflate(R.layout.overwatch_fragment, container, false);
        return inflatedView;

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String BattleTag = OverwatchData.BattleTag;
        final String Platform = OverwatchData.Platform.toString();
        final String Region = OverwatchData.Region.toString();
        final Context context = this.getContext();
        getStatsThread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    URL url = new URL("https://ow-api.com/v1/stats/" + Platform + "/" + Region + "/" + BattleTag.replace("#", "-") +"/profile");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setConnectTimeout(30000);
                    con.setReadTimeout(30000);
                    System.out.println("Request created, sending...");
                    int status = con.getResponseCode();
                    System.out.println("Response status : " + status);
                    if(status == 200) {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        System.out.println("Respose : " + content.toString());
                        OverwatchData.APIRequestSucessfull = true;
                        OverwatchData.APIResponse = content.toString();
                        con.disconnect();
                        // doStuffWithStats(content.toString());
                        return;
                    } else {
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                        dlgAlert.setMessage("Nous n'avons pas pû récupérer les données à partir de l'API 'ow-api.com'");
                        dlgAlert.setTitle("Erreur :");
                        dlgAlert.setPositiveButton("Tant pis...", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }

                    con.disconnect();
                }
                catch (Exception e){
                    e.printStackTrace();

                    System.out.println("Exeption while connectiong to the API : " + e.getMessage() + ", " + e.getCause());
                }
            }
        });
        getStatsThread.start();
        try {
            getStatsThread.join();
            if(OverwatchData.APIRequestSucessfull){
                doStuffWithStats(OverwatchData.APIResponse);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void doStuffWithStats(String rawJsonFromApi) throws JSONException {
        JSONObject stats = new JSONObject(rawJsonFromApi);

        if(stats.has("error")){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this.getContext());
            dlgAlert.setMessage(stats.getString("error"));
            dlgAlert.setTitle("Erreur de l'API :");
            dlgAlert.setPositiveButton("Tant pis...", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        if(stats.getBoolean("private")){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this.getContext());
            dlgAlert.setMessage("Votre profile est privé !\nAllez dans Options > Social > ");
            dlgAlert.setTitle("Erreur de l'API :");
            dlgAlert.setPositiveButton("Tant pis...", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;

        }


        final String Name = stats.getString("name");
        final int Level = stats.getInt("level");
        final int QuickPlayWins = stats.getJSONObject("quickPlayStats").getJSONObject("games").getInt("won");
        final int QuickPlayPlayed = stats.getJSONObject("quickPlayStats").getJSONObject("games").getInt("played");

        TextView battleTag = inflatedView.findViewById(R.id.overwatch_battletag_display);
        TextView QPWins = inflatedView.findViewById(R.id.overwatch_quick_games_won_display);
        TextView QPPlays = inflatedView.findViewById(R.id.overwatch_quick_games_played_display);

        battleTag.setText(Name);
        QPWins.setText(String.valueOf(QuickPlayWins));
        QPPlays.setText(String.valueOf(QuickPlayPlayed));



        // String pageName = obj.getJSONObject("pageInfo").getString("pageName");
        //
        // JSONArray arr = obj.getJSONArray("posts");
        // for (int i = 0; i < arr.length(); i++)
        // {
        //     String post_id = arr.getJSONObject(i).getString("post_id");
        // }
    }

}
