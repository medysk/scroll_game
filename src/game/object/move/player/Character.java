package game.object.move.player;

import java.awt.Color;
import java.awt.Graphics;

import game.StagePanel;
import game.Stage;
import game.object.FixedObj;
import game.object.MoveObj;
import game.object.fixed.Flag;
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
    // �L�[�{�[�h�́��������ꂽ
    if( keyState.isKeyPressed( Key.RIGHT.getName() ) ) {
      momentum.rightVectorIncrease();
    } else if( isRightMove() ) {
      momentum.rightVectorDecrease();
    }
    // �L�[�{�[�h�́��������ꂽ
    if( keyState.isKeyPressed( Key.LEFT.getName() ) ) {
      momentum.leftVectorIncrease();
    } else if( isLeftMove() ) {
      momentum.leftVectorDecrease();
    }
    // �L�[�{�[�h�́��������ꂽ
    if( keyState.isKeyPressed( Key.UP.getName() ) ) {
      if( ! isFlying ) { jump(); }
    }
  }

  @Override
  public void destructor() {
    // echo��X���W������o��
    int x;
    if( positionX < StagePanel.WIDTH  / 2 ) {
      x = positionX;
    } else if( positionX > Map.getRightLimit() - StagePanel.WIDTH / 2  ) {
      int clearance = Map.getRightLimit() - positionX;
      x = StagePanel.WIDTH - clearance;
    } else {
      x = StagePanel.WIDTH / 2;
    }

    Stage.echo("���肷����", x - 50, positionY -30, 1, 800);
    super.destructor();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Character obj = (Character)super.clone();
    obj.setCollisionManager(obj);
    obj.setMomentum(obj);
    return obj;
  }

  public void setMomentum(Character character) {
    momentum = new Momentum(character);
  }

  // ###  Private methods  ###

  /**
   * FixedObj�Ƃ̏Փˎ�����
   * @param data �Փˏ��
   */
  private void collisionHandlingForFixedObj( CollisionData data ) {
    // �I�u�W�F�N�g��TOP�ɏՓ˂��� ���� �W�����v��(�㏸��)
    if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {

      ((FixedObj) data.getSubject()).bottomEvent();

      vectorY = - vectorY / 3; // �����Ԃ��蒵�˕Ԃ�
    }

    // �N���A�t���b�O�ƏՓ˂�����X�e�[�W�N���A
    if( data.getSubject() instanceof Flag ) {
      ((FixedObj) data.getSubject()).event();
    }
  }

  /**
   * MoveObj�Ƃ̏Փˎ�����
   * @param data �Փˏ��
   */
  private void collisionHandlingForMoveObj( CollisionData data ) {
    if( data.getSide() == Side.BOTTOM ) {
      // ���񂾂�G��|��
      if( keyState.isKeyPressed( Key.UP.getName() ) ) {
        vectorY = - verticalLeap - vectorY / 3;
      } else {
        vectorY = - vectorY / 3;
      }
      data.getSubject().destructor();
    } else {
      // TODO: �L�����N�^�[���j�O���t�B�b�N
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

