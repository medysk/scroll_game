package game.system;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * �L�[�C�x���g�����ƂɌ��݂̃L�[�̏�Ԃ�ێ�����N���X
 * @author medysk
 *
 */
public class KeyState implements KeyListener {
  private HashMap<String, Boolean> keyState;

  public KeyState() {
    keyState = new HashMap<String, Boolean>();

    for( Key key: Key.values() ) {
      keyState.put( key.getName(), false );
    }
  }

  /* (�� Javadoc)
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

  /* (�� Javadoc)
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
   * �C�ӂ̃L�[�̏�Ԃ�Ԃ�
   * @param keyName Key(enum)��name��n��
   * @return true: �L�[��������Ă��� false: �L�[��������Ă��Ȃ�
   */
  public boolean isKeyPressed( String keyName ) {
    return keyState.get( keyName );
  }

  /**
   * �S�ẴL�[�̏�Ԃ�Ԃ�
   * @return keyName( Key(enum)��name ), �L�[��������Ă����true
   */
  public HashMap<String, Boolean> getKeyState() {
    return keyState;
  }
}
