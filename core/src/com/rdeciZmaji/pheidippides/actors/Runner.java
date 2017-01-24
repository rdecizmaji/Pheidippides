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

package com.rdeciZmaji.pheidippides.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rdeciZmaji.pheidippides.baza.DAO;
import com.rdeciZmaji.pheidippides.baza.Igra;
import com.rdeciZmaji.pheidippides.baza.Igralec;
import com.rdeciZmaji.pheidippides.baza.Level;
import com.rdeciZmaji.pheidippides.baza.Lik;
import com.rdeciZmaji.pheidippides.box2d.RunnerUserData;
import com.rdeciZmaji.pheidippides.enums.Difficulty;
import com.rdeciZmaji.pheidippides.enums.GameState;
import com.rdeciZmaji.pheidippides.stages.GameStage;
import com.rdeciZmaji.pheidippides.utils.AssetsManager;
import com.rdeciZmaji.pheidippides.utils.AudioUtils;
import com.rdeciZmaji.pheidippides.utils.Constants;
import com.rdeciZmaji.pheidippides.utils.GameManager;

import java.util.List;

public class Runner extends GameActor {

    private boolean dodging;
    private boolean jumping;
    private boolean hit;
    private Animation runningAnimation;
    private TextureRegion jumpingTexture;
    private TextureRegion dodgingTexture;
    private TextureRegion hitTexture;
    private float stateTime;
    public static int i;
    public static int sit;
    private Sound jumpSound;
    private Sound hitSound;
    private Difficulty dif;
    private TextureAtlas textureAtlas;

    private int jumpCount;

    public Runner(Body body, int si) {
        super(body);
        i=si;
        jumpCount = 0;
        stateTime = 0f;
        Gdx.app.log("BUTTON", String.valueOf(i));
        if(i==1){
            Constants.RUNNER_JUMPING_LINEAR_IMPULSE= new Vector2(0, 25f);
            runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID);
            jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID);
            dodgingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_DODGING_ASSETS_ID);
            hitTexture = AssetsManager.getTextureRegion(Constants.RUNNER_HIT_ASSETS_ID);
        }
        else if(i==2){
            Constants.RUNNER_JUMPING_LINEAR_IMPULSE= new Vector2(0, 18f);
            runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID_GREEN);
            jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID_GREEN);
            dodgingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_DODGING_ASSETS_ID_GREEN);
            hitTexture = AssetsManager.getTextureRegion(Constants.RUNNER_HIT_ASSETS_ID_GREEN);
        }
        else{
            Constants.RUNNER_JUMPING_LINEAR_IMPULSE= new Vector2(0, 13f);
            runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID_BLUE);
            jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID_BLUE);
            dodgingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_DODGING_ASSETS_ID_BLUE);
            hitTexture = AssetsManager.getTextureRegion(Constants.RUNNER_HIT_ASSETS_ID_BLUE);
        }

        jumpSound = AudioUtils.getInstance().getJumpSound();
        hitSound = AudioUtils.getInstance().getHitSound();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = screenRectangle.x - (screenRectangle.width * 0.1f);
        float y = screenRectangle.y;
        float width = screenRectangle.width * 1.2f;

        if (dodging) {
            batch.draw(dodgingTexture, x, y + screenRectangle.height / 4, width, screenRectangle.height * 3 / 4);
        } else if (hit) {
            // When he's hit we also want to apply rotation if the body has been rotated
            batch.draw(hitTexture, x, y, width * 0.5f, screenRectangle.height * 0.5f, width, screenRectangle.height, 1f,
                    1f, (float) Math.toDegrees(body.getAngle()));
        } else if (jumping) {
            batch.draw(jumpingTexture, x, y, width, screenRectangle.height);
        } else {
            // Running
            if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
                stateTime += Gdx.graphics.getDeltaTime();
            }
            batch.draw(runningAnimation.getKeyFrame(stateTime, true), x, y, width, screenRectangle.height);
        }
    }

    @Override
    public RunnerUserData getUserData() {
       return (RunnerUserData) userData;
    }

    public void jump() {

        if (!(jumping || dodging || hit)) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
            AudioUtils.getInstance().playSound(jumpSound);
            jumpCount++;
        }

    }

    public void landed() {
        jumping = false;
    }

    public void dodge() {
        if (!(jumping || hit)) {
            body.setTransform(getUserData().getDodgePosition(), getUserData().getDodgeAngle());
            dodging = true;
        }
    }

    public void stopDodge() {
        dodging = false;
        // If the runner is hit don't force him back to the running position
        if (!hit) {
            body.setTransform(getUserData().getRunningPosition(), 0f);
        }
    }

    public boolean isDodging() {
        return dodging;
    }

    public void hit() {
        body.applyAngularImpulse(getUserData().getHitAngularImpulse(), true);
        hit = true;
        AudioUtils.getInstance().playSound(hitSound);

        DAO d=new DAO();
        d.open();
        List<Igralec> igr=d.getIgralec();
        Igralec ig=igr.get(igr.size()-1);
        int lv=1;
        if(dif!=null){
            lv=dif.getLevel();
        }
        else{
            lv=2;
        }
        int tocke=GameStage.score.getScore();
        int level_fk=lv;
        int igralec_fk= (int) ig.getId();
        int lik_fk= i;
        d.createIgra(tocke,level_fk, igralec_fk, lik_fk);

        List<Igra> igre=d.getIgre();
        for(int j=0; j<igre.size(); j++){
           Igra igr1= igre.get(j);
            Gdx.app.log("IGRA_ID", String.valueOf(igr1.getId()));
            Gdx.app.log("IGRA_TOCKE", String.valueOf(igr1.getTocke()));
            Gdx.app.log("IGRA_LEVEL_FK", String.valueOf(igr1.getLevel()));
            Gdx.app.log("IGRA_IGRALEC_FK", String.valueOf(igr1.getIgralec()));
            Gdx.app.log("IGRA_LIK_FK", String.valueOf(lik_fk));
        }
        List<Lik> liki=d.getLiki();
        for(int j=0; j<liki.size(); j++){
            Lik l=liki.get(j);
            Gdx.app.log("LIK_ID", String.valueOf(l.getId()));
            Gdx.app.log("LIK_NAZIV", String.valueOf(l.getNaziv()));
            Gdx.app.log("LIK_SKOK", String.valueOf(l.getSkok()));
        }
        List<Igralec> igralci=d.getIgralec();
        for(int j=0; j<igralci.size(); j++){
            Igralec igr2=igralci.get(j);
            Gdx.app.log("IGRALEC_ID", String.valueOf(igr2.getId()));
            Gdx.app.log("IGRALEC_IME", String.valueOf(igr2.getIme()));
            Gdx.app.log("IGRALEC_LOGIN", String.valueOf(igr2.getLogin()));
        }
        List<Level> level=d.getLevel();
        for(int j=0; j<level.size(); j++){
            Level lve=level.get(j);
            Gdx.app.log("LEVEL_ID", String.valueOf(lve.getId()));
            Gdx.app.log("LEVEL_NAZIV_LEVEL", String.valueOf(lve.getNaziv()));
            Gdx.app.log("LEVEL_OVIRE", String.valueOf(lve.getOvire()));
            Gdx.app.log("LEVEL_GRAVITACIJA", String.valueOf(lve.getGravitacija()));
        }
        d.close();
    }

    public boolean isHit() {
        return hit;
    }

    public void onDifficultyChange(Difficulty newDifficulty) {
        dif=newDifficulty;
        setGravityScale(newDifficulty.getRunnerGravityScale());
        Vector2 v1=null;
        if(i==1){
            v1= new Vector2(0, 25f);
        }else if(i==2){
            v1= new Vector2(0, 18f);
        }else{
            v1= new Vector2(0, 13f);
        }
        getUserData().setJumpingLinearImpulse(v1);
    }

    public void setGravityScale(float gravityScale) {
        body.setGravityScale(gravityScale);
        body.resetMassData();
    }

    public int getJumpCount() {
        return jumpCount;
    }
}
