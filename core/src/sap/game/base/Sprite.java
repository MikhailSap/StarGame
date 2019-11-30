package sap.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import sap.game.math.Rect;
import sap.game.utils.Regions;

public class Sprite extends Rect {
    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    protected boolean destroyed;

    public Sprite() {

    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("region is null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        if (region == null) {
            throw new NullPointerException("region is null");
        }
        regions = Regions.split(region, rows, cols, frames);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale, angle
        );
    }

    public void setHeightProportion(float height) {
        setHalfHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setHalfWidth(height * aspect);

    }

    public void update(float delta) {

    }

    public void resize(Rect worldBounds) {

    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;

    }

    public boolean toucUp(Vector2 touch, int pointer) {
        return false;
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Sprite flushDestroyed() {
        destroyed = false;
        return this;
    }
}
