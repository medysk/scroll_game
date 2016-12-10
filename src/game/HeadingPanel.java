package game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import game.config.GameData;

/**
 * @author medysk
 * 文字表示
 */
public class HeadingPanel extends JPanel {
  private static final int width = GameData.PANEL_WIDTH;
  private static final int height = GameData.PANEL_HEIGHT;

  private String heading;
  private int x;
  private int y;

  /**
   * 初期化
   */
  public HeadingPanel(String heading, int x, int y) {
    setSize( new Dimension(width, height) );
    setOpaque(false);
    setFocusable(false);
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
