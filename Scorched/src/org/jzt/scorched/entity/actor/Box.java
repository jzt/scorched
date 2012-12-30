package org.jzt.scorched.entity.actor;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jzt.scorched.renderable.Renderable;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/21/12
 * Time: 5:09 PM
 */
public class Box implements Renderable {
  public World m_world;
  public Body m_body;
  public Vec2 loc;
  public float m_width;
  public float m_height;

  private float R;
  private float G;
  private float B;

  private Random random;

  public Box(World world, float x, float y) {

    random = new Random();

    R = random.nextFloat();
    G = random.nextFloat();
    B = random.nextFloat();

    loc = new Vec2(x, y);
    m_world = world;
    m_body = null;
    m_width = random.nextFloat()*20f + 20f;
    m_height = random.nextFloat()*20f + 20f;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DYNAMIC;
    bodyDef.position.set(loc.x, loc.y);
    bodyDef.angle = (float) (random.nextFloat()*Math.PI*2);
    m_body = m_world.createBody(bodyDef);

    PolygonShape boxShape = new PolygonShape();
    boxShape.setAsBox(m_width/2, m_height/2);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = boxShape;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.8f;
    fixtureDef.restitution = .3f;
    m_body.createFixture(fixtureDef);
  }

  public void render() {
    Vec2 pos = m_body.getPosition();
    float angle = m_body.getAngle();
    Vec2 vel = m_body.getLinearVelocity();

    glPushMatrix();
    glTranslatef(pos.x, pos.y, 0);
    glRotated(angle*(180/Math.PI), 0, 0, 1);
    //glColor4f(R, G, B, Math.min(1, vel.length()/20));
    glColor4f(R, G, B, 1f);
    glBegin(GL_TRIANGLES);
    glVertex2d(m_width/2, -m_height/2);
    glVertex2d(m_width/2, m_height/2);
    glVertex2d(-m_width/2, m_height/2);
    glVertex2d(m_width/2, -m_height/2);
    glVertex2d(-m_width/2, -m_height/2);
    glVertex2d(-m_width/2, m_height/2);
    glEnd();
    glPopMatrix();
  }
}

