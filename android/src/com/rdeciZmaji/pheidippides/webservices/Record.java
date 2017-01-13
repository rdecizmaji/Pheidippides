package com.rdeciZmaji.pheidippides.webservices;

import org.json.JSONObject;

/**
 * Created by fklezin on 13.1.2017.
 */

public class Record {
    private String score;
    private String nickname;
    private String mac;
    private long epoch;

    public Record(String score, String nickname, String mac, long epoch) {
        this.score = score;
        this.nickname = nickname;
        this.mac = mac;
        this.epoch = epoch;
    }

    public Record(JSONObject response){
        //@TODO
    }

    public String getJSONString(){
        String json;
        json="{" +
                "'score':"+score+
                ",'nickname':"+ "'" +nickname + "'" +
                ",'mac':"+ "'" +mac + "'" +
                ",'epoch':"+ "'" +epoch + "'" +
              "}";

        return json;
    }


}
