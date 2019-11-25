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
import sap.game.sprite.Background;
import sap.game.sprite.ButtonExit;
import sap.game.sprite.GameOver;
import sap.game.sprite.ButtonNewGame;
import sap.game.sprite.Star;

public class GemeOverScreen extends BaseScreen {
    private Game game;
    private final int STAR_COUNT = 64;
    private Texture bg;
    private TextureAtlas atlas;
    private TextureAtlas buttonAtlas;
    private Star[] stars;
    private Background background;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;
    private ButtonExit buttonExit;
    private Music music;

    public GemeOverScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        buttonAtlas = new TextureAtlas("textures/menuAtlas.tpack");
        background = new Background(new TextureRegion(bg));
        gameOver = new GameOver(atlas);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        buttonNewGame = new ButtonNewGame(atlas, game);
        buttonExit = new ButtonExit(buttonAtlas);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/start_theme.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Star star : stars)
            star.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        gameOver.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        buttonNewGame.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        gameOver.resize(worldBounds);
        for (Star star : stars)
            star.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonExit.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonNewGame.touchDown(touch, pointer);
        buttonExit.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonNewGame.toucUp(touch, pointer);
        buttonExit.toucUp(touch, pointer);
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        music.stop();
    }

}
