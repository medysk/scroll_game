package game;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * GUI用のフレームを作成
 * @author medysk
 *
 */
public class Frame extends JFrame {
  private Container contentPane;

  public Frame() {
    setTitle("Horizontal Scroll");
    contentPane = getContentPane();
  }

  /**
   * フレーム設定
   * @param panel ゲーム描写用のパネル貼り付け
   */
  public void setParams( JPanel panel) {
    contentPane.add(panel);

    setDefaultCloseOperation( EXIT_ON_CLOSE );
    setVisible( true );
    pack();
  }
}
