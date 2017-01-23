package com.rdeciZmaji.pheidippides.webservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by fklezin on 13.1.2017.
 */

public class LeaderBoardClient {

    public ArrayList<Record> getLeaderboard() {
        ArrayList<Record> leaderboard;

        ServiceClient ws = new ServiceClient("http://193.77.150.15:48529/_db/pheidippides/phe/leaderboard", ServiceClient.RequestMethod.POST);

        try {
            String responseStr = ws.requestJson().getString("response");
            JSONArray response = new JSONArray(responseStr);

            leaderboard=new ArrayList<>();
            for (int i=0; i<response.length();i++){
                Record r = new Record(response.getJSONObject(i));
                leaderboard.add(r);
            }

            return  leaderboard;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Something wrong with connection");
        }


        return null;
    }

    public boolean insertRecord(Record r){

        ServiceClient ws = new ServiceClient("http://193.77.150.15:48529/_db/pheidippides/phe/insertRecord", ServiceClient.RequestMethod.POST);
        ws.addParam("user",r.getJSONString());
        try {
            JSONObject response = ws.requestJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Something wrong with connection");
            return false;
        }

        return true;
    }
}
