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
  public RockBlock(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;   // 可視ならtrue( default: true )
    canCollision = true;   // 衝突可能( default: true )
    isDestory = false;     // 破壊可能ならtrue
  }

  /**
   * 不可視化
   */
  public void invisibility() {
    isVisibility = false;
    canPassing = true;
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Block#bottomAction()
   */
  @Override
  public void bottomEvent() {
    // ブロックが不可視だったら可視化
    if( ! isVisibility ) {
      isVisibility = true;
      canPassing = false;
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
