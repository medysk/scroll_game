package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import game.object.Obj;
import game.object.move.player.Character;
import game.system.Map;

/**
 * �Q�[���p�̃��C���p�l��
 * @author medysk
 *
 */
public class FieldPanel extends JPanel {
  // TODO: �ݒ�t�@�C������ǂݍ���
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;
  private Character character;

  // �Q�[���ɕ`�ʂ���I�u�W�F�N�g���i�[����
  private HashMap<String,Obj> objs = new HashMap<>();

  /**
   * �p�l���̐ݒ�ƕϐ��̏�����
   */
  public FieldPanel() {
    setPreferredSize( new Dimension(WIDTH, HEIGHT) );
    setFocusable( true );
    objs = Obj.getInstances();
  }

  @Override
  public void paintComponent( Graphics g ) {
    super.paintComponent(g);

    // TODO: Background�N���X�ɏ�����C����
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );

    // �I�u�W�F�N�g�̕`��
    if( objs.isEmpty() ) {
      return;
    }

    // �I�u�W�F�N�g�̕`��
    objs.forEach( (k, obj) -> {
      // ��ʊO�̃I�u�W�F�N�g�͕`�ʂ��Ȃ�
      if( character.getPositinX() + WIDTH / 2 < obj.getPositinX() &&
          character.getPositinX() - WIDTH / 2 > obj.getPositinX() + obj.getWidth() ) {
        return;
      }

      // �L�����N�^�[�ɍ��킹�ă}�b�v���X���C�h������
      if( character.getPositinX() < (WIDTH / 2) ) {
        obj.draw( g.create() );
      } else if( character.getPositinX() > (Map.getRightLimit() - WIDTH / 2) ) {
        Graphics ng = g.create();
        ng.translate( - Map.getRightLimit() + (WIDTH), 0);
        obj.draw(ng);
      } else {
        Graphics ng = g.create();
        ng.translate(- character.getPositinX() + (WIDTH / 2), 0);
        obj.draw(ng);
      }
    } );
  }

  /**
   * setter
   * @param character �L�����N�^�[�̃C���X�^���X
   */
  public void setCharacter( Character character ) {
    this.character = character;
  }
}
