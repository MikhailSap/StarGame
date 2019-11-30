package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import sap.game.base.ScaledTouchUpButton;
import sap.game.math.Rect;
import sap.game.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {
    GameScreen gameScreen;


    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.03f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
