package game;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * GUI�p�̃t���[�����쐬
 * @author medysk
 *
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
   * �t���[���ݒ�
   * @param panel �Q�[���`�ʗp�̃p�l���\��t��
   */
  public void addLayer(JPanel panel, Integer layer) {
    layerPane.add(panel, layer);
  }

  public void removeLayer(JPanel panel) {
    layerPane.remove(panel);
  }
}
