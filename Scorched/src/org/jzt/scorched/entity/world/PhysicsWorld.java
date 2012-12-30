package org.jzt.scorched.entity.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jzt.scorched.entity.actor.Ball;
import org.jzt.scorched.entity.actor.Box;
import org.jzt.scorched.renderable.Renderable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Author: jon tucker
 * Date: 12/17/12
 * Time: 12:11 PM
 */

public class PhysicsWorld {

  public World world;

  public float timeStep = 1/60f;  // 60Hz
  private int velocityIterations = 8;
  private int positionIterations = 3;

  public PhysicsWorld() {

    Vec2 gravity = new Vec2(0f, 9.8f);
    boolean doSleep = true;
    world = new World(gravity, doSleep);

    BodyDef groundBodyDef = new BodyDef();
    //groundBodyDef.type = BodyType.STATIC;
    groundBodyDef.position.set(0f, 0f);
    Body staticBody = world.createBody(groundBodyDef);

    PolygonShape groundShape = new PolygonShape();

    FixtureDef groundFixture = new FixtureDef();
    groundFixture.shape = groundShape;

    groundShape.setAsBox(1280/2, 0, new Vec2(0f, -768/2f), 0);
    staticBody.createFixture(groundFixture);
    groundShape.setAsBox(0, 768, new Vec2(-1280/2f, -768/2f), 0);
    staticBody.createFixture(groundFixture);
    groundShape.setAsBox(0, 768, new Vec2(1280/2f, -768/2f), 0);
    staticBody.createFixture(groundFixture);
    groundShape.setAsBox(1280/2, 0, new Vec2(0f, 768/2f), 0);
    staticBody.createFixture(groundFixture);
  }

  public void addBall(List<Renderable> renderables) {
    Random random = new Random();
    //Ball b = new Ball(physicsWorld, 25, -50, 300f);
    Ball b = new Ball(world, random.nextInt(1280) - 1280/2, random.nextInt(768) - 768/2);
    renderables.add(b);
  }

  public void addBox(List<Renderable> renderables) {
    Random random = new Random();
    //Ball b = new Ball(physicsWorld, 25, -50, 300f);
    Box b = new Box(world, random.nextInt(1280) - 1280/2, random.nextInt(768) - 768/2);
    renderables.add(b);
  }

  public void update() {
    world.step(timeStep, velocityIterations, positionIterations);
  }
}
