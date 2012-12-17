package org.jzt.scorched.system.render;

import org.jzt.scorched.renderable.Renderable;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:40 PM
 */
public class ScorchedRenderer {

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 768;
  private List<Renderable> renderables;

  public ScorchedRenderer(List<Renderable> r) {
    renderables = r;
  }

  public void start() {
    try {
      Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
      Display.create();
    } catch (LWJGLException e) {
      e.printStackTrace();
      System.exit(0);
    }

    // TODO: init OpenGL here
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, WIDTH, 0, HEIGHT, 0, 1);
    glMatrixMode(GL_MODELVIEW);

    // render here
    while (!Display.isCloseRequested()) {
      // clear screen & depth buffer
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      for (Renderable r : renderables) {
        r.render();
      }

      Display.update();
    }
    Display.destroy();
  }
}
