package org.jzt.scorched.entity.actor;

import org.jzt.scorched.entity.Entity;
import org.lwjgl.util.vector.Vector2f;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 10:51 PM
 */
public class Tank extends Entity {
  public Tank() {
    loc = new Vector2f(300, 300);
  }

  @Override
  public void update() {
  }

  @Override
  public void die() {
  }
}
