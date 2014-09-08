package za.co.jethromuller.collisiondetection;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class CollisionDetectionDemo extends ApplicationAdapter {
	private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;
    protected int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };
    protected int tileSize = 20;
    protected int mapWidth = map[0].length * tileSize;
    protected int mapHeight = map.length * tileSize;

    private Texture obstacleTexture;
    private Texture groundTexture;

    private ArrayList<Entity> entities;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(mapWidth, mapHeight, camera);
        viewport.apply(true);

        Player player = new Player(this, 40, 60, "player.png");

        obstacleTexture = new Texture("block.png");
        groundTexture = new Texture("ground.jpg");

        entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Entity(this, 50, 150, "enemy.png"));
        entities.add(new Entity(this, 200, 200, "enemy.png"));
        entities.add(new Entity(this, 180, 50, "enemy.png"));
        getInnerObstacles();
	}

	@Override
	public void render () {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        //Drawing
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        drawMap();
        drawEntities();
        batch.end();
	}

    private void getInnerObstacles() {
        int x_pos;
        int y_pos = mapHeight;
        for (int[] ints : map) {
            x_pos = mapWidth;
            y_pos -= tileSize;
            for (int i = 0; i < ints.length; i++) {
                x_pos -= tileSize;
                if (ints[i] == 1) {
                    if ((x_pos < mapWidth - tileSize) && (x_pos > tileSize) &&
                        (y_pos < (mapHeight - (2 * tileSize))) && (y_pos > (2 * tileSize))) {
                        entities.add(new Entity(this, x_pos, y_pos, "block.png"));
                        ints[i] = 0;
                    }
                }
            }
        }
    }

    public void drawEntities() {
        for (Entity entity : entities) {
            entity.update();
            batch.draw(entity.getTexture(), entity.getX(), entity.getY());
        }
    }

    public void drawMap() {
        int x_pos;
        int y_pos = mapHeight;
        for (int[] ints : map) {
            x_pos = mapWidth;
            y_pos -= tileSize;
            for (int anInt :ints) {
                x_pos -= tileSize;
                if (anInt == 1) {
                    batch.draw(obstacleTexture, x_pos, y_pos);
                } else {
                    batch.draw(groundTexture, x_pos, y_pos);
                }
            }
        }
    }

    public ArrayList<Entity> getEntities() {
        return ((ArrayList<Entity>) entities.clone());
    }
}
