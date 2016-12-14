package game.object.fixed;

import java.awt.Graphics;

import game.config.GameData;
import game.object.FixedObj;

/**
 * @author medysk
 * ブロックオブジェクトのスーパークラス
 */
public abstract class Block extends FixedObj {

  /**
   * 設定の初期化
   * @param positionX X座標の初期位置
   * @param positionY Y座標の初期位置
   */
  public Block(int positionX, int positionY) {
    super(positionX, positionY);
    canPassing = false;
    height = GameData.BASE_OBJ_HEIGHT;
    width = GameData.BASE_OBJ_WIDTH;
  }

  public void event() {}

  /* (非 Javadoc)
   * @see game.object.Obj#draw(java.awt.Graphics)
   */
  @Override
  public abstract void draw(Graphics g);
}
