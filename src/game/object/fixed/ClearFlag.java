package game.object.fixed;

import java.awt.Color;

import game.Stage;

/**
 * �X�e�[�W�̃N���A�n�_�ɐݒu�����
 * @author medysk
 *
 */
public class ClearFlag extends Flag {

  /**
   * �ݒ�̏�����
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
