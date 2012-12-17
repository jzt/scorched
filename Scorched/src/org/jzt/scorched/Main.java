package org.jzt.scorched;

import org.jzt.scorched.entity.world.Terrain;
import org.jzt.scorched.renderable.Renderable;
import org.jzt.scorched.system.render.ScorchedRenderer;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:25 PM
 */

public class Main {


  public static void main(String[] args) {

    List<Renderable> renderables = new LinkedList<Renderable>();
    ScorchedRenderer sr = new ScorchedRenderer(renderables);

    renderables.add(new Terrain(sr.WIDTH, sr.HEIGHT));

    sr.start();
  }

}
