package game.system;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * @author medysk
 * キーイベントをもとに現在のキーの状態を保持するクラス
 */
public class KeyState implements KeyListener {
  private HashMap<String, Boolean> keyState;

  public KeyState() {
    keyState = new HashMap<String, Boolean>();

    for( Key key: Key.values() ) {
      keyState.put( key.getName(), false );
    }
  }

  /* (非 Javadoc)
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  public void keyPressed( KeyEvent e ) {
    switch( e.getKeyCode() ) {
      case KeyEvent.VK_UP :
        keyState.put( Key.UP.getName(), true );
        break;
      case KeyEvent.VK_RIGHT :
        keyState.put( Key.RIGHT.getName(), true );
        break;
      case KeyEvent.VK_DOWN :
        keyState.put( Key.DOWN.getName(), true );
        break;
      case KeyEvent.VK_LEFT :
        keyState.put( Key.LEFT.getName(), true );
        break;
    }
  }

  /* (非 Javadoc)
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  public void keyReleased( KeyEvent e ) {
    switch( e.getKeyCode() ) {
      case KeyEvent.VK_UP :
        keyState.put( Key.UP.getName(), false );
        break;
      case KeyEvent.VK_RIGHT :
        keyState.put( Key.RIGHT.getName(), false );
        break;
      case KeyEvent.VK_DOWN :
        keyState.put( Key.DOWN.getName(), false );
        break;
      case KeyEvent.VK_LEFT :
        keyState.put( Key.LEFT.getName(), false );
        break;
    }
  }

  public void keyTyped( KeyEvent e ) {}

  /**
   * 任意のキーの状態を返す
   * @param keyName Key(enum)のnameを渡す
   * @return true: キーが押されている false: キーが押されていない
   */
  public boolean isKeyPressed( String keyName ) {
    return keyState.get( keyName );
  }

  /**
   * 全てのキーの状態を返す
   * @return keyName( Key(enum)のname ), キーが押されていればtrue
   */
  public HashMap<String, Boolean> getKeyState() {
    return keyState;
  }
}
