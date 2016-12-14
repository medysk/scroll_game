package game.object.fixed;

import java.awt.Graphics;

/**
 * @author medysk
 * 平地オブジェクト
 */
public class Flat extends Ground {

  public Flat( int positionX, int positionY ) {
    super( positionX, positionY );
  }


  /* (非 Javadoc)
   * @see game.object.fixed.Ground#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    super.draw(g);
    g.fillRect( positionX, positionY, width, height );
  }
}
