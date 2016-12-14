package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

import game.config.GameData;

/**
 * @author medysk
 * 破壊可能なブロック
 */
public class BrickBlock extends Block {

  /**
   * 設定の初期化
   * @param positionX
   * @param positionY
   */
  public BrickBlock(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;    // 可視ならtrue
    isDestory = true;       // 破壊可能ならtrue
    canCollision = true;    // 衝突可能ならtrue
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Block#bottomAction()
   */
  @Override
  public void bottomEvent() {
    // TODO: 破壊アニメーションを実装し呼び出す

    // ゲームから除外
    destructor();
  }

  /* (非 Javadoc)
   * @see game.object.fixed.Block#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(GameData.BRICK_BLOCK_MAIN_COLOR) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(GameData.BRICK_BLOCK_FRAME_COLOR) );
    g.drawRect( positionX, positionY, width, height );
    g.setColor( new Color(GameData.BRICK_BLOCK_LINE_COLOR) );
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
