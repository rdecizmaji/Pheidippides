/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rdeciZmaji.pheidippides.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rdeciZmaji.pheidippides.utils.AssetsManager;
import com.rdeciZmaji.pheidippides.webservices.LeaderBoardClient;
import com.rdeciZmaji.pheidippides.webservices.Record;

import java.util.ArrayList;

public class LeaderboardLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;
    private String leaderboardStr;

    public LeaderboardLabel(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getSmallFont();
        LeaderBoardClient lbc=new LeaderBoardClient();
        this.leaderboardStr = new String();
        ArrayList<Record> leaderboard=lbc.getLeaderboard();
        leaderboardToString(leaderboard);

    }

    private void leaderboardToString(ArrayList<Record> leaderboard) {
        for (int i=0; i<leaderboard.size() ;i++){
            Record r=leaderboard.get(i);
            this.leaderboardStr +=i+1+ ". "+r.getNickname()+" "+r.getScore();
            this.leaderboardStr +="\n";
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.drawWrapped(batch,this.leaderboardStr, bounds.x, bounds.y, bounds.width, BitmapFont.HAlignment.CENTER);
    }

}
