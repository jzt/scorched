package org.jzt.scorched.util;

import org.jzt.scorched.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import sun.plugin2.message.GetAppletMessage;

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
            Game.reset();
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
