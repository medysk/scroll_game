package game.object.fixed;

import java.awt.Graphics;

/**
 * �����I�u�W�F�N�g
 * @author medysk
 *
 */
public class Downhill extends Ground {

  public Downhill( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Ground#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
      super.draw(g);
      int[] xPoints = { 0,  0, 30 };
      int[] yPoints = { 0, 30, 30 };
      g.translate( positionX, positionY );
      g.fillPolygon(xPoints, yPoints, xPoints.length );
  }
}
