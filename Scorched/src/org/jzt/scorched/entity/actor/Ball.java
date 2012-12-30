package org.jzt.scorched.entity.actor;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jzt.scorched.audio.AudioHandler;
import org.jzt.scorched.audio.AudioSamples;
import org.jzt.scorched.audio.Sound;
import org.jzt.scorched.renderable.Renderable;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Author: jon tucker
 * Date: 12/21/12
 * Time: 5:09 PM
 */
public class Ball implements Renderable {

  public World m_world;
  public Body m_body;
  public float m_radius;
  public Vec2 loc;

  private float R;
  private float G;
  private float B;

  private Random random;

  public Sound sound;

  public Ball(World world, float x, float y) {

    random = new Random();

    R = random.nextFloat();
    G = random.nextFloat();
    B = random.nextFloat();

    loc = new Vec2(x, y);
    m_world = world;
    m_body = null;
    m_radius = random.nextFloat() * 20f + 20f;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DYNAMIC;
    bodyDef.position.set(loc.x, loc.y);
    bodyDef.angle = (float) (random.nextFloat() * Math.PI * 2);
    m_body = m_world.createBody(bodyDef);

    CircleShape circleShape = new CircleShape();
    circleShape.m_p.set(0f, 0f);
    circleShape.m_radius = m_radius;
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circleShape;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.9f;
    fixtureDef.restitution = 0.4f;
    m_body.createFixture(fixtureDef);

    //sound = new Sound(m_body, AudioSamples.ENERGIZE, AudioHandler.totalSources++, false);
  }

  public void render() {
    Vec2 pos = m_body.getPosition();
    float angle = m_body.getAngle();
    Vec2 vel = m_body.getLinearVelocity();

    glPushMatrix();
    glTranslatef(pos.x, pos.y, 0);
    glRotated(angle*(180/Math.PI), 0, 0, 1);

    glLineWidth(.5f);
    glColor4f(R, G, B, 1f);
    glBegin(GL_POLYGON);
    for (float a = 0; a < Math.PI*2; a += Math.PI/32) {
      glVertex2d(m_radius*Math.sin(a), m_radius*Math.cos(a));
    }
    glEnd();

    glColor4f(1.0f, 1.0f, 1.0f, 1f);
    glBegin(GL_LINE_LOOP);
    for (float a = 0; a < Math.PI*2; a += Math.PI/32) {
      glVertex2d(m_radius*Math.sin(a), m_radius*Math.cos(a));
    }
    glEnd();
    glBegin(GL_LINES);
    glVertex2d(0f, 0f);
    glVertex2d(m_radius, 0f);
    glEnd();
    glPopMatrix();
  }
}
