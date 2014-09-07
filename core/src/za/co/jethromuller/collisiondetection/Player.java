package za.co.jethromuller.collisiondetection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.BitSet;

public class Player extends Entity {

    private int speed;

    public Player(CollisionDetectionDemo game, int x, int y, String fileName) {
        super(game, x, y, fileName);
        speed = 80;
    }

    public void update(float delta) {
        float deltaX = 0;
        float deltaY = 0;

        if(Gdx.input.isKeyPressed(Keys.UP)) {
            deltaY = speed * delta;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            deltaY = -speed * delta;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            deltaX = -speed * delta;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            deltaX = speed * delta;
        }

        collisionDetection(deltaX, true);
        collisionDetection(deltaY, false);
    }

    private void collisionDetection(float delta, boolean xAxis) {
        boolean collision = false;
        for (Entity entity : game.getEntities()) {
            if (entity.equals(this)) {
                continue;
            }

            if (this.getBoundingRectangle().overlaps(entity.getBoundingRectangle())) {
                BitSet[] playerMask = getBitMask(new Pixmap(this.current_file));
                BitSet[] entityMask = getBitMask(new Pixmap(entity.current_file));

                int x_start = xAxis ? ((int) Math.max(this.getX() + delta, entity.getX())) :
                              ((int) Math.max(this.getX(), entity.getX()));
                int y_start = !xAxis ? ((int) Math.max(this.getY() + delta, entity.getY())) :
                              ((int) Math.max(this.getY(), entity.getY()));

                int x_end = xAxis ? ((int) Math.min(this.getX() + delta + this.getWidth(),
                                            entity.getX() + entity.getWidth())) :
                            ((int) Math.min(this.getX() + this.getWidth(),
                                            entity.getX() + entity.getWidth()));
                int y_end = !xAxis ? ((int) Math.min(this.getY() + delta + this.getHeight(),
                                            entity.getY() + entity.getHeight())) :
                            ((int) Math.min(this.getY() + this.getHeight(),
                                            entity.getY() + entity.getHeight()));


                for (int y = 1; y < (y_end - y_start) + 1; y++) {
                    for (int x = 0; x < (x_end - x_start) + 1; x++) {
                        if (playerMask[y].get(x) && entityMask[y].get(x)) {
                            System.out.println(x + " " + y);
                            collision = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!collision) {
            if (xAxis) {
                this.translateX(delta);
            } else {
                this.translateY(delta);
            }
        }
    }

    private BitSet[] getBitMask(Pixmap pixmap) {
        BitSet[] bitmask = new BitSet[pixmap.getWidth()];
        for (int i = 0; i < bitmask.length; i++) {
            bitmask[i] = new BitSet(pixmap.getWidth());
        }
        for (int y = 0; y < pixmap.getHeight(); y++) {
            for (int x = 0; x < pixmap.getWidth(); x++) {
                if ((pixmap.getPixel(x, y) & 0x000000ff) != 0x00) {
                    bitmask[pixmap.getHeight() - y - 1].set(x);
                }
            }
        }
        return bitmask;
    }
}
