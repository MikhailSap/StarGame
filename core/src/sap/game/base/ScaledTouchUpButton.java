package sap.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledTouchUpButton extends Sprite {
    private int pointer;
    private boolean pressed;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.pointer = pointer;
        if (pressed || !isMe(touch))
            return false;
        pressed = true;
        scale = 0.9f;
        return false;
    }

    @Override
    public boolean toucUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed)
            return false;
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 1;
        return false;
    }

    public abstract void action();
}
