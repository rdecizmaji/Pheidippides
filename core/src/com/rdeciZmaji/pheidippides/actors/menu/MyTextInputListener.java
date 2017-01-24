package com.rdeciZmaji.pheidippides.actors.menu;

import com.badlogic.gdx.Input;
import com.rdeciZmaji.pheidippides.utils.GameManager;
import com.rdeciZmaji.pheidippides.webservices.LeaderBoardClient;
import com.rdeciZmaji.pheidippides.webservices.Record;

public class MyTextInputListener implements Input.TextInputListener {
    private int score;

    public MyTextInputListener(int score) {
        this.score=score;
    }

    @Override
    public void input (String text) {

        if (text.length()>3&text.length()<15){


            LeaderBoardClient lbc=new LeaderBoardClient();
            lbc.insertRecord(new Record(this.score,text));
        }
    }

    @Override
    public void canceled () {
    }
}