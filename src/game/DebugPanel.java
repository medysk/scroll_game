package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

public class DebugPanel extends JPanel {

  HashMap<String, String> strs = new HashMap<>();

  public DebugPanel() {
    setPreferredSize( new Dimension(200, 200) );
    setFocusable( true );
  }

  public void paintComponent( Graphics g ) {
    super.paintComponent(g);

  }
}