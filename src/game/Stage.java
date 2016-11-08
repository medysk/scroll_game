package game;

import java.util.concurrent.TimeUnit;

import game.Frame;
import game.MapPanel;
import game.object.MoveObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.FrameManagement;
import game.system.KeyState;
import game.system.Map;

/**
 * �v���O�������s�p�̃N���X
 * @author medysk
 *
 */
public class Stage implements Runnable {
  private static Frame frame;           // GUI�p�̃t���[��
  private static MapPanel mapPanel; // �Q�[���`�ʗp�̃��C���p�l��
  private static Character character;   // ���L����
  private static KeyState keyState;     // �L�[���͊Ǘ�
  private static Thread game;           // �X���b�h�p�N���X

  // �������A�ݒ�A���s
  static {
    Map.create();
    frame = new Frame();
    mapPanel = new MapPanel();
    keyState = new KeyState();
    mapPanel.addKeyListener(keyState);
    character = (Character) Obj.create( new Character( 20, 20, keyState ) ); // TODO: �����ʒu�͐ݒ�t�@�C������ǂݍ���
    mapPanel.setCharacter(character);
  }

  /**
   * Main Method
   */
  public static void main(String[] args){
    FrameManagement.increment();
    game = new Thread( new Stage() );

    frame.setParams( mapPanel );
    game.start();
  }

  /**
   * �X���b�h�̎������\�b�h
   * �Q�[���̋N�_
   */
  public void run() {

    while(true) {
      FrameManagement.increment();

      Obj.getMoveObjIds().forEach( id -> {
        ((MoveObj) Obj.getInstances().get(id)).execute();
      });

      mapPanel.repaint();      // ���C���p�l���̍ĕ`��

      // �Q�[���̑��x�ɉe�����鏈��
      // TODO: �Q�[���̊�{�@�\�����������̂��A�������� �܂��A�ݒ�t�@�C������ǂݍ���
      try {
        TimeUnit.MILLISECONDS.sleep(15);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }
}
