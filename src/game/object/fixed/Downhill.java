package game.object.fixed;

import java.awt.Graphics;

/**
 * @author medysk
 * 下り坂オブジェクト
 */
public class Downhill extends Ground {

  public Downhill( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /* (非 Javadoc)
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
