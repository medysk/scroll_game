package game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * @author medysk
 * 文字表示
 */
public class HeadingPanel extends JPanel {
  // TODO: 設定ファイルから読み込む
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;

  private String heading;
  private int x;
  private int y;

  /**
   *
   */
  public HeadingPanel(String heading, int x, int y) {
    setSize( new Dimension(WIDTH, HEIGHT) );
    setOpaque(false);
    setFocusable( true );
    this.heading = heading;
    this.x = x;
    this.y = y;
  }

  /* (非 Javadoc)
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;

    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 36) );

    g2.drawString(heading, x, y);
  }
}
