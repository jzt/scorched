package org.jzt.scorched.util;

import org.jbox2d.dynamics.joints.MouseJoint;
import org.jzt.scorched.Game;
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
      alSourcei(Game.source.get(0), AL_POSITION, Game.buffer.get(0));
      System.out.println("Mouse pressed: (" + x + ", " + y + ")");

    }

    /**
     * KB shit
     */
    while (Keyboard.next()) {
      if (Keyboard.getEventKeyState()) {  // key(s) pressed
        switch (Keyboard.getEventKey()) {

          case Keyboard.KEY_ESCAPE:
            Game.running = false;
            break;
          case Keyboard.KEY_SPACE:
            //Game.reset();
            alSourcePlay(Game.source);
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
