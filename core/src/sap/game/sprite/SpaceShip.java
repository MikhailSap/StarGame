package sap.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import sap.game.base.Ship;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;
import sap.game.pool.ExplosionPool;

public class SpaceShip extends Ship {
    private final int HP = 30;
    private final Vector2 START_POS = new Vector2();
    private final float BOTTOM_MARGIN = 0.05f;
    private final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public SpaceShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        V_NORMAL.set(0.5f, 0);
        reloadInterval = 0.2f;
        bulletHeight = 0.01f;
        damage = 1;
        hp = HP;
        bulletV.set(0, 0.5f);
    }

    public void startNewGame() {
        hp = HP;
        pressedLeft = false;
        pressedRight = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        flushDestroyed();
        stop();
        pos.set(START_POS);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        reloadTimer += delta;
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER)
                return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER)
                return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean toucUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                pressedRight = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft = true;
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }


    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
        }

    }

    public void dispose() {
        sound.dispose();
    }

    private void moveRight() {
        V.set(V_NORMAL);
    }

    private void moveLeft() {
        V.set(V_NORMAL).rotate(180);
    }

    private void stop() {
        V.setZero();
    }

    public boolean isBulletCollision(Rect bullet) {
         return bullet.getLeft() > getLeft() &&
                bullet.getRight() < getRight() &&
                bullet.getBottom() < pos.y;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
