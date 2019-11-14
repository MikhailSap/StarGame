package sap.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.BaseScreen;
import sap.game.math.Rect;
import sap.game.sprite.Background;
import sap.game.sprite.SpaceShip;
import sap.game.sprite.Star;

public class GameScreen extends BaseScreen {
    private final int STAR_COUNT = 128;
    private Texture bg;
    private TextureAtlas atlas;
    private Star[] stars;
    private Background background;
    private SpaceShip ship;
    private SpaceShip shipFlash;
    private int test;


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
        TextureRegion textureRegion = atlas.findRegion("main_ship");
        TextureRegion[][] split = textureRegion.split(textureRegion.getRegionWidth()/2, textureRegion.getRegionHeight());
        ship = new SpaceShip(split[0][0]);
        shipFlash = new SpaceShip(split[0][1]);
    }

    @Override
    public void render(float delta) {
        draw();
        update(delta);

    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        if (((test/10)%2==0)) {
            ship.draw(batch);
        }
        else {
            shipFlash.draw(batch);
        }
        batch.end();
    }

    private void update(float delta) {
        for (Star star : stars)
            star.update(delta);
        test++;
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars)
            star.resize(worldBounds);
        ship.resize(worldBounds);
        shipFlash.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
