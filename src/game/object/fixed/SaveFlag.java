
package game.object.fixed;

import java.awt.Color;

import game.system.StageManager;

/**
 * @author medysk
 * ステージのセーブ地点に設置する旗
 */
public class SaveFlag extends Flag {

  private boolean canSave;

  /**
   * 設定の初期化
   * @param positionX
   * @param positionY
   */
  public SaveFlag(int positionX, int positionY) {
    super(positionX, positionY);
    flagColor = Color.CYAN;
    flagSymbol = "S";
    canSave = true;
  }

  @Override
  public void event() {
    if(! canSave) {
      return;
    }
    flagColor = Color.WHITE;
    StageManager.save();
    canSave = false;
  }

}
