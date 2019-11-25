package sap.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import sap.game.base.ScaledTouchUpButton;
import sap.game.math.Rect;
import sap.game.screen.GameScreen;


public class ButtonPlay extends ScaledTouchUpButton {
    private Game game;


    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.19f);
        setLeft(worldBounds.getLeft() + 0.015f);
        setBottom(worldBounds.getBottom() + 0.028f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));

    }
}
