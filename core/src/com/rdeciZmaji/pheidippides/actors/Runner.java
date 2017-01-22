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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rdeciZmaji.pheidippides.box2d.RunnerUserData;
import com.rdeciZmaji.pheidippides.enums.GameState;
import com.rdeciZmaji.pheidippides.utils.AssetsManager;
import com.rdeciZmaji.pheidippides.utils.AudioUtils;
import com.rdeciZmaji.pheidippides.utils.Constants;
import com.rdeciZmaji.pheidippides.utils.GameManager;
import com.rdeciZmaji.pheidippides.enums.Difficulty;

public class Runner extends GameActor {

    private boolean dodging;
    private boolean jumping;
    private boolean hit;
    private Animation runningAnimation;
    private TextureRegion jumpingTexture;
    private TextureRegion dodgingTexture;
    private TextureRegion hitTexture;
    private float stateTime;
    private static int i;
    private Sound jumpSound;
    private Sound hitSound;

    private int jumpCount;

    public Runner(Body body, int si) {
        super(body);
        i=si;
        jumpCount = 0;
        stateTime = 0f;
        if(i==1){
            Constants.RUNNER_JUMPING_LINEAR_IMPULSE= new Vector2(0, 25f);
            runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID);
            jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID);
            dodgingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_DODGING_ASSETS_ID);
            hitTexture = AssetsManager.getTextureRegion(Constants.RUNNER_HIT_ASSETS_ID);
        }
        else if(i==2){
            Constants.RUNNER_JUMPING_LINEAR_IMPULSE= new Vector2(0, 18f);
            runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID);
            jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID);
            dodgingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_DODGING_ASSETS_ID);
            hitTexture = AssetsManager.getTextureRegion(Constants.RUNNER_HIT_ASSETS_ID);
        }
        else{
            Constants.RUNNER_JUMPING_LINEAR_IMPULSE= new Vector2(0, 13f);
            runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID);
            jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID);
            dodgingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_DODGING_ASSETS_ID);
            hitTexture = AssetsManager.getTextureRegion(Constants.RUNNER_HIT_ASSETS_ID);
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
    }

    public boolean isHit() {
        return hit;
    }

    public void onDifficultyChange(Difficulty newDifficulty) {
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
