package sap.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import java.util.List;
import sap.game.base.BaseScreen;
import sap.game.base.Font;
import sap.game.math.Rect;
import sap.game.pool.BulletPool;
import sap.game.pool.EnemyPool;
import sap.game.pool.ExplosionPool;
import sap.game.sprite.Background;
import sap.game.sprite.Bullet;
import sap.game.sprite.ButtonNewGame;
import sap.game.sprite.EnemyShip;
import sap.game.sprite.GameOver;
import sap.game.sprite.SpaceShip;
import sap.game.sprite.Star;
import sap.game.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private enum State {PLAYING, PAUSE, GAME_OVER};
    private State state;
    private State prevState;

    private final int STAR_COUNT = 64;
    private final String FRAGS = "Frags: ";
    private final String HP = "HP: ";
    private final String LEVEL = "Level: ";

    private Texture bg;
    private TextureAtlas atlas;

    private Star[] stars;
    private Background background;
    private GameOver gameOver;

    private ButtonNewGame newGame;
    private SpaceShip ship;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;

    private Music playingTheme;
    private Font font;
    private int frags;

    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private int prevLevel;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(new TextureRegion(bg));
        gameOver = new GameOver(atlas);
        newGame = new ButtonNewGame(atlas, this);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        ship = new SpaceShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        playingTheme = Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3"));
        playingTheme.setLooping(true);
        playingTheme.setVolume(0.1f);
        playingTheme.play();
        font = new Font("font/abc.fnt", "font/abc.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        state = State.PLAYING;
        prevLevel = enemyEmitter.getLevel();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars)
            star.resize(worldBounds);
        ship.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
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
        if (state == State.GAME_OVER)
            newGame.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.toucUp(touch, pointer);
        if (state == State.GAME_OVER)
            newGame.toucUp(touch, pointer);
        return false;
    }

    public void startNewGame() {
        state = State.PLAYING;
        menuTheme.stop();
        playingTheme.play();
        ship.startNewGame();
        bulletPool.freeAllSprites();
        enemyPool.freeAllSprites();
        explosionPool.freeAllSprites();
        frags = 0;
    }

    @Override
    public void pause() {
        prevState = state;
        if (state == State.PLAYING)
            playingTheme.pause();
        else
            menuTheme.pause();
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        state = prevState;
        if (prevState == State.PLAYING)
            playingTheme.play();
        else
            menuTheme.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        playingTheme.stop();
        menuTheme.stop();
        bg.dispose();
        atlas.dispose();
        ship.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        enemyEmitter.dispose();
    }

    private void update(float delta) {
        for (Star star : stars)
            star.update(delta);
        if (state == State.PLAYING) {
            ship.update(delta);
            bulletPool.updateSprites(delta);
            enemyPool.updateSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
        explosionPool.updateSprites(delta);
        if (enemyEmitter.getLevel() > prevLevel) {
            prevLevel = enemyEmitter.getLevel();
            ship.setHP(ship.getHP() + 10);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        if (state == State.PLAYING) {
            ship.draw(batch);
            bulletPool.drawSprites(batch);
            enemyPool.drawSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        printStat();
        explosionPool.drawSprites(batch);
        batch.end();
    }

    private void printStat() {
        sbFrags.setLength(0);
        float fragsX = worldBounds.getLeft() + 0.01f;
        float fragsY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbFrags.append(FRAGS).append(frags), fragsX, fragsY);
        sbHP.setLength(0);
        float hpX = worldBounds.pos.x;
        float hpY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbHP.append(HP).append(ship.getHP()), hpX, hpY, Align.center);
        sbLevel.setLength(0);
        float levelX = worldBounds.getRight() - 0.01f;
        float levelY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), levelX, levelY, Align.right);
    }

    private void checkCollisions() {
        if (state != State.PLAYING)
            return;
        List<EnemyShip> enemyShips = enemyPool.getActiveSprites();
        List<Bullet> bullets = bulletPool.getActiveSprites();
        float miniDist;
        for (EnemyShip enemy : enemyShips) {
            miniDist = enemy.getHalfWidth() + ship.getHalfWidth();
            if (enemy.pos.dst(ship.pos) < miniDist) {
                ship.damage(enemy.getDamage());
                enemy.destroy();
                frags++;
                if (ship.isDestroyed()) {
                    state = State.GAME_OVER;
                    playingTheme.stop();
                    menuTheme.play();
                }
            }

            for (Bullet bullet : bullets) {
                if (bullet.getOwner().equals(ship) && enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed())
                        frags++;
                }
            }
        }

        for (Bullet bullet : bullets) {
            if (!bullet.getOwner().equals(ship) && ship.isBulletCollision(bullet)) {
                ship.damage(bullet.getDamage());
                bullet.destroy();
                if (ship.isDestroyed()) {
                    state = State.GAME_OVER;
                    playingTheme.stop();
                    menuTheme.play();
                }
            }
        }
    }
}
