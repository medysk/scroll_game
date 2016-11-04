package game.object.fixed;

import java.awt.Color;
import java.awt.Graphics;
import game.object.FixedObj;

/**
 * �n�ʃI�u�W�F�N�g�̃X�[�p�[�N���X
 * �T�u�N���X��Flat, Downhill, Uphill������
 * @author medysk
 *
 */
public class Ground extends FixedObj {
  // Constructor
  public Ground(int positionX, int positionY) {
    super( positionX, positionY );
    isVisibility = true;
    isDestory = false;
    height = 30;
    width = 30;
  }

  /* (�� Javadoc)
   * @see game.object.fixed.Ground#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(139, 69, 19) );
  }
}