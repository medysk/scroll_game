package game.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import game.FieldPanel;
import game.object.FixedObj;
import game.object.MoveObj;
import game.object.Obj;
import game.object.fixed.Downhill;
import game.object.fixed.Ground;
import game.object.fixed.Uphill;

/**
 * �I�u�W�F�N�g���m�̏Փ˔���p�N���X
 * ���s��MoveObj�̃C���X�^���X��execute���\�b�h�ɓn�����Ƃ�
 * ���̃I�u�W�F�N�g�ƏՓ˂��Ă���I�u�W�F�N�g�����邩���肷��
 *
 * �g�p��
 * // ������������I�u�W�F�N�g����Ăяo���ė��p����
 * CollisionDetection cd = new CollisionDetection( this )
 * cd.execute(); // �Փ˔���
 * isFlying = ! cd.onGround();
 *
 * @author medysk
 *
 */
public class CollisionDetection {
  // �Փ˂��N�������ۂɁA�Փˏ󋵂��i�[����
  private List<CollisionData> collisionDataList;
  // �Փ˔�����s���Ώۂ̃I�u�W�F�N�g
  private MoveObj target;
  // �Ώۂ̃I�u�W�F�N�g�ƏՓ˂��Ă��邩�𔻒肵������Ώۂ̃I�u�W�F�N�g�Q
  private static final HashMap<String,Obj> objs = Obj.getInstances();


  /**
   * @param obj �Փ˔�����s�������I�u�W�F�N�g
   */
  public CollisionDetection(MoveObj target) {
    this.target = target;
  }

  /**
   * �Փ˔�����s�����\�b�h
   */
  public void execute() {
    // (����)�Փ˂��N�������I�u�W�F�N�g��ID������
    List<String> preCollisionIds = new ArrayList<>();
    collisionDataList = new ArrayList<>();  // ������

    objs.forEach( (objId, subject) -> {
      // �����I�u�W�F�N�g�̏ꍇ�A���̃I�u�W�F�N�g��
      if( target == subject ) { return; }
      // �s���I�u�W�F�N�g�̏ꍇ�A���̃I�u�W�F�N�g��
      if( subject instanceof FixedObj && ! ((FixedObj) subject).isVisivility() ) {
        return;
      }

      // �Փ˂��Ă����ꍇ�A���̏Փ˃��X�g�ɓ����
      if( CollisionInfoAnalyzer.isCollided(target, subject) ) {
        preCollisionIds.add( subject.getObjId() );
      }
    });
    // �Փ˂��N�����Ă��Ȃ������珈����߂�
    if( preCollisionIds.isEmpty() ) { return; }

    // �ŏ��̏Փ�(�T�C�h��)���N�������ʒu���擾
    collisionDataList = CollisionInfoAnalyzer.
        createCollisionData(target, preCollisionIds);
  }

  /**
   * �Փˏ����i�[�������X�g�����񂷂�
   * @param cons �R�[���o�b�N�֐�
   */
  public void forEach( Consumer<CollisionData> cons ) {
    if( collisionDataList.isEmpty() ) { return; }
    collisionDataList.forEach( data -> {
      cons.accept( data );
    } );
  }

  public boolean isCollided() {
    return ! collisionDataList.isEmpty();
  }

  public boolean onFixedObj() {
    return determinationProc( data -> {
      return data.getSubject() instanceof FixedObj &&
          data.getSide() == Side.BOTTOM;
    });
  }

  public boolean onGround() {
    return determinationProc( data -> {
      return data.getSubject() instanceof Ground &&
          data.getSide() == Side.BOTTOM;
    });
  }

  public boolean onUphill() {
    return determinationProc( data -> data.getSubject() instanceof Uphill );
  }

  public boolean onDownhill() {
    return determinationProc( data -> data.getSubject() instanceof Downhill );
  }

  // public (enum Side) collidingWith~~

  // ###  Private Methods  ###

  private boolean determinationProc( Predicate<CollisionData> predicate ) {
    // �I�u�W�F�N�g���Փ˂��N�����Ă��Ȃ���� false
    if( ! isCollided() ) { return false; }

    for( CollisionData data : collisionDataList ) {
      if( predicate.test( data ) ) { return true; }
    }
    return false;
  }
}
