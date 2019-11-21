package sap.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.Ship;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;

public class EnemyShip extends Ship {

    private final Vector2 V_START = new Vector2(0,-0.5f);
    private boolean flag;

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v.set(V_START);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!flag && getBottom() < worldBounds.getTop()-getHeight()) {
            this.v.set(v0);
            this.reloadTimer = reloadInterval;
            this.flag = true;
        }
        if (getTop() < worldBounds.getBottom()) {
            this.flag = false;
            this.v.set(V_START);
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            Sound sound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.sound = sound;
        setHeightProportion(height);
        this.hp = hp;
    }
}
