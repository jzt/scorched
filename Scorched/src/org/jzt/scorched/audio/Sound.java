package org.jzt.scorched.audio;

import org.jbox2d.dynamics.Body;

/**
 * Author: jon tucker
 * Date: 12/29/12
 * Time: 3:38 PM
 */
public class Sound {
  private Body body;
  private int id;
  private int sourcePosition;

  public Sound(Body b, int sound, int i, boolean looping) {
    body = b;
    id = i;
    sourcePosition = AudioHandler.getInstance().addSource(sound, looping);
  }

  public void play() {
    AudioHandler.getInstance().play(sourcePosition, body.getPosition());
  }
}
