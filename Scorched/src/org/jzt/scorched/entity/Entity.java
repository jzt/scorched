package org.jzt.scorched.entity;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:46 PM
 */

/**
 * Abstract Entity base class for all non-renderable actors in the game.
 */
public abstract class Entity {
  public abstract void update();
  public abstract void die();
}
