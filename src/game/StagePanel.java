package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import game.object.FixedObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.Map;

/**
 * �Q�[���p�̃��C���p�l��
 * @author medysk
 *
 */
public class StagePanel extends JPanel {
  // TODO: �ݒ�t�@�C������ǂݍ���
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;

  // �Q�[���ɕ`�ʂ���I�u�W�F�N�g���i�[����
  private HashMap<String,Obj> objs = new HashMap<>();

  /**
   * �p�l���̐ݒ�ƕϐ��̏�����
   */
  public StagePanel() {
    setSize( new Dimension(WIDTH, HEIGHT) );
    setFocusable( true );
    objs = Obj.getInstances();
  }

  @Override
  public void paintComponent( Graphics g ) {
    super.paintComponent(g);

    // TODO: Background�N���X�ɏ�����C����
    g.setColor( new Color(30, 144, 255) );
    g.fillRect( 0, 0, getWidth(), getHeight() );

    // �I�u�W�F�N�g�̕`��
    if( objs.isEmpty() ) {
      return;
    }

    // �I�u�W�F�N�g�̕`��
    objs.forEach( (k, obj) -> {
      Character character = Obj.getCharacter();
      // �s���I�u�W�F�N�g�̏ꍇ�A�`�ʂ��Ȃ�
      if( obj instanceof FixedObj && ! ((FixedObj)obj).isVisivility() ) {
        return;
      }

      // ��ʊO�̃I�u�W�F�N�g�͕`�ʂ��Ȃ�
      if( character.getPositionX() + WIDTH / 2 < obj.getPositionX() &&
          character.getPositionX() - WIDTH / 2 > obj.getPositionX() + obj.getWidth() ) {
        return;
      }

      // �L�����N�^�[�ɍ��킹�ă}�b�v���X���C�h������
      if( character.getPositionX() < (WIDTH / 2) ) {
        obj.draw( g.create() );
      } else if( character.getPositionX() > (Map.getRightLimit() - WIDTH / 2) ) {
        Graphics ng = g.create();
        ng.translate( - Map.getRightLimit() + (WIDTH), 0);
        obj.draw(ng);
      } else {
        Graphics ng = g.create();
        ng.translate(- character.getPositionX() + (WIDTH / 2), 0);
        obj.draw(ng);
      }
    } );
  }
}
