package sap.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.1f;
    private float animateTimer;
    private Sound sound;

    public Explosion(TextureAtlas atlas, Sound sound) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.sound = sound;
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        sound.play(0.1f);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer > animateInterval)
            frame++;
        if (frame == regions.length)
            destroy();

    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
