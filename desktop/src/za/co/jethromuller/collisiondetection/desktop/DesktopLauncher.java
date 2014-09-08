package za.co.jethromuller.collisiondetection.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import za.co.jethromuller.collisiondetection.CollisionDetectionDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.title = "Pixel Perfect Collision Detection Demo - Assignment 3";
		new LwjglApplication(new CollisionDetectionDemo(), config);
	}
}
