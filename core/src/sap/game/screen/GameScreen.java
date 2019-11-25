package sap.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import sap.game.base.BaseScreen;
import sap.game.base.Ship;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;
import sap.game.pool.EnemyPool;
import sap.game.pool.ExplosionPool;
import sap.game.sprite.Background;
import sap.game.sprite.Bullet;
import sap.game.sprite.EnemyShip;
import sap.game.sprite.GameOver;
import sap.game.sprite.SpaceShip;
import sap.game.sprite.Star;
import sap.game.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private Game game;
    private final int STAR_COUNT = 64;
    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private GameOver gameOver;
    private SpaceShip ship;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;
    private Music music;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(new TextureRegion(bg));
        gameOver = new GameOver(atlas);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        ship = new SpaceShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }

    @Override
    public void render(float delta) {
        if (ship.isDestroyed())
            game.setScreen(new GemeOverScreen(game));
        update(delta);
        checkCollisions();
        draw();
    }

    private void update(float delta) {
        for (Star star : stars)
            star.update(delta);
        ship.update(delta);
        bulletPool.updateSprites(delta);
        enemyPool.updateSprites(delta);
        explosionPool.updateSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        ship.draw(batch);
        bulletPool.drawSprites(batch);
        enemyPool.drawSprites(batch);
        explosionPool.drawSprites(batch);
        batch.end();
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
        explosionPool.dispose();
        enemyEmitter.dispose();
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShips = enemyPool.getActiveSprites();
        List<Bullet> bullets = bulletPool.getActiveSprites();
        Ship checkShip;
        float miniDist;
        for (EnemyShip enemy : enemyShips) {
            miniDist = enemy.getHalfWidth() + ship.getHalfWidth();
            if (enemy.pos.dst(ship.pos) < miniDist) {
                enemy.destroy();
                ship.damage(enemy.getDamage());
            }
            for (Bullet bullet : bullets) {
                checkShip = bullet.getOwner().equals(ship) ? enemy : ship;
                if (checkShip.isBulletCollision(bullet)) {
                    checkShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }

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
