package za.co.jethromuller.collisiondetection;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.BitSet;

public class Entity extends Sprite {

    CollisionDetectionDemo game;
    protected FileHandle current_file;
    protected BitSet[] bitSet;

    public Entity(CollisionDetectionDemo game, int x, int y, String fileName) {
        super(new Texture(fileName), x, y);
        Texture entityTexture = new Texture(fileName);
        setBounds(x, y, entityTexture.getWidth(), entityTexture.getHeight());
        this.game = game;
        current_file = new FileHandle(fileName);
        bitSet = getBitMask(new Pixmap(current_file));
        printBitmask();
    }

    private void printBitmask() {
        System.out.println(current_file.name());
        for (BitSet set : bitSet) {
            for (int i = 0; i < getTexture().getWidth(); i++) {
                if (set.get(i)) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }

    public void update() {
        //Normal entities don't move
    }

    public BitSet[] getBitMask(Pixmap pixmap) {
        BitSet[] bitmask = new BitSet[pixmap.getHeight()];
        for (int i = 0; i < bitmask.length; i++) {
            bitmask[i] = new BitSet(pixmap.getWidth());
        }
        for (int y = 0; y < pixmap.getHeight(); y++) {
            for (int x = 0; x < pixmap.getWidth(); x++) {
                if ((pixmap.getPixel(x, y) & 0x000000ff) != 0x00) {
                    bitmask[y].set(x);
                }
            }
        }
        return bitmask;
    }
}
