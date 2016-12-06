package game;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import game.config.GameData;
import game.system.KeyState;

/**
 * @author medysk
 * GUI用のフレームを作成
 */
public class Frame extends JFrame {

  private JLayeredPane layerPane;

  public Frame() {
    setTitle("Horizontal Scroll");
    layerPane = new JLayeredPane();
    layerPane.setPreferredSize(
        new Dimension( GameData.PANEL_WIDTH, GameData.PANEL_HEIGHT ));

    getContentPane().add(layerPane);

    addKeyListener(KeyState.getInstance());

    setDefaultCloseOperation( EXIT_ON_CLOSE );
    setVisible( true );
    pack();
  }

  /**
   * パネル追加
   * @param panel
   */
  public void addLayer(JPanel panel, Integer layer) {
    layerPane.add(panel, layer);
  }

  /**
   * パネル削除
   * @param panel
   */
  public void removeLayer(JPanel panel) {
    layerPane.remove(panel);
  }
}
