/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:25 PM
 */

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {
  public Main() {
    try {
      Display.setDisplayMode(new DisplayMode(1024, 768));
    } catch (LWJGLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new Main();
  }
}
