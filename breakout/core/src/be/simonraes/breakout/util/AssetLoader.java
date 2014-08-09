package be.simonraes.breakout.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Simon Raes on 9/08/2014.
 */
public class AssetLoader {

    private static Texture texture;
    public static TextureRegion rail, ball;

    public static void load(){
        texture = new Texture(Gdx.files.internal("data/breakout_texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        //texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        rail = new TextureRegion(texture, 0,0,3,4);
        ball = new TextureRegion(texture, 4,0,27,24);
    }

    public static void dispose(){
        texture.dispose();
    }
}
