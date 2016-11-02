package game.system;

import java.awt.event.KeyEvent;

/**
 * ���̃v���O������ł̃L�[�̖��O�𓝈ꂷ�邽�߂�enum
 * @author medysk
 *
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
   * @return �L�[��name��Ԃ�
   */
  public String getName() {
    return name;
  }

  /**
   * getter
   * @return �L�[�R�[�h��Ԃ�
   */
  public int getCode() {
    return code;
  }
}
