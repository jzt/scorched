package org.jzt.scorched;

import org.jzt.scorched.entity.world.PhysicsWorld;
import org.jzt.scorched.renderable.Renderable;
import org.jzt.scorched.util.Input;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:25 PM
 */

public class Game {

  private static int width = 1280;
  private static int height = 768;
  public static PhysicsWorld physicsWorld;
  private static Thread thread;
  public static List<Renderable> renderableList; // TODO: refactor all rendering out into ScorchedRenderer
  private static Input input;
  public static boolean running;

  public static void main(String[] args) {
    try {
      Display.setDisplayMode(new DisplayMode(width, height));
      Display.create();

      reset();

      thread = new Thread(update);
      //thread.start();

    } catch (LWJGLException e) {
      e.printStackTrace();
    }
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    GLU.gluOrtho2D(-1280 / 2f, 1280 / 2f, -768 / 2f, 768 / 2f);
    glMatrixMode(GL_MODELVIEW);

    run();

    thread.interrupt();
    thread = null;
    Display.destroy();
  }

  private static Runnable update = new Runnable() {
    @Override
    public void run() {
      while (!Thread.interrupted()) {
        physicsWorld.update();
      }
    }
  };

  private static void run() {
    running = true;

    while (running) {
      // clear screen & depth buffer
      glClear(GL_COLOR_BUFFER_BIT);
      glClearColor(0f, 0f, 0f, 1f);
      physicsWorld.update();
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

    physicsWorld = null;
    renderableList = null;

    renderableList = new LinkedList<Renderable>();
    physicsWorld = new PhysicsWorld();

    running = true;
    for (int i = 0; i < 200; ++i) {
      if (i % 2 == 0)
        physicsWorld.addBox(renderableList, i);
      else
        physicsWorld.addBall(renderableList, i);
    }
  }

}
