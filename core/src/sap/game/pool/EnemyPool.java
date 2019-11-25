package sap.game.pool;

import sap.game.base.SpritesPool;
import sap.game.math.Rect;
import sap.game.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {
    private Rect worldBounds;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    public EnemyShip getInstance() {
        return new EnemyShip(bulletPool, worldBounds, explosionPool);
    }
}
