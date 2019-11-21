package sap.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.BaseScreen;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;
import sap.game.pool.EnemyPool;
import sap.game.sprite.Background;
import sap.game.sprite.SpaceShip;
import sap.game.sprite.Star;
import sap.game.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private final int STAR_COUNT = 64;
    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private SpaceShip ship;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private Music music;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(new TextureRegion(bg));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        ship = new SpaceShip(atlas, bulletPool);
        enemyPool = new EnemyPool(worldBounds, bulletPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        ship.draw(batch);
        bulletPool.drawSprites(batch);
        enemyPool.drawSprites(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star : stars)
            star.update(delta);
        ship.update(delta);
        bulletPool.updateSprites(delta);
        enemyPool.updateSprites(delta);
        enemyEmitter.generate(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars)
            star.resize(worldBounds);
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.stop();
        bg.dispose();
        atlas.dispose();
        ship.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        enemyEmitter.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.toucUp(touch, pointer);
        return false;
    }
}
