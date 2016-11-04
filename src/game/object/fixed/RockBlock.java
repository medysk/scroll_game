package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

public class RockBlock extends Block {

  public RockBlock(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;
    isDestory = false;
  }

  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(128, 128, 128) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(32, 32, 32) );
    g.drawRect( positionX, positionY, width, height );
  }
}
