package game.system;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * @author medysk
 * キーイベントをもとに現在のキーの状態を保持するクラス
 */
public class KeyState implements KeyListener {
  private HashMap<String, Boolean> keyStatus;
  private static KeyState keyState;

  static {
    keyState = new KeyState();
  }

  /**
   * キーステートを生成する
   */
  public KeyState() {
    keyStatus = new HashMap<String, Boolean>();

    for( Key key: Key.values() ) {
      keyStatus.put( key.getName(), false );
    }
  }

  /* (非 Javadoc)
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  public void keyPressed( KeyEvent e ) {
    switch( Key.searchByCode( e.getKeyCode() ) ) {
      case X :
        keyStatus.put( Key.X.getName(), true );
          break;
      case Z :
        keyStatus.put( Key.Z.getName(), true );
          break;
      case UP:
        keyStatus.put( Key.UP.getName(), true );
        break;
      case RIGHT :
        keyStatus.put( Key.RIGHT.getName(), true );
        break;
      case DOWN :
        keyStatus.put( Key.DOWN.getName(), true );
        break;
      case LEFT :
        keyStatus.put( Key.LEFT.getName(), true );
        break;
      case SPACE :
        keyStatus.put( Key.SPACE.getName(), true );
          break;
      case ENTER :
        keyStatus.put( Key.ENTER.getName(), true );
          break;
    }
  }

  /* (非 Javadoc)
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  public void keyReleased( KeyEvent e ) {
    switch( Key.searchByCode( e.getKeyCode() ) ) {
    case X :
      keyStatus.put( Key.X.getName(), false );
        break;
    case Z :
      keyStatus.put( Key.Z.getName(), false );
        break;
    case UP:
      keyStatus.put( Key.UP.getName(), false );
      break;
    case RIGHT :
      keyStatus.put( Key.RIGHT.getName(), false );
      break;
    case DOWN :
      keyStatus.put( Key.DOWN.getName(), false );
      break;
    case LEFT :
      keyStatus.put( Key.LEFT.getName(), false );
      break;
    case SPACE :
      keyStatus.put( Key.SPACE.getName(), false );
        break;
    case ENTER :
      keyStatus.put( Key.ENTER.getName(), false );
        break;
    }
  }

  public void keyTyped( KeyEvent e ) {}

  /**
   * 全てのキーステートをクリア（false）する
   */
  public void clearAll() {
    keyStatus.forEach( (k,v) -> {
      keyStatus.put( k, false );
    });
  }

  /**
   * 指定したキーステートをクリア（false）する
   * @param key
   */
  public void clear( Key key ) {
    keyStatus.put( key.getName(), false );
  }

  /**
   * 任意のキーの状態を返す
   * @param keyName Key(enum)のnameを渡す
   * @return true: キーが押されている false: キーが押されていない
   */
  public boolean isKeyPressed( Key key ) {
    return keyStatus.get( key.getName() );
  }

  /**
   * 全てのキーの状態を返す
   * @return keyName( Key(enum)のname ), キーが押されていればtrue
   */
  public HashMap<String, Boolean> getKeyState() {
    return keyStatus;
  }

  /**
   * キーステートの取得
   * @return
   */
  public static KeyState getInstance() {
    return keyState;
  }
}
