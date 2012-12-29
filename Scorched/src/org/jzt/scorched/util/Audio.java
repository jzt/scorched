package org.jzt.scorched.util;

import org.lwjgl.util.WaveData;
import static org.lwjgl.openal.AL10.*;

import java.util.List;

/**
 * Author: jon tucker
 * Date: 12/28/12
 * Time: 8:11 PM
 */
public class Audio {
  public List<WaveData> sounds;

  public void load(String soundPath) {
    sounds.add(WaveData.create(soundPath));
  }
}
