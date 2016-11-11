package game.system;

import game.object.MoveObj;

/**
 * MoveObj�̓����̐����𒲐����A�I�u�W�F�N�g�̈ړ�������j��I�ɕύX����
 * @author medysk
 *
 */
public class Momentum {
  private MoveObj obj;
  private int minSpeed;
  private int maxSpeed;
  private int interval = 5; // TODO: �ݒ�t�@�C������ǂݍ���

  public Momentum( MoveObj obj ) {
    this.obj = obj;
    minSpeed = obj.getMinSpeed();
    maxSpeed = obj.getMaxSpeed();
  }

  /**
  * MoveObj�̉E�����̑����𒲐�����j��I�ȃ��\�b�h
  */
  public void rightVectorIncrease() {
    if( obj.isLeftMove() ) {
      // �I�u�W�F�N�g���������ɐi��ł���ꍇ�A�X�g�b�v����
      obj.setVectorX( 0 );
    } else if( obj.isRightMove() ) {
      // �I�u�W�F�N�g���E�����ɐi��ł���ꍇ�AmaxSpeed�܂Œi�K�I�ɉ���
      obj.setVectorX( acceleratedCalc() );
    } else {
      obj.setVectorX( minSpeed );
    }
  }

  /**
  * MoveObj�̍������̑����𒲐�����j��I�ȃ��\�b�h
  */
  public void leftVectorIncrease() {
    if( obj.isRightMove() ) {
      // �I�u�W�F�N�g���E�����ɐi��ł���ꍇ�A�X�g�b�v����
      obj.setVectorX( 0 );
    } else if( obj.isLeftMove() ) {
      obj.setVectorX( - acceleratedCalc() ); // �}�C�i�X
    } else {
      obj.setVectorX( - minSpeed ); // �}�C�i�X
    }
  }

  /**
  * MoveObj�̍������̌����𒲐�����j��I�ȃ��\�b�h
  */
  public void rightVectorDecrease() {
    obj.setVectorX( decelerationCalc() ); // �}�C�i�X
  }

  /**
  * MoveObj�̍������̌����𒲐�����j��I�ȃ��\�b�h
  */
  public void leftVectorDecrease() {
    obj.setVectorX( - decelerationCalc() );
  }

  // ***  Private Methods ###

  /**
  * �������̈ړ�������Ԃ�
  * @return �ړ�������"��Βl"��Ԃ�
  */
  private int acceleratedCalc() {
    // �I�u�W�F�N�g���C�ӂ̕����Ɍp���I�ɐi��ł���ꍇ�AmaxSpeed�܂Œi�K�I�ɉ���
    // ������ runFrame��Ɉ�x���s����
    if( FrameManager.isActionFrame(interval) ) {
      int acceleration = (maxSpeed - minSpeed) / 10;
      if( acceleration == 0 ) { acceleration = 1; }
      int distance = acceleration + Math.abs( obj.getVectorX() );
      return distance > maxSpeed ? maxSpeed : distance;
    } else {
      return Math.abs( obj.getVectorX() );
    }
  }

  private int decelerationCalc() {
    // �I�u�W�F�N�g���i��ł��鎞�A�L�[�𗣂��ƒ�~�܂Œi�K�I�Ɍ���������
    // ������ runFrame��Ɉ�x���s����
    if( FrameManager.isActionFrame(interval) ) {
      int deceleration = (maxSpeed - 0) / 3;
      if( deceleration == 0 ) { deceleration = 1; }
      int distance = Math.abs( obj.getVectorX() ) - deceleration;
      return distance < minSpeed ? 0 : distance;
    } else {
      return Math.abs( obj.getVectorX() );
    }
  }
}
