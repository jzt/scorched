package org.jzt.scorched.renderable.mesh;

import org.jzt.scorched.entity.Entity;
import org.jzt.scorched.renderable.Renderable;
import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 10:55 PM
 */
public abstract class Mesh implements Renderable {
  public Entity owner; // owner of this mesh

  // TODO: define here an array of vertices (or a sprite/texture/bbox/etc) to represent the Entity who owns this mesh

  @Override
  public void render() {
    glPushMatrix();
    glTranslatef(owner.loc.x, owner.loc.y, 0f);
    // TODO: rotate here
    glBegin(GL_POLYGON);
    // TODO: vertices here
    glEnd();
    glPopMatrix();
  }
}
