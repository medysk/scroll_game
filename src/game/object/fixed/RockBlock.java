package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;

/**
 * �j��ł��Ȃ��u���b�N
 * @author medysk
 *
 */
public class RockBlock extends Block {

  /**
   * �ݒ�̏�����
   * @param positionX
   * @param positionY
   */
  public RockBlock(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;   // ���Ȃ�true( default: true )
    canCollision = true;   // �Փˉ\( default: true )
    isDestory = false;     // �j��\�Ȃ�true
  }

  /**
   * �s����
   */
  public void invisibility() {
    isVisibility = false;
    canPassing = true;
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Block#bottomAction()
   */
  @Override
  public void bottomEvent() {
    // �u���b�N���s�������������
    if( ! isVisibility ) {
      isVisibility = true;
      canPassing = false;
    }
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Block#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(128, 128, 128) );
    g.fillRect( positionX, positionY, width, height );
    g.setColor( new Color(32, 32, 32) );
    g.drawRect( positionX, positionY, width, height );
  }
}
