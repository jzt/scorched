package org.jzt.scorched.renderable.mesh;

import org.jzt.scorched.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 10:57 PM
 */
public class TankMesh extends Mesh {
  public TankMesh(Entity o) {
    owner = o;
  }
}
