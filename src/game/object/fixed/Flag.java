package game.object.fixed;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.object.FixedObj;

/**
 * �X�e�[�W��ɐݒu������̃X�[�p�[�N���X
 * @author medysk
 *
 */
public abstract class Flag extends FixedObj {

  protected Color flagColor;
  protected String flagSymbol;

  /**
   * �ݒ�̏�����
   * @param positionX
   * @param positionY
   */
  public Flag(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;    // ���Ȃ�true
    isDestory = true;       // �j��\�Ȃ�true
    canCollision = true;    // �Փˉ\�Ȃ�true
    height = 80;
    width = 30;
  }

  @Override
  public void bottomEvent() {}

  @Override
  public void draw(Graphics g) {
    g.translate(positionX, positionY);
    g.setColor( Color.GRAY );
    g.fillRect(0, 0, 5, 80);
    g.setColor( flagColor );
    int xPoints[] = { 5, 30, 5 };
    int yPoints[] = { 0, 15, 30 };

    g.fillPolygon(xPoints, yPoints, xPoints.length);

    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 16) );
    g.setColor( Color.DARK_GRAY );
    g2.drawString( flagSymbol, 7, 20);
  }

}
