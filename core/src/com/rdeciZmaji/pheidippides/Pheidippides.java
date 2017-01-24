/*
 * Copyright (c) 2015. William Mora
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

package com.rdeciZmaji.pheidippides;

import com.badlogic.gdx.Game;
import com.rdeciZmaji.pheidippides.baza.DAO;
import com.rdeciZmaji.pheidippides.baza.Level;
import com.rdeciZmaji.pheidippides.baza.Lik;
import com.rdeciZmaji.pheidippides.screens.GameScreen;
import com.rdeciZmaji.pheidippides.utils.AssetsManager;
import com.rdeciZmaji.pheidippides.utils.AudioUtils;
import com.rdeciZmaji.pheidippides.utils.GameEventListener;
import com.rdeciZmaji.pheidippides.utils.GameManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Pheidippides extends Game {

    public Pheidippides(GameEventListener listener) {
        GameManager.getInstance().setGameEventListener(listener);
    }

    @Override
    public void create() {
        AssetsManager.loadAssets();
        setScreen(new GameScreen());
        DAO d=new DAO();
        d.open();
       List<Lik> listL=d.getLiki();
        if(listL.size()<3){
            d.createLik("A", "25f");
            d.createLik("B", "18f");
            d.createLik("C", "13f");
        }
        List<Level> level=d.getLevel();
        if(level.size()<13){
            d.createLevel("1","new Vector2(-12f, 0f)","1.1f");
            d.createLevel("2","new Vector2(-12f, 0f)","1.1f");
            d.createLevel("3","new Vector2(-14f, 0f)","1.1f");
            d.createLevel("4","new Vector2(-16f, 0f)","1.1f");
            d.createLevel("5","new Vector2(-18f, 0f)","1.1f");
            d.createLevel("6","new Vector2(-20f, 0f)","1.3f");
            d.createLevel("7","new Vector2(-22f, 0f)","1.3f");
            d.createLevel("8","new Vector2(-24f, 0f)","1.3f");
            d.createLevel("9","new Vector2(-26f, 0f)","1.5f");
            d.createLevel("10","new Vector2(-28f, 0f)","1.5f");
            d.createLevel("11","new Vector2(-30f, 0f)","1.6f");
            d.createLevel("12","new Vector2(-32f, 0f)","1.7f");
            d.createLevel("13","new Vector2(-34f, 0f)","2.1f");
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String da=dateFormat.format(date);
        d.createIgralec("unknown",da);
        d.close();
    }

    @Override
    public void dispose() {
        super.dispose();
        AudioUtils.dispose();
        AssetsManager.dispose();
    }
}
