package org.jzt.scorched.util;

/**
 * Author: jon tucker
 * Date: 12/29/12
 * Time: 1:24 PM
 */
public class ScorchedUtils {

  public static float map(float value, float start1, float stop1, float start2, float stop2) {
    return start2 + (stop2 - start2)*((value - start1) / (stop1 - start1));
  }
}
