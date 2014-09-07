package za.co.jethromuller.collisiondetection;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Jethro Muller on 2014/09/06.
 */
public class Entity extends Sprite {

    CollisionDetectionDemo game;
    protected FileHandle current_file;

    public Entity(CollisionDetectionDemo game, int x, int y, String fileName) {
        super(new Texture(fileName), x, y);
        Texture entityTexture = new Texture(fileName);
        setBounds(x, y, entityTexture.getWidth(), entityTexture.getHeight());
        this.game = game;
        current_file = new FileHandle(fileName);
    }

    public void update(float timeDelta) {}
}
