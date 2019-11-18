package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.Sprite;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;

public class EnemyShip extends Sprite {

    private final Vector2 v = new Vector2(0, -0.1f);

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV = new Vector2(0, 0.5f);


    private final float reloadInterval = 0.2f;
    private float reloadTimer;

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("enemy0"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getTop());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }
}
