package game.object.move.player;

import java.awt.Color;
import java.awt.Graphics;

import game.object.FixedObj;
import game.object.MoveObj;
import game.object.move.enemy.Enemy1;
import game.system.CollisionData;
import game.system.Key;
import game.system.KeyState;
import game.system.Map;
import game.system.Momentum;
import game.system.Side;

/**
 * ���L�����N�^�[
 * @author medysk
 *
 */
public class Character extends MoveObj {
  private KeyState keyState; // �L�[�̏��(������Ă��邩�ǂ���)
  private Momentum momentum; // �I�u�W�F�N�g�̐����𒲐�����N���X

  public Character( int positionX, int positionY, KeyState keyState ) {
    super( positionX, positionY );
    this.keyState = keyState;
    // TODO: �ݒ�t�@�C������ǂݍ���
    height = 50;
    width = 28;
    minSpeed = 1;
    maxSpeed = 10;
    fallVelocity = 1;
    maxFallVelocity = height - 1; //
    verticalLeap = 20;
    momentum = new Momentum( this ); // Momentum�͔j��I�ȑ�����s��
  }

  /* (�� Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void execute() {
    super.execute();

    // �}�b�v�̗��[����o�Ȃ��悤�ɂ���
    positionWithinLimit();

    // �Փˏ���
    collisionHandling( data -> {
      // �Փ˂����I�u�W�F�N�g��FixedObj��MoveObj���ŁA�����𕪂���
      if( data.getSubject() instanceof FixedObj ) {
        collisionHandlingForFixedObj(data);
      } else if( data.getSubject() instanceof MoveObj ) {
        collisionHandlingForMoveObj(data);
      }
    });

    // �ʒu�␳��ɑO��ʒu���X�V
    updatePrePosition();

  }

  /* (�� Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void draw( Graphics g ) {
    g.setColor( Color.BLUE );
    g.fillRect( positionX, positionY, width, height );
  }

  // ###  Protected methods  ###

  /* (�� Javadoc)
   * @see game.object.MoveObj#action()
   */
  @Override
  protected void action() {
    if( keyState.isKeyPressed( Key.RIGHT.getName() ) ) {
      momentum.rightVectorIncrease();
    } else if( isRightMove() ) {
      momentum.rightVectorDecrease();
    }

    if( keyState.isKeyPressed( Key.LEFT.getName() ) ) {
      momentum.leftVectorIncrease();
    } else if( isLeftMove() ) {
      momentum.leftVectorDecrease();
    }

    if( keyState.isKeyPressed( Key.UP.getName() ) ) {
      if( ! isFlying ) { jump(); }
    }
  }

  // ###  Private methods  ###

  /**
   * FixedObj�Ƃ̏Փˎ�����
   * @param data �Փˏ��
   */
  private void collisionHandlingForFixedObj( CollisionData data ) {
    // �I�u�W�F�N�g��TOP�ɏՓ˂��� ���� �W�����v��(�㏸��)
    if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {

      ((FixedObj) data.getSubject()).bottomAction();

      vectorY = - vectorY / 3; // �����Ԃ��蒵�˕Ԃ�
    }
  }

  /**
   * MoveObj�Ƃ̏Փˎ�����
   * @param data �Փˏ��
   */
  private void collisionHandlingForMoveObj( CollisionData data ) {
    if( ! (data.getSubject() instanceof Enemy1) ) {
      return;
    }

    if( data.getSide() == Side.BOTTOM ) {
      // ���񂾂�G��|��
      if( keyState.isKeyPressed( Key.UP.getName() ) ) {
        vectorY = - verticalLeap - vectorY / 3;
      } else {
        vectorY = - vectorY / 3;
      }

      data.getSubject().destructor();
    } else {
      // TODO: GameOver���W�b�N����������
      destructor();
    }
  }


  /**
   * ���~�b�g�O�Ɉړ��ł��Ȃ��悤�ɂ���
   */
  private void positionWithinLimit() {
    if( positionX < Map.getLeftLimit() ) {
      positionX = Map.getLeftLimit();
    } else if( (positionX + width) > Map.getRightLimit() ) {
      positionX = Map.getRightLimit() - width;
    }
  }
}

