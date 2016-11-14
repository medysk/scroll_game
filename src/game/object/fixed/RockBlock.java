package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 破壊できないブロック
 * @author medysk
 *
 */
public class RockBlock extends Block {

  /**
   * 設定の初期化
   * @param positionX
   * @param positionY
   */
  public RockBlock(int positionX, int positionY, boolean isVisibility) {
    super(positionX, positionY);
    this.isVisibility = isVisibility;   // 可視ならtrue
    canCollision = isVisibility;        // 可視なら衝突可能
    isDestory = false;                  // 破壊可能ならtrue
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Block#bottomAction()
   */
  @Override
  public void bottomEvent() {
    // ブロックが不可視だったら可視化
    if( ! isVisibility ) {
      isVisibility = true;
      canCollision = true;
    }
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Block#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(128, 128, 128) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(32, 32, 32) );
    g.drawRect( positionX, positionY, width, height );
  }
}
