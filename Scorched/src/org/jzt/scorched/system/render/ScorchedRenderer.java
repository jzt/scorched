package org.jzt.scorched.system.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:40 PM
 */
public class ScorchedRenderer {
  public void start() {
    try {
      Display.setDisplayMode(new DisplayMode(1024, 768));
      Display.create();
    } catch (LWJGLException e) {
      e.printStackTrace();
      System.exit(0);
    }

    // TODO: init OpenGL here
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, 1024, 0, 768, 1, -1);
    glMatrixMode(GL_MODELVIEW);

    // render here
    while (!Display.isCloseRequested()) {
      // clear screen & depth buffer
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      glColor4f(1.0f, 0f, 1.0f, 0.01f);
/*            glBegin(GL_QUADS);
            glVertex2f(100, 100);
            glVertex2f(100 + 200, 100);
            glVertex2f(100 + 200, 100 + 200);
            glVertex2f(100, 100+200);
            glEnd();*/
      Display.update();
    }
    Display.destroy();
  }
}
