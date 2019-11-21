package sap.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {
    protected List<T> activePool = new ArrayList<>();
    protected List<T> freePool = new ArrayList<>();

    public T get() {
        T object;
        if (freePool.isEmpty()) {
            object = getInstance();
            activePool.add(object);
        } else {
            object = freePool.remove(freePool.size()-1);
            activePool.add(object);
        }
        return object;
    }


    public void updateSprites(float delta) {
        for (int i = 0; i < activePool.size(); i++) {
            if (activePool.get(i).isDestroyed()) {
                freePool.add((T)activePool.remove(i).flushDestroyed());
                i--;
            } else {
                activePool.get(i).update(delta);
            }
        }
    }

    public void drawSprites(SpriteBatch batch) {
        for (T sprite : activePool)
            if (!sprite.isDestroyed())
            sprite.draw(batch);
    }

    public List<T> getPool() {
        return activePool;
    }

    public void dispose() {
        activePool.clear();
        freePool.clear();
    }

    public abstract T getInstance();

}
