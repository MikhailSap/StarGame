package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sap.game.base.Sprite;
import sap.game.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
        setHeightProportion(1f);
    }

    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
