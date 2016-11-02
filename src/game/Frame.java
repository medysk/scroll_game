package game;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * GUI�p�̃t���[�����쐬
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
   * �t���[���ݒ�
   * @param panel �Q�[���`�ʗp�̃p�l���\��t��
   */
  public void setParams( JPanel panel) {
    contentPane.add(panel);

    setDefaultCloseOperation( EXIT_ON_CLOSE );
    setVisible( true );
    pack();
  }
}
