package sap.game.pool;

import sap.game.base.SpritesPool;
import sap.game.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    public Bullet getInstance() {
        return new Bullet();
    }
}
