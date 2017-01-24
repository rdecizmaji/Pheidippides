package com.rdeciZmaji.pheidippides.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.rdeciZmaji.pheidippides.enums.GameState;
import com.rdeciZmaji.pheidippides.utils.Constants;
import com.rdeciZmaji.pheidippides.utils.GameManager;

/**
 * Created by Spela on 11.1.2017.
 */

public class ChooseButton extends GameButton {
    public interface ChooseButtonListener {
        public void onChoose(int i);
    }

    private ChooseButton.ChooseButtonListener listener;
    private int buttonNumber;

    public ChooseButton(Rectangle bounds, ChooseButton.ChooseButtonListener listener, int bn) {
        super(bounds, bn);
        this.listener = listener;

    }

    @Override
    protected String getRegionName() {
        if(buttonNumber==1){
            return Constants.RUNNER_DODGING_REGION_NAME;
        }
        else if(buttonNumber==2){
            return Constants.RUNNER_HIT_REGION_NAME;
        }
        else if(buttonNumber==3){
            return Constants.RUNNER_JUMPING_REGION_NAME;
        }
        return Constants.PLAY_REGION_NAME;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameManager.getInstance().getGameState() != GameState.OVER) {
            remove();
        }
    }

    @Override
    public void touched() {
        listener.onChoose(buttonNumber);
    }

    @Override
    public void setButtonNumber(int bn){
        this.buttonNumber=bn;
    }
}
