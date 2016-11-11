package game.object.fixed;

import java.awt.Color;

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
    flagColor = Color.WHITE;
    flagSymbol = "G";
  }

}
