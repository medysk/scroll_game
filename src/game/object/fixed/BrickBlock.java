package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

/**
 * �j��\�ȃu���b�N
 * @author medysk
 *
 */
public class BrickBlock extends Block {

  /**
   * �ݒ�̏�����
   * @param positionX
   * @param positionY
   */
  public BrickBlock(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;    // ���Ȃ�true
    isDestory = true;       // �j��\�Ȃ�true
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Block#bottomAction()
   */
  @Override
  public void bottomAction() {
    // TODO: �j��A�j���[�V�������������Ăяo��

    // �Q�[�����珜�O
    destructor();
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Block#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(139, 69, 19) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(60, 60, 60) );
    g.drawRect( positionX, positionY, width, height );
    g.setColor( new Color(96, 96, 96) );
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
