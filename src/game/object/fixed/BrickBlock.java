package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

public class BrickBlock extends Block {

  public BrickBlock(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;
    isDestory = true;
  }

  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(139, 69, 19) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(60, 60, 60) );
    g.drawRect( positionX, positionY, width, height );
    g.setColor( new Color(96, 96, 96) );
    g.drawLine( positionX + width / 2,
                positionY,
                positionX + width / 2,
                positionY + height);
    g.drawLine( positionX,
                positionY + height / 2,
                positionX + width,
                positionY + height / 2);
  }

}
