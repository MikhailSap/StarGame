package sap.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import sap.game.base.ScaledTouchUpButton;
import sap.game.math.Rect;
import sap.game.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {
    private Game game;


    public ButtonNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.03f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.03f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
