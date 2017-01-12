package com.rdeciZmaji.pheidippides.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rdeciZmaji.pheidippides.utils.AssetsManager;
import com.rdeciZmaji.pheidippides.utils.Constants;

/**
 * Created by Spela on 10.1.2017.
 */

public class GameLabelC extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    public GameLabelC(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getSmallFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.drawWrapped(batch, "Choose character:", bounds.x, bounds.y, bounds.width, BitmapFont.HAlignment.CENTER);
    }

}