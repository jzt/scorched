package org.jzt.scorched.renderable.hud;

import org.jzt.scorched.Game;
import org.jzt.scorched.renderable.Renderable;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.InputStream;

/**
 * Author: jon tucker
 * Date: 12/30/12
 * Time: 1:52 PM
 */
public class Hud implements Renderable {

  private UnicodeFont font;

  public Hud() {
    init();
  }

  private void init() {
    try {
      InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/MONACO.TTF");
      Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
      awtFont = awtFont.deriveFont(Font.ITALIC, 12f);
      font = new UnicodeFont(awtFont);
      font.getEffects().add(new ColorEffect(java.awt.Color.green));
      font.addAsciiGlyphs();
      font.loadGlyphs();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void render() {
    font.drawString(-Game.width / 2 + 50, -Game.height / 2 + 50, "Testing!", Color.green);
  }
}
