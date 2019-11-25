package sap.game.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import sap.game.base.SpritesPool;
import sap.game.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound sound;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    public Explosion getInstance() {
        return new Explosion(atlas, sound);
    }
}
