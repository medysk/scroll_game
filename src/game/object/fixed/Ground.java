package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;
import game.object.FixedObj;

/**
 * 地面オブジェクトのスーパークラス
 * サブクラスにFlat, Downhill, Uphillを持つ
 * @author medysk
 *
 */
public class Ground extends FixedObj {
  // Constructor
  public Ground(int positionX, int positionY) {
    super( positionX, positionY );
    isVisibility = true;
    isDestory = false;
    canCollision = true;
    height = 30;
    width = 30;
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Ground#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(222, 184, 135) );
  }

  @Override
  public void event() {}

  /* (非 Javadoc)
   * @see game.object.FixedObj#bottomAction()
   */
  @Override
  public void bottomEvent() {}

}
