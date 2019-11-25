package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import sap.game.base.Sprite;
import sap.game.math.Rect;

public class GameOver extends Sprite {
    private final float BOTTOM_MARGIN = 0.05f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.05f);
        setBottom(getBottom() + BOTTOM_MARGIN);
    }

    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.05f);
        setBottom(getBottom() + BOTTOM_MARGIN);
    }
}
