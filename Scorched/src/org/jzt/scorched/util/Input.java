package org.jzt.scorched.util;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jzt.scorched.Game;
import org.jzt.scorched.audio.AudioHandler;
import org.jzt.scorched.audio.AudioSamples;
import org.jzt.scorched.entity.actor.Ball;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

/**
 * Author: jon tucker
 * Date: 12/28/12
 * Time: 2:58 PM
 */
public class Input {

  public void poll() {
    /**
     * Mouse shit
     */
    if (Mouse.isButtonDown(0)) {
      int x = Mouse.getX();
      int y = Mouse.getY();
      System.out.println("Click at (" + x + ", " + y + ")");
    }

    /**
     * KB shit
     */

    if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
      Ball b = (Ball) Game.renderableList.get(0);
      b.m_body.setAngularVelocity(-5f);
    }

    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
      Ball b = (Ball) Game.renderableList.get(0);
      b.m_body.setAngularVelocity(5f);
    }

    if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
      Ball b = (Ball) Game.renderableList.get(0);
      b.m_body.setAngularVelocity(0f);
    }

    while (Keyboard.next()) {
      if (Keyboard.getEventKeyState()) {  // key(s) pressed
        Ball b;
        switch (Keyboard.getEventKey()) {
          case Keyboard.KEY_ESCAPE:
            Game.running = false;
            break;
          case Keyboard.KEY_R:
            Game.reset();
            break;
          case Keyboard.KEY_W:
            b = (Ball) Game.renderableList.get(0);
            b.m_body.applyLinearImpulse(new Vec2().set(0f, -100000f), b.m_body.getPosition());
            break;
/*          case Keyboard.KEY_S:
            b = (Ball) Game.renderableList.get(0);
            b.m_body.setAngularVelocity(0f);
            break;*/
/*          case Keyboard.KEY_LEFT:
            b = (Ball) Game.renderableList.get(0);
            b.m_body.applyAngularImpulse(100000f);
            break;*/
/*          case Keyboard.KEY_RIGHT:
            b = (Ball) Game.renderableList.get(0);
            b.m_body.applyAngularImpulse(-100000f);
            //b.m_body.applyLinearImpulse(new Vec2().set(100000f, 0f), b.m_body.getPosition());
            break;*/
          case Keyboard.KEY_SPACE:
            ((Ball) Game.renderableList.get(0)).sound.play();
            break;
          default:
            break;
        }
      }
      else {  // key(s) released

      }
    }
  }
}
