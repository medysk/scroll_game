package game;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * @author medysk
 * GUI用のフレームを作成
 */
public class Frame extends JFrame {
//  private Container contentPane;
  private JLayeredPane layerPane;

  public Frame(JPanel panel) {
    setTitle("Horizontal Scroll");
//    contentPane = getContentPane();
    layerPane = new JLayeredPane();
    layerPane.setPreferredSize(new Dimension( StagePanel.WIDTH, StagePanel.HEIGHT ));
    layerPane.add(panel, JLayeredPane.DEFAULT_LAYER);

    getContentPane().add(layerPane);

    setDefaultCloseOperation( EXIT_ON_CLOSE );
    setVisible( true );
    pack();
  }

  /**
   * フレーム設定
   * @param panel ゲーム描写用のパネル貼り付け
   */
  public void addLayer(JPanel panel, Integer layer) {
    layerPane.add(panel, layer);
  }

  public void removeLayer(JPanel panel) {
    layerPane.remove(panel);
  }
}
