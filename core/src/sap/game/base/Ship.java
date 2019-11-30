package sap.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;
import sap.game.pool.ExplosionPool;
import sap.game.sprite.Bullet;
import sap.game.sprite.Explosion;

public abstract class Ship extends Sprite {
    protected final Vector2 V_NORMAL = new Vector2();
    protected final Vector2 V = new Vector2();

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected Sound sound;
    protected float bulletHeight;
    protected int damage;

    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    protected float animateInterval = 0.05f;
    protected float animateTimer = animateInterval;


    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        animateTimer += delta;
        if (animateTimer > animateInterval)
            frame = 0;
        pos.mulAdd(V, delta);
    }

    public void damage(int damage) {
        animateTimer = 0;
        frame = 1;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    public abstract boolean isBulletCollision(Rect bullet);

    @Override
    public void destroy() {
        super.destroy();
        if (!this.isOutside(worldBounds))
        boom();
    }

    protected void shoot() {
        sound.play(0.3f);
        Bullet bullet = bulletPool.get();
        bullet.set(worldBounds, bulletV, damage, this, bulletRegion, pos, bulletHeight);
    }

    protected void boom() {
        Explosion explosion = explosionPool.get();
        explosion.set(pos, getHeight());
    }
}
