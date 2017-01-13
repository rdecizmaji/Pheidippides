package com.rdeciZmaji.pheidippides.webservices;

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
            JSONObject response = ws.requestJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Something wrong with connection");
        }


        return null;
    }
}
