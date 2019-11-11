package sap.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.Sprite;

public class Logo extends Sprite {
    private static final float V_LEN = 0.03f;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 buff;

    public Logo(TextureRegion region) {
        super(region);
        touch = new Vector2();
        v = new Vector2();
        buff = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        buff.set(touch);
        if (buff.sub(pos).len() > V_LEN)
            pos.add(v);
        else
            pos.set(touch);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println(touch.x + " " + touch.y);
        System.out.println(pos.x + " " + pos.y);
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos).setLength(V_LEN));
        return false;
    }
}
