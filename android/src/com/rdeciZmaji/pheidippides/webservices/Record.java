package com.rdeciZmaji.pheidippides.webservices;

import android.content.Context;
import android.telephony.TelephonyManager;

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
        //@TODO
    }

    public String getJSONString(){
        String json;
        json="{" +
                "'score':"+score+
                ",'nickname':"+ "'" +nickname + "'" +
                ",'id':"+ "'" +id + "'" +
                ",'epoch':"+ "'" +epoch + "'" +
              "}";

        return json;
    }


}
