package org.jzt.scorched.entity.world;

import org.jzt.scorched.entity.Entity;
import org.jzt.scorched.renderable.Renderable;
import org.jzt.scorched.util.Perlin;
import org.lwjgl.opengl.GL11;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:46 PM
 */
public class Terrain implements Renderable {

  private int width;
  private int height;
  private int delta;
  private float[] terrain;

  public Terrain(int w, int h) {
    width = w;
    height = h;
    delta = 32;
    terrain = Perlin.perlin(w, h, delta, .025, 16);
  }

  @Override
  public void render() {
    GL11.glColor3f(0.2f, .8f, .25f);
    GL11.glLineWidth(4f);

    GL11.glColor3f(1f, 0f, .25f);
    GL11.glBegin(GL11.GL_LINE_LOOP);
    for (int i = 0; i < terrain.length; ++i) {
      GL11.glVertex2f(i*delta, height/4 + 24*terrain[i]);
    }
    GL11.glVertex2f(width, height/4);
    GL11.glVertex2f(width, 0);
    GL11.glVertex2f(0, 0);
    GL11.glEnd();
  }

}
