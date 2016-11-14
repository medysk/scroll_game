package game.object.fixed;

import java.awt.Graphics;

import game.object.FixedObj;

/**
 * ブロックオブジェクトのスーパークラス
 *
 * @author medysk
 *
 */
public abstract class Block extends FixedObj {

  /**
   * 設定の初期化
   * @param positionX X座標の初期位置
   * @param positionY Y座標の初期位置
   */
  public Block(int positionX, int positionY) {
    super(positionX, positionY);
    // TODO: 設定ファイルから読み込む
    canPassing = false;
    height = 30;
    width = 30;
  }

  public void event() {}

  /* (非 Javadoc)
   * @see game.object.Obj#draw(java.awt.Graphics)
   */
  @Override
  public abstract void draw(Graphics g);
}
