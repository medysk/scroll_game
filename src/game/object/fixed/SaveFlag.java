/**
 *
 */
package game.object.fixed;

import java.awt.Color;

import game.system.StageManager;

/**
 * �X�e�[�W�̃Z�[�u�n�_�ɐݒu�����
 * @author medysk
 *
 */
public class SaveFlag extends Flag {

  /**
   * �ݒ�̏�����
   * @param positionX
   * @param positionY
   */
  public SaveFlag(int positionX, int positionY) {
    super(positionX, positionY);
    flagColor = Color.CYAN;
    flagSymbol = "S";
  }

  @Override
  public void event() {
    flagColor = Color.WHITE;
    StageManager.save();
  }

}
