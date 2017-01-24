package com.rdeciZmaji.pheidippides.webservices;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.script.ScriptEngineManager;

/**
 * Created by fklezin on 13.1.2017.
 */

public class LeaderBoardClient {

    private final static String USER_AGENT = "Mozilla/5.0";

    public ArrayList<Record> getLeaderboard() {
        ArrayList<Record> leaderboard;

        ServiceClient ws = new ServiceClient("http://193.77.150.15:48529/_db/pheidippides/phe/leaderboard", ServiceClient.RequestMethod.POST);

        try {
            String responseStr = ws.requestJson().getString("response");
            JSONArray response = new JSONArray(responseStr);

            leaderboard=new ArrayList<Record>();
            for (int i=0; i<response.length() && i<5;i++){
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



        try {
            String urlParams=URLEncoder.encode(r.getJSONString(), "UTF-8");

            sendingGetRequest(urlParams);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Something wrong with connection");
            return false;
        }

        return true;
    }

    public void sendingGetRequest(String params) {

        try {

            String urlString = "http://193.77.150.15:48529/_db/pheidippides/phe/insertRecord?user="+params;

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // By default it is GET request
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("Sending get request : "+ url);
            System.out.println("Response code : "+ responseCode);

            // Reading response from input Stream
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();

            //printing result from response
            System.out.println(response.toString());

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void sendRequest(Object requestObject, String method) {

        final Json json = new Json();

        String requestJson = json.toJson(requestObject); // this is just an example

        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = "http://193.77.150.15:48529/_db/pheidippides/phe/insertRecord";
        request.setUrl(url);

        request.setContent(requestJson);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if(statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed");
                    return;
                }

                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println(responseJson);

                    //DO some stuff with the response string

                }
                catch(Exception exception) {

                    exception.printStackTrace();
                }
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");

            }

        });

    }

}
