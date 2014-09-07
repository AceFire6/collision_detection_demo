package za.co.jethromuller.collisiondetection;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CollisionDetectionDemo extends ApplicationAdapter {
	private SpriteBatch batch;
    private Player player;

    private int[][] map = {
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
    private int tileSize = 20;
    private int mapWidth = map[0].length * tileSize;
    private int mapHeight = map.length * tileSize;

    private Texture obstacleTexture;
    private Texture groundTexture;

    private ArrayList<Entity> entities;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        player = new Player(this, 40, 60, "player.png");

        obstacleTexture = new Texture("block.png");
        groundTexture = new Texture("ground.jpg");

        entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(new Entity(this, 50, 150, "enemy.png"));
        entities.add(new Entity(this, 200, 200, "enemy.png"));
        entities.add(new Entity(this, 180, 50, "enemy.png"));
	}

	@Override
	public void render () {
        //Drawing
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        drawMap();
        drawEntities();
        batch.end();
	}

    public void drawEntities() {
        float timeDelta = Gdx.graphics.getDeltaTime();
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                entity.update(timeDelta);
            }
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
