package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import sap.game.base.Sprite;
import sap.game.math.Rect;

public class GameOver extends Sprite {
    private final float BOTTOM_MARGIN = 0.08f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setBottom(getBottom() + BOTTOM_MARGIN);
    }
}
