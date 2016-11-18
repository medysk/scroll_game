package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

import config.GameData;

/**
 * @author medysk
 * 破壊できないブロック
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
    g.setColor( new Color(GameData.ROCK_BLOCK_MAIN_COLOR) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(GameData.ROCK_BLOCK_FRAME_COLOR) );
    g.drawRect( positionX, positionY, width, height );
  }
}
