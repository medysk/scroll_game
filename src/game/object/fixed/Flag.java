package game.object.fixed;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.object.FixedObj;

/**
 * ステージ上に設置する旗のスーパークラス
 * @author medysk
 *
 */
public abstract class Flag extends FixedObj {

  protected Color flagColor;
  protected String flagSymbol;

  /**
   * 設定の初期化
   * @param positionX
   * @param positionY
   */
  public Flag(int positionX, int positionY) {
    super(positionX, positionY);
    isVisibility = true;    // 可視ならtrue
    isDestory = true;       // 破壊可能ならtrue
    canCollision = true;    // 衝突可能ならtrue
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
