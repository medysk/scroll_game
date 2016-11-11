package game.object.fixed;

import java.awt.Color;

import game.Stage;

/**
 * ステージのクリア地点に設置する旗
 * @author medysk
 *
 */
public class ClearFlag extends Flag {

  /**
   * 設定の初期化
   * @param positionX
   * @param positionY
   */
  public ClearFlag(int positionX, int positionY) {
    super(positionX, positionY);
    flagColor = Color.YELLOW;
    flagSymbol = "G";
  }

  @Override
  public void event() {
    Stage.clear();
  }

}
