package sap.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import sap.game.base.BaseScreen;
import sap.game.math.Rect;
import sap.game.sprite.Background;
import sap.game.sprite.ButtonExit;
import sap.game.sprite.ButtonPlay;
import sap.game.sprite.Star;

public class MenuScreen extends BaseScreen {
    private Game game;
    private final int STAR_COUNT = 128;
    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        background = new Background(new TextureRegion(bg));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        menuTheme.setVolume(0.05f);
        menuTheme.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
        update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars)
            star.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.toucUp(touch, pointer);
        buttonPlay.toucUp(touch, pointer);
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTheme.stop();
        bg.dispose();
        atlas.dispose();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();

    }

    private void update(float delta) {
        for (Star star : stars)
            star.update(delta);
    }
}
