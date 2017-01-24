package com.rdeciZmaji.pheidippides.webservices;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fklezin on 13.1.2017.
 */

public class Record {
    private int score;
    private String nickname;
    private String id;
    private long epoch;

    public Record(int score, String nickname, String id, long epoch) {
        this.score = score;
        this.nickname = nickname;
        this.id = id;
        this.epoch = epoch;
    }

    public Record(JSONObject response){
        try{
            this.score= Integer.parseInt(response.getString("score"));
            this.nickname= response.getString("user");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public Record(int score, String nickname) {
        this.score = score;
        this.nickname=nickname;
    }

    public String getJSONString(){
        String json;
        json="{" +
                "'score':"+score+
                ",'user':"+ "'" +nickname + "'" +
                ",'id':"+ "'" +id + "'" +
                ",'epoch':"+ "'" +epoch + "'" +
              "}";

        return json;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }
}
