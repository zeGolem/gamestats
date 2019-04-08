package com.gamestats.zegolem.gamestats.league;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataDownloader extends AsyncTask<String, Integer, String> {
    static String accountName = "";
    @Override
    protected String doInBackground(String... strings) {
        accountName = strings[0];

        try {
            URL url = new URL("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + accountName + "?api_key=RGAPI-c75f30c3-3e97-4ed5-8920-5ae53dc44bfa");
            // System.out.println("https://owjs.ovh/overall/" + Platform + "/" + Region + "/" + BattleTag.replace("#", "-"));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            System.out.println("Request created, sending...");
            int status = con.getResponseCode();
            System.out.println("Response status : " + status);
            StringBuilder content = new StringBuilder();
            if(status == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println("Response : " + content.toString());
                con.disconnect();
            } else {
                return null;
            }
            con.disconnect();
            return content.toString();
        }
        catch (Exception e){
            e.printStackTrace();

            System.out.println("Exception while connecting to the API : " + e.getMessage() + ", " + e.getCause());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            LeagueFragment.doStuffWithStats(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }
}
