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
  public RockBlock(int positionX, int positionY, boolean isVisibility) {
    super(positionX, positionY);
    this.isVisibility = isVisibility;   // ���Ȃ�true
    canCollision = isVisibility;        // ���Ȃ�Փˉ\
    isDestory = false;                  // �j��\�Ȃ�true
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Block#bottomAction()
   */
  @Override
  public void bottomEvent() {
    // �u���b�N���s�������������
    if( ! isVisibility ) {
      isVisibility = true;
      canCollision = true;
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
