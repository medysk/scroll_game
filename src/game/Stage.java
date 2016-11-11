package game;

import java.util.concurrent.TimeUnit;

import javax.swing.JLayeredPane;

import game.Frame;
import game.StagePanel;
import game.object.MoveObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.FrameManager;
import game.system.KeyState;
import game.system.Map;
import game.system.StageManager;

/**
 * �v���O�������s�p�̃N���X
 * @author medysk
 *
 */
public class Stage implements Runnable {
  private static Frame frame;           // GUI�p�̃t���[��
  private static StagePanel stagePanel; // �Q�[���`�ʗp�̃��C���p�l��
  private static KeyState keyState;     // �L�[���͊Ǘ�
  private static Thread stage;          // �X���b�h�p�N���X
  private static boolean isClear;              // �N���A�t���O
  private static int deathCount;               // ���S��

  // �������A�ݒ�A���s
  static {
    keyState = new KeyState();
    Obj.create( new Character( 20, 20, keyState ) ); // TODO: �����ʒu�͐ݒ�t�@�C������ǂݍ���
    Map.create();
    stagePanel = new StagePanel();
    frame = new Frame(stagePanel);
    stagePanel.addKeyListener(keyState);
    isClear = false;
    deathCount = 0;
  }

  /**
   * Main Method
   */
  public static void main(String[] args){
    FrameManager.increment();
    stage = new Thread( new Stage() );

    echo("Game Start", 400, 100, 4, 300);
    StageManager.save();
    stage.start();
  }

  /**
   * �X���b�h�̎������\�b�h
   * �Q�[���̋N�_
   */
  public void run() {
    while(true) {
      FrameManager.increment();

      Obj.moveObjIds().forEach( id -> {
        // �C���X�^���X���Q�[�������菜���ꂽ�ꍇ�X�L�b�v
        if( ! Obj.getInstances().containsKey(id) ) {
          return;
        }
        ((MoveObj) Obj.getInstances().get(id)).execute();
      });

      // �v���C���[���Q�[�����珜�O���ꂽ�烊�X�^�[�g
      if( Obj.getCharacter() == null ) {
        restart();
      }

      stagePanel.repaint();      // ���C���p�l���̍ĕ`��

      // �X�e�[�W�N���A������X���b�h���I��
      if( isClear ) { break; }

      // �Q�[���̑��x
      // TODO: �Q�[���̊�{�@�\�����������̂��A�������� �܂��A�ݒ�t�@�C������ǂݍ���
      try {
        TimeUnit.MILLISECONDS.sleep(15);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }

  /**
   * �X�e�[�W���ŏ�����A�������̓Z�[�u�|�C���g����J�n����
   */
  public static void restart() {
    deathCount++;
    StageManager.load();
    echo("restart", 450, 100, 4, 300);
  }

  /**
   * �X�e�[�W�N���A�̏���
   */
  public static void clear() {
    isClear = true;
    echo("Game Clear", 400, 100, 5, 300);
    String msg = "���Ȃ��̎��S�񐔂�" + deathCount + "��ł�";
    echo(msg, 400, 100, 1, 5000);
  }

  /**
   * �������`��
   * @param str ������
   * @param x X���W
   * @param y Y���W
   */
  public static void echo( String msg, int x, int y, int flashing, int milliSeconds ) {
    HeadingPanel headingPanel = new HeadingPanel(msg, x, y);

    for( int i=0; i<flashing*2; i++) {
      // �������_�ł�����
      if( i % 2 == 0) {
        frame.addLayer(headingPanel, JLayeredPane.POPUP_LAYER);
      }
      try {
        TimeUnit.MILLISECONDS.sleep(milliSeconds);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
      frame.removeLayer(headingPanel);
      stagePanel.repaint();
    }
    headingPanel = null;
  }
}
