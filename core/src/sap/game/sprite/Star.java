package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.Sprite;
import sap.game.math.Rect;
import sap.game.math.Rnd;

public class Star extends Sprite {
    private Vector2 v;
    private Rect worldBounds;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f, 0.015f));
        float vx = Rnd.nextFloat(-0.0001f, 0.0001f);
        float vy = Rnd.nextFloat(-0.004f, -0.004f);
        v = new Vector2();
        v.set(vx, vy);
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.add(v);
        checkBounds();
    }

    public void checkBounds() {
        if (getLeft() > worldBounds.getRight())
            setLeft(worldBounds.getLeft());
        if (getRight() < worldBounds.getLeft())
            setRight(worldBounds.getRight());
        if (getTop() < worldBounds.getBottom())
            setTop(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop())
            setBottom(worldBounds.getBottom());
    }
}
