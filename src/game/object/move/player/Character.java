package game.object.move.player;

import java.awt.Color;
import java.awt.Graphics;

import game.object.fixed.Block;
import game.object.FixedObj;
import game.object.MoveObj;
import game.system.CollisionData;
import game.system.CollisionDetection;
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
  private CollisionDetection cd;    // �Փ˔���p�N���X

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
    cd = new CollisionDetection(this);
    momentum = new Momentum( this ); // Momentum�͔j��I�ȑ�����s��
  }

  /* (�� Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void execute() {
    super.execute();
    action();
    super.move();
    cd.execute();
    isFlying = ! cd.onFixedObj();

    // �Փˏ���
    collisionHandling();

    // �ʒu�␳
    positionCorrection();
    positionWithinLimit();

    updatePrePosition();
  }

  private void collisionHandling() {
    cd.forEach( data -> {
      // �Փ˂����I�u�W�F�N�g��FixedObj��MoveObj���ŁA�����𕪂���
      if( data.getSubject() instanceof FixedObj ) {
        collisionHandlingForFixedObj( data );
      } else if( data.getSubject() instanceof MoveObj ) {
        collisionHandlingForMoveObj( data );
      }
    } );
  }

  private void collisionHandlingForFixedObj( CollisionData data ) {
    // �I�u�W�F�N�g��TOP�ɏՓ˂��� ���� �W�����v��(�㏸��)
    if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {

      ((FixedObj) data.getSubject()).bottomAction();

      vectorY = - vectorY / 3; // �����Ԃ��蒵�˕Ԃ�
    }
  }

  private void collisionHandlingForMoveObj( CollisionData data ) {

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

//    if( isFlying ) { return; }
//
//    int leftSoleLimit;
//    int centerSoleLimit;
//    int rightSoleLimit;
//    try {
//      leftSoleLimit = Map.getLowerLimit(positionX);
//    } catch( NullPointerException e ) {
//      leftSoleLimit = FieldPanel.HEIGHT + height;
//    }
//    try {
//      centerSoleLimit = Map.getLowerLimit(positionX + width / 2);
//    } catch( NullPointerException e ) {
//      centerSoleLimit = FieldPanel.HEIGHT + height;
//    }
//    try {
//      rightSoleLimit = Map.getLowerLimit(positionX + width);
//    } catch ( NullPointerException e) {
//      rightSoleLimit = FieldPanel.HEIGHT + height;
//    }
//
//    int sole = positionY + height;
//    if( cd.onUphill() || cd.onDownhill() ) {
//      if( sole > centerSoleLimit ) {
//        positionY = centerSoleLimit - height;
//      }
//      return;
//    }
//    if( sole > leftSoleLimit ) {
//      positionY = leftSoleLimit - height;
//    }
//    if( sole > rightSoleLimit ) {
//      positionY = rightSoleLimit - height;
//    }
  }

  /**
   * �L�����N�^�[�̓���
   */
  public void action() {
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

  /* (�� Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void draw( Graphics g ) {
    g.setColor( Color.BLUE );
    g.fillRect( positionX, positionY, width, height );
  }

  /**
   * �I�u�W�F�N�g���d�Ȃ�Ȃ��悤�ɂ���
   */
  private void positionCorrection() {
    cd.forEach( data -> {
      // �s���I�u�W�F�N�g�̏ꍇ�A����data��
      if( data.getSubject() instanceof FixedObj &&
          ! ((FixedObj) data.getSubject()).isVisivility() ) {
        return;
      }

      switch (data.getSide()) {
      case TOP:
        positionY = data.getCollisionPositionY();
        break;
      case LEFT:
        positionX = data.getCollisionPositionX();
        break;
      case BOTTOM:
        positionY = data.getCollisionPositionY();
        break;
      case RIGHT:
        positionX = data.getCollisionPositionX();
        break;
      }
    });
  }
}

