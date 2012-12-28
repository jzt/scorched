package org.jzt.scorched.entity;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:46 PM
 */

import org.lwjgl.util.vector.Vector2f;

/**
 * Abstract Entity base class for all non-renderable actors in the game.
 */
public abstract class Entity {

  public Vector2f loc;
  public Vector2f vel;
  public Vector2f acc;

  public abstract void update();
  public abstract void die();
}
