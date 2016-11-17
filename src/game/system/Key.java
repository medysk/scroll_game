package game.system;

import java.awt.event.KeyEvent;

/**
 * @author medysk
 * このプログラム上でのキーの名前を統一するためのenum
 */
public enum Key {
  UP   ("up",    KeyEvent.VK_UP),
  RIGHT( "right", KeyEvent.VK_RIGHT ),
  DOWN ("down",  KeyEvent.VK_DOWN),
  LEFT ("left",  KeyEvent.VK_LEFT);

  private final String name;
  private final int code;

  Key(String name, int code) {
    this.name = name;
    this.code = code;
  }

  /**
   * getter
   * @return キーのnameを返す
   */
  public String getName() {
    return name;
  }

  /**
   * getter
   * @return キーコードを返す
   */
  public int getCode() {
    return code;
  }
}
