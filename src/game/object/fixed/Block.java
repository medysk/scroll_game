package game.object.fixed;

import java.awt.Graphics;

import game.object.FixedObj;

/**
 * �u���b�N�I�u�W�F�N�g�̃X�[�p�[�N���X
 *
 * @author medysk
 *
 */
public class Block extends FixedObj {

  /**
   * �ݒ�̏�����
   * @param positionX X���W�̏����ʒu
   * @param positionY Y���W�̏����ʒu
   */
  public Block(int positionX, int positionY) {
    super(positionX, positionY);
    // TODO: �ݒ�t�@�C������ǂݍ���
    height = 30;
    width = 30;
  }

  /* (�� Javadoc)
   * @see game.object.Obj#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {}
}
