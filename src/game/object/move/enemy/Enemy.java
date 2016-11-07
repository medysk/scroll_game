package game.object.move.enemy;

import java.awt.Color;
import java.awt.Graphics;

import game.object.MoveObj;
import game.object.Obj;
import game.system.FrameManagement;
import game.system.Side;

/**
 * �G�L�����N�^�[
 * @author medysk
 *
 */
public class Enemy extends MoveObj {

  /**
   * �ݒ�̏�����
   * @param positionX �����ʒu
   * @param positionY
   */
  public Enemy(int positionX, int positionY) {
    super(positionX, positionY);
    height = 28;
    width = 28;
    minSpeed = 2;
    maxSpeed = 2;
    fallVelocity = 1;
    maxFallVelocity = height - 1;
    verticalLeap = 12;
  }

  /* (�� Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void execute() {
    super.execute();

    // �Փˏ���
    collisionHandling( data -> {
   // �I�u�W�F�N�g��TOP�ɏՓ˂��� ���� �W�����v��(�㏸��)
      if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {
        vectorY = - vectorY / 3;
      }
    });

    // �ʒu�␳��ɑO��ʒu���X�V
    updatePrePosition();
  }

  /* (�� Javadoc)
   * @see game.object.MoveObj#action()
   */
  @Override
  protected void action() {
    if( FrameManagement.isActionFrame(100) ) {
      jump();
    }

    vectorX = Obj.getCharacter().getPositionX() < positionX ?
        - minSpeed : minSpeed;
  }

  /* (�� Javadoc)
   * @see game.object.Obj#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( Color.ORANGE);
    g.fillRect(positionX, positionY, width, height);
  }

}
