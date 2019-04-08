package com.gamestats.zegolem.gamestats.overwatch;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataDownloader extends AsyncTask<String, Integer, String> {
    static String Platform = "", Region = "", BattleTag = "";

    @Override
    protected String doInBackground(String... strings) {
        Platform = strings[0];
        Region = strings[1];
        BattleTag = strings[2];
        try {
            URL url = new URL("https://owjs.ovh/overall/" + Platform + "/" + Region + "/" + BattleTag.replace("#", "-"));
            System.out.println("https://owjs.ovh/overall/" + Platform + "/" + Region + "/" + BattleTag.replace("#", "-"));
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
            OverwatchFragment.doStuffWithStats(s);

        } catch (Exception e){
            System.out.println("Here is an exception, have fun ;)");
            System.out.println(e.getMessage());
        }
        super.onPostExecute(s);
    }
}
