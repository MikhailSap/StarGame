package sap.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import sap.game.base.ScaledTouchUpButton;
import sap.game.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        float posX = worldBounds.getRight()-0.1f;
        float posy = worldBounds.getBottom()+0.1f;
        pos.set(posX, posy);
    }
}
