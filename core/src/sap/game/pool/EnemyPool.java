package sap.game.pool;

import sap.game.base.SpritesPool;
import sap.game.math.Rect;
import sap.game.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {
    private Rect worldBounds;
    private BulletPool bulletPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    public EnemyShip getInstance() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
