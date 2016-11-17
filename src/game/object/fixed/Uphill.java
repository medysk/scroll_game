package game.object.fixed;

import java.awt.Graphics;

/**
 * @author medysk
 * 上り坂オブジェクト
 */
public class Uphill extends Ground {

  public Uphill( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Ground#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
      super.draw(g);
      int[] xPoints = {  0, 30, 30 };
      int[] yPoints = { 30, 30,  0 };
      g.translate( positionX, positionY );
      g.fillPolygon( xPoints, yPoints, xPoints.length );
  }
}
