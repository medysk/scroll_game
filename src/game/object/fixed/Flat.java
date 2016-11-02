package game.object.fixed;

import java.awt.Graphics;

/**
 * ���n�I�u�W�F�N�g
 * @author medysk
 *
 */
public class Flat extends Ground {

  public Flat( int positionX, int positionY ) {
    super( positionX, positionY );
  }


  /* (�� Javadoc)
   * @see game.object.fixed.Ground#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    super.draw(g);
    g.fillRect( positionX, positionY, width, height );
  }
}
