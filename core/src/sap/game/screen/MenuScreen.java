package sap.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import sap.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Vector2 position;
    private Vector2 bound;
    private Vector2 v;
    private int drawWidth = 50;
    private int drawHeight = 50;
    private boolean isKeyDown;


    @Override
    public void show() {
        super.show();
        img = new Texture("zonda.jpg");

        position = new Vector2(0, 0);
        v = new Vector2(0, 0);
        bound = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, position.x, position.y,  drawWidth, drawHeight);
        batch.end();
        if (isKeyDown) {
            position.add(v);
            isKeyDown = false;
        }
        if (v.x>0&&v.y>0)
        if (bound.x > position.x || bound.y > position.y)
        position.add(v);
        if (v.x<0&&v.y<0)
        if (bound.x < position.x || bound.y < position.y)
        position.add(v);
        if (v.x>0&&v.y<0)
        if (bound.x > position.x || bound.y < position.y)
        position.add(v);
        if (v.x<0&&v.y>0)
        if (bound.x < position.x || bound.y > position.y)
        position.add(v);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        screenY = Gdx.graphics.getHeight()-screenY;

        bound.set(screenX, screenY);
        v = bound.cpy().sub(position);
        System.out.println("x "+screenX+" y "+screenY);
        System.out.println("xn "+v.x+" yn "+v.y);
        v.nor();
        System.out.println("X "+v.x+" Y "+v.y);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        isKeyDown = true;
        if (keycode == 19)
            v.set(0, 3);
        if (keycode == 20)
            v.set(0, -3);
        if (keycode == 21)
            v.set(-3, 0);
        if (keycode == 22)
            v.set(3, 0);
        return false;
    }
}
