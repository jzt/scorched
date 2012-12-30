package org.jzt.scorched;

import org.jzt.scorched.audio.AudioHandler;
import org.jzt.scorched.entity.world.PhysicsWorld;
import org.jzt.scorched.renderable.Renderable;
import org.jzt.scorched.util.Input;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:25 PM
 */

public class Game {

  public static int width = 1280;
  public static int height = 768;
  public static PhysicsWorld physicsWorld;
  private static Thread thread;
  public static List<Renderable> renderableList; // TODO: refactor all rendering out into ScorchedRenderer
  private static Input input;
  public static boolean running;
  public static IntBuffer source = BufferUtils.createIntBuffer(1);
  public static FloatBuffer sourcePos;
  public static IntBuffer buffer = BufferUtils.createIntBuffer(1);

  public static AudioHandler audioHandler;

  public static void main(String[] args) {
    try {
      Display.setDisplayMode(new DisplayMode(width, height));
      Display.setTitle("Charred Terra");
      Display.create();
      AL.create();
      audioHandler = AudioHandler.getInstance();

      reset();

      thread = new Thread(update);
      thread.start();

      glMatrixMode(GL_PROJECTION);
      glLoadIdentity();
      GLU.gluOrtho2D(-1280 / 2f, 1280 / 2f, -768 / 2f, 768 / 2f);
      glMatrixMode(GL_MODELVIEW);

      run();

      thread.interrupt();
      thread = null;

      audioHandler.killALData();
      AL.destroy();
      Display.destroy();

    } catch (LWJGLException e) {
      e.printStackTrace();
    }
  }

  private static Runnable update = new Runnable() {
    long timer = 0;
    int step = 60/1000;
    @Override
    public void run() {
      while (!Thread.interrupted()) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - timer > step) {
          getWorld().update();
          timer = currentTime;
        }
      }
    }
  };

  private static PhysicsWorld getWorld() {
    return physicsWorld;
  }

  private static void run() {
    running = true;

    while (running) {
      // clear screen & depth buffer
      glClear(GL_COLOR_BUFFER_BIT);
      glClearColor(0f, 0f, 0f, 1f);
      //physicsWorld.update();
      for (Renderable r : renderableList) {
        r.render();
      }
      input.poll();
      Display.update();
      if (Display.isCloseRequested()) {
        running = false;
      }
    }
  }

  public static void reset() {
    if (input == null) {
      input = new Input();
    }

    renderableList = new LinkedList<Renderable>();
    physicsWorld = new PhysicsWorld();

    running = true;
    physicsWorld.addBall(renderableList);
    physicsWorld.addBox(renderableList);
/*    for (int i = 0; i < 50; ++i) {
      if (i % 2 == 0)
        physicsWorld.addBox(renderableList);
      else
        physicsWorld.addBall(renderableList);
    }*/
  }

}
