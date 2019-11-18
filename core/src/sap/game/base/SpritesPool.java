package sap.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {
    protected List<T> pool = new ArrayList<>();

    public abstract T get();


    public void updateSprites(float delta) {
        for (T sprite : pool)
            sprite.update(delta);
    }

    public void drawSprites(SpriteBatch batch) {
        for (T sprite : pool)
            sprite.draw(batch);
    }

    public void freeSprites() {
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i).isDestroyed()) {
                pool.remove(i);
                i--;
            }
        }
    }

    public List<T> getPool() {
        return pool;
    }

    public void dispose() {
        pool.clear();
    }

}
