package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import sap.game.base.Ship;
import sap.game.base.Sprite;
import sap.game.math.Rect;

public class Bullet extends Sprite {
    private Rect worldBounds;
    private final Vector2 v = new Vector2();
    private int damage;
    private Ship owner;


    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(
            Rect worldBounds,
            Vector2 v,
            int damage,
            Ship owner,
            TextureRegion region,
            Vector2 pos,
            float height
            ) {
        this.worldBounds = worldBounds;
        this.v.set(v);
        this.damage = damage;
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds))
            this.destroy();
    }

    public int getDamage() {
        return damage;
    }

    public Ship getOwner() {
        return owner;
    }
}
