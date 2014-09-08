package za.co.jethromuller.collisiondetection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import java.util.BitSet;

public class Player extends Entity {

    private int speed;

    public Player(CollisionDetectionDemo game, int x, int y, String fileName) {
        super(game, x, y, fileName);
        speed = 2;
    }

    @Override
    public void update() {
        float deltaX = 0;
        float deltaY = 0;

        if(Gdx.input.isKeyPressed(Keys.UP)) {
            deltaY = speed;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            deltaY = -speed;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            deltaX = -speed;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            deltaX = speed;
        }

        collisionDetection(getX() + deltaX, getY());
        collisionDetection(getX(), getY() + deltaY);
    }

    public boolean worldBorderCollisionX(float newX) {
        int tileSize = game.tileSize;

        if (newX < tileSize) {
            setX(tileSize);
            return true;
        } else if (newX > tileSize * (game.map[0].length - 2) - 5) {
            setX(tileSize * (game.map[0].length - 2) - 5);
            return true;
        }
        return false;
    }

    public boolean worldBorderCollisionY(float newY) {
        int tileSize = game.tileSize;
        if (newY < tileSize) {
            setY(tileSize);
            return true;
        } else if (newY > tileSize * (game.map.length - 2) - 5) {
            setY(tileSize * (game.map.length - 2) - 5);
            return true;
        }
        return false;
    }

    private void collisionDetection(float newX, float newY) {
        boolean collision = false;
        if (worldBorderCollisionX(newX)) {
            return;
        }
        if (worldBorderCollisionY(newY)) {
            return;
        }

        for (Entity entity : game.getEntities(this)) {
            if (entity.equals(this)) {
                continue;
            }

            if (this.getBoundingRectangle().overlaps(entity.getBoundingRectangle())) {
                int x_start = (int) Math.max(newX, entity.getX());
                int y_start = ((int) Math.max(newY, entity.getY()));

                int x_end = ((int) Math.min(newX + this.getWidth(),
                                            entity.getX() + entity.getWidth()));
                int y_end = ((int) Math.min(newY + this.getHeight(),
                                            entity.getY() + entity.getHeight()));


                for (int y = 1; y < Math.abs(y_end - y_start); y++) {
                    int y_test1 = Math.abs(((int) (y_start - newY))) + y;
                    int y_test2 = Math.abs(y_start - (int) entity.getY()) + y;
                    int x_test1 = Math.abs(((int) (x_start - newX)));
                    int x_test2 = Math.abs(((int) (x_start - entity.getX())));
                    BitSet overlayEntity = entity.bitSet[y_test2].get(x_test2,
                                                                 x_test2 + 1 + Math.abs(x_end -
                                                                                     x_start));
                    BitSet overlayPlayer = bitSet[y_test1].get(x_test1,
                                                               x_test1 + Math.abs(x_end - x_start));
                    overlayPlayer.and(overlayEntity);
                    if (overlayPlayer.cardinality() != 0) {
                        collision = true;
                        break;
                    }
                }
            }
        }

        if (!collision) {
            setPosition(newX, newY);
        }
    }
}
