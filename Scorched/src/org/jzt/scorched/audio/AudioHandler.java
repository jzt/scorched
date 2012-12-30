package org.jzt.scorched.audio;

/*08:26PM <KittyCat> hi
    08:27PM <tnzr> hi KittyCat... would you happen to have any idea as to why a sound from a source is either absolutely left/right/center but no in-between?
    08:28PM <tnzr> i was thinking maybe it has to do with the upper/lower bounds of the values (I evenutally started setting the x/y to between 0 and 1)
    08:28PM <tnzr> but now, like if a ball is the source, and it rolls left of center, its all the way left, right of center is all the way right
    08:29PM <tnzr> but there's no difference between say, all the way left and maybe just left of center
    08:30PM <KittyCat> because most implementations normalize the distance when figuring out the 3D panning. distance attenuation is typically used to tell the difference
    between something that's just to the left compared to something that's far too the left
    08:32PM <KittyCat> openal-soft does use smooth panning when inside the source's reference distance, as long as HRTF isn't being used. but practically, if you want
    smooth 2D-like panning you'll need to think in a circle around the listener rather than going straight through it
    08:33PM <KittyCat> so like, {0,0,-1} is center panned, {-1,0,0} is left-panned, and {+1,0,0} is right-panned
    08:33PM <tnzr> ahhh ok
    08:34PM <tnzr> interesting :)
    08:34PM <KittyCat> the easiest way to calculate it is like this:
    08:35PM <KittyCat> given -1<=pan<=+1, use the vector: { pan, 0, -sqrt(1.0 - pan*pan) }
    08:36PM <KittyCat> or if you'd rather center be more all-encompassing rather than straight in front: { pan,  sqrt(1.0 - pan*pan), 0 }
*/

import org.jbox2d.common.Vec2;
import org.jzt.scorched.Game;
import org.jzt.scorched.util.ScorchedUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;

/**
 * Author: jon tucker
 * Date: 12/29/12
 * Time: 3:56 PM
 */
public class AudioHandler {
  public static final int NUM_BUFFERS = 2;  // how many sound buffers we will need
  public static final int NUM_SOURCES = 32;  // how many sound sources there can be
  public static int totalSources = 0;

/*  // sound indices into soundbuffer
  public static final int ENERGIZE = 0;
  public static final int THRUST = 1;*/

  public static IntBuffer sounds;
  public static IntBuffer sources;

  public static FloatBuffer sourcePos;
  public static FloatBuffer sourceVel;
  public static FloatBuffer listenerPos;
  public static FloatBuffer listenerVel;
  public static FloatBuffer listenerOri;

  public static AudioHandler instance;

  private AudioHandler() {
    sounds = BufferUtils.createIntBuffer(NUM_BUFFERS);
    sources =  BufferUtils.createIntBuffer(NUM_SOURCES);
    sourcePos =   BufferUtils.createFloatBuffer(3*NUM_SOURCES);
    sourceVel =   BufferUtils.createFloatBuffer(3*NUM_SOURCES);
    listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] {0f, 0f, 0f});
    listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] {0f, 0f, 0f});
    listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] {0f, 0f, -1f, 0f, 1f, 0f});

    listenerOri.flip();
    listenerPos.flip();
    listenerVel.flip();

    if (loadALData() == AL_FALSE) {
      System.out.println("Error loading audio data.");
      System.exit(-1);
    }
    setListenerValues();
  }

  public static AudioHandler getInstance() {
    if (instance == null) {
      instance = new AudioHandler();
    }
    return instance;
  }

  private int loadALData() {
    alGenBuffers(sounds);

    if (alGetError() != AL_NO_ERROR) {
      return AL_FALSE;
    }

    // repeat next 3 lines for each sound
    try {
      WaveData wave = WaveData.create(new BufferedInputStream(new FileInputStream("res/audio/joust_energize.wav")));
      alBufferData(sounds.get(AudioSamples.ENERGIZE), wave.format, wave.data, wave.samplerate);
      wave.dispose();

      wave = WaveData.create(new BufferedInputStream(new FileInputStream("res/audio/asteroids_thrust.wav")));
      alBufferData(sounds.get(AudioSamples.THRUST), wave.format, wave.data, wave.samplerate);
      wave.dispose();

      alGenSources(sources);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    if (alGetError() == AL_NO_ERROR) {
      System.out.println("alGetError() == AL_NO_ERROR");
      return AL_TRUE;
    }

    return AL_FALSE;
  }

  public int addSource(int id, boolean looping) {
    int position = sources.position();
    sources.limit(position + 1);
    alGenSources(sources);

    if (alGetError() != AL_NO_ERROR) {
      System.out.println("Error generating audio source.");
      System.exit(-1);
    }

    System.out.println(id);
    alSourcei(sources.get(position), AL_BUFFER, sounds.get(id));
    alSourcef(sources.get(position), AL_PITCH, 1.0f);
    alSourcef(sources.get(position), AL_GAIN, 1.0f);
    alSource(sources.get(position), AL_POSITION, (FloatBuffer) sourcePos.position(id*3));
    alSource(sources.get(position), AL_VELOCITY, (FloatBuffer) sourcePos.position(id*3));
/*    alSource(sources.get(position), AL_POSITION, sourcePos);
    alSource(sources.get(position), AL_VELOCITY, sourcePos);*/
    alSourcei(sources.get(position), AL_LOOPING, ((looping) ? AL_TRUE : AL_FALSE));

    if (looping)
      alSourcePlay(sources.get(position));

    sources.position(position + 1);

    return position;
  }

  public void setListenerValues() {
    alListener(AL_POSITION, listenerPos);
    alListener(AL_VELOCITY, listenerVel);
    alListener(AL_ORIENTATION, listenerOri);
  }

  public void killALData() {
    System.out.println("AudioHandler.killALData()");
    int position = sources.position();
    sources.position(0).limit(position);
    alDeleteSources(sources);
    alDeleteBuffers(sounds);
  }

  public void play(int id, Vec2 position) {
    float xpan = ScorchedUtils.map(position.x, -Game.width/2, Game.width/2, -1f, 1f);
    float ypan = ScorchedUtils.map(position.y, -Game.height/2, Game.height/2, -1f, 1f);
    sourcePos.put(id * 3 + 0, xpan);
    //sourcePos.put(id*3+1, ypan);    // TODO: test this to make sure it sounds best
    sourcePos.put(id*3+1, 0f);
    //sourcePos.put(id*3+2, 0f);
    //sourcePos.put(id * 3 + 1, (float) Math.sqrt(1.0 - xpan*xpan));
    sourcePos.put(id * 3 + 2, (float) -Math.sqrt(1.0 - xpan*xpan));

/*    sourceVel.put(id * 3 + 0, velocity.x);
    sourceVel.put(id * 3 + 1, velocity.y);
    sourceVel.put(id * 3 + 2, 0f);*/

    alSource(sources.get(id), AL_POSITION, (FloatBuffer) sourcePos.position(id * 3));
    alSource(sources.get(id), AL_VELOCITY, (FloatBuffer) sourceVel.position(id * 3));

    alSourcePlay(sources.get(id));
  }
}
