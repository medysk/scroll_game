package game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class HeadingPanel extends JPanel {
  // TODO: ê›íËÉtÉ@ÉCÉãÇ©ÇÁì«Ç›çûÇﬁ
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

  /* (îÒ Javadoc)
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;

    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 36);
    g2.setFont(font2);

    g2.drawString(heading, x, y);
  }

  public void set(String heading, int left, int top) {

  }
}
