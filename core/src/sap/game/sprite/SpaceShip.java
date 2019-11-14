package sap.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sap.game.base.Sprite;
import sap.game.math.Rect;

public class SpaceShip extends Sprite {

    public SpaceShip(TextureRegion textureRegion) {
        super(textureRegion);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        float posX = worldBounds.getLeft() + 0.38f;
        float posY = worldBounds.getBottom() + 0.1f;
        pos.set(posX, posY);
    }
}
