package game.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import game.StagePanel;
import game.object.FixedObj;
import game.object.MoveObj;
import game.object.Obj;
import game.object.Trajectory;
import game.object.fixed.Downhill;
import game.object.fixed.Flat;
import game.object.fixed.Ground;
import game.object.fixed.Uphill;

/**
 * CollisionDetection���痘�p����Փ˂̉�͂��s���N���X
 * @author medysk
 *
 */
public class CollisionInfoAnalyzer {
  /**
   * �Փ˔���̏���
   *
   * @param target
   * @param subject
   */
  public static boolean isCollided( Obj target, Obj subject ) {
    // �Փ˂��Ă���� true
    return target.upperLeft().get("x")  < subject.upperRight().get("x") &&
           target.lowerRight().get("x") > subject.lowerLeft().get("x")  &&
           target.upperLeft().get("y")  < subject.lowerLeft().get("y")  &&
           target.lowerRight().get("y") > subject.upperRight().get("y");
  }

  /**
   * �Փˏ��̃��X�g�����ƂɏڍׂȏՓˏ����擾����
   * @param target �ΏۃI�u�W�F�N�g
   * @param ids �Փ˂��N��������ΏۃI�u�W�F�N�gID�̃��X�g
   * @return CollisionData�̃��X�g��Ԃ�
   */
  public static List<CollisionData> createCollisionData(MoveObj target, List<String> ids) {
    // Trajectory�Ɏg�p���镪��
    int denominator = Math.abs(target.getVectorX()) > Math.abs(target.getVectorY()) ?
        Math.abs(target.getVectorX()) : Math.abs(target.getVectorY());

    int targetMoveCount = 0;
    Obj subject;
    HashMap<String,Obj> objs = Obj.getInstances();
    HashMap<Side,CollisionData> collisionMap = new HashMap<>();
    Trajectory tTrajectory = new Trajectory(target, denominator);
    List<CollisionData> collisionDataList = new ArrayList<>();
    Ground hill = null;

    // �Ώۂ̃I�u�W�F�N�g�̑O��ʒu����ړ���܂ł̃��[�v
    while( targetMoveCount <= denominator ) {
      HashSet<String> completionIds = new HashSet<>();
      targetMoveCount++;
      tTrajectory.increase();

      // �Փ˃��X�g�̃I�u�W�F�N�g�����񂷂郋�[�v
      for( String id : ids ) {
        // MoveObj�̏ꍇtarget�Ɠ����������ʒu��i�߂�
        if( objs.get(id) instanceof MoveObj ) {
          subject = new Trajectory((MoveObj) objs.get(id), denominator);

          for(int i=0; i<targetMoveCount; i++) {
            ((Trajectory) subject).increase();
          }
        } else {
          subject = objs.get(id);
        }
        // �Փ˂��N�����Ă�����
        if( isCollided( tTrajectory, subject ) ) {
          // ��͕ʂɏ�������
          if( objs.get(id) instanceof Uphill || objs.get(id) instanceof Downhill ) {
            // �Փ˂����I�u�W�F�N�g��ID������(ids����폜����Ƃ��Ɏg��)
            completionIds.add(subject.getObjId());
            hill = (Ground)subject;
            continue;
          }

          // �Փ˂����I�u�W�F�N�g��ID������(ids����폜����Ƃ��Ɏg��)
          completionIds.add(subject.getObjId());
          // �Փˈʒu���擾
          Side side = checkSide(tTrajectory, objs.get(id));
          if( side == null ) { continue; }

          if( ! collisionMap.containsKey(side) ) {
            HashMap<String,Integer> position = collisionPosition(tTrajectory, subject, side);
            CollisionData data = new CollisionData(
                target, objs.get(id), side, position.get("x"), position.get("y"));
            collisionMap.put( side, data);
          }
        }
      }
      // �Փˍς݂�ID���폜
      completionIds.forEach( id -> ids.remove(ids.indexOf(id)) );
    }

    // �ŏI�I�Ȉʒu���₾������ collisionMap �� BOTTOM ���㏑��
    if( hill != null ) {
      CollisionData data = collisionForHill(tTrajectory, hill);
      if( data != null ) {
        collisionMap.put(Side.BOTTOM, data);

        // ��̓o���t�߂ŕǂɂȂ��Ă��Ȃ�Flat�ɏՓ˂��Ȃ��悤�ɂ���
        Side course = target.getVectorX() < 0 ? Side.LEFT : Side.RIGHT;
        Side deleteSide = collisionOnGroundAtHill(
            collisionMap, course, (Ground) data.getSubject() );

        if( deleteSide != null ) {
          collisionMap.remove(deleteSide);
        }
      }
    }

    collisionMap.forEach( (k,v) -> collisionDataList.add(v) );
    return collisionDataList;
  }

  /**
   * ���Flat�ɏՓ˂��Ă��邩���ʂ���
   * @param collisionMap
   * @param side
   * @param lower
   * @return
   */
  private static Side collisionOnGroundAtHill(
      HashMap<Side,CollisionData> collisionMap, Side side, Ground hill) {
    if( collisionMap.containsKey(side) ) {
      if( collisionMap.get(side).getSubject() instanceof Flat ) {
        int flatPositionY = collisionMap.get(side).getSubject().upperLeft().get("y");
        if( flatPositionY <= hill.getPositionY() ) {
          return side;
        }
      }
    }
    return null;
  }

  private static CollisionData collisionForHill(Trajectory target, Ground subject) {
    int centerSolePositionX = target.upperLeft().get("x") + target.getWidth() / 2;
    int centerSolePositionY = target.upperLeft().get("y") + target.getHeight();
    int centerSoleLimit;
    try {
      centerSoleLimit = Map.getLowerLimit(centerSolePositionX);
    } catch( NullPointerException e ) {
      centerSoleLimit = StagePanel.HEIGHT + target.getHeight();
    }
    if( centerSolePositionY > centerSoleLimit ) {
      return new CollisionData(target.getMoveObj(), subject, Side.BOTTOM,
          target.upperLeft().get("x"), centerSoleLimit - target.getHeight() );
    }
    return null;
  }

  private static HashMap<String,Integer> collisionPosition(
      Trajectory target, Obj subject, Side side) {
    switch (side) {
    case TOP:
      return new HashMap<String,Integer>() { {
        put( "x", target.upperLeft().get("x") );
        put( "y", subject.lowerLeft().get("y") );
      } };
    case RIGHT:
      return new HashMap<String,Integer>() { {
        put( "x", subject.upperLeft().get("x") - target.getWidth() );
        put( "y", target.upperLeft().get("y") );
      } };
    case BOTTOM:
      return new HashMap<String,Integer>() { {
        put( "x", target.upperLeft().get("x") );
        put( "y", subject.upperLeft().get("y") - target.getHeight() );
      } };
    case LEFT:
      return new HashMap<String,Integer>() { {
        put( "x", subject.upperRight().get("x") );
        put( "y", target.upperLeft().get("y") );
      } };
    }
    return null;
  }

  /**
   * �Փ˂������ʂ𒲂ׂ�
   * @param target �ΏۃI�u�W�F�N�g
   * @param subject ��ΏۃI�u�W�F�N�g
   * @return Side�^(enum)�ŕԂ� ����ł��Ȃ��ꍇ�� null ��Ԃ�
   */
  private static Side checkSide( Obj target, Obj subject ) {
    int xLength;
    int yLength;
    boolean isRight;
    boolean isTop;

    // �Փ˂����͈͂��擾
    if( target.upperLeft().get("x") < subject.upperLeft().get("x") ) {
      isRight = true;
      xLength = target.upperRight().get("x") - subject.upperLeft().get("x");
    } else {
      isRight = false;
      xLength = subject.upperRight().get("x") - target.upperLeft().get("x");
    }

    if( target.lowerLeft().get("y") < subject.lowerLeft().get("y") ) {
      isTop = false;
      yLength = target.lowerLeft().get("y") - subject.upperLeft().get("y");
    } else {
      isTop = true;
      yLength = subject.lowerLeft().get("y") - target.upperLeft().get("y");
    }

    // �Փ˂����͈͂������ꍇ�ɖ����ɂ���
    if( subject instanceof MoveObj ) {
      if( xLength <= 1 && yLength <= 1 ) { return null; }
    } else if( subject instanceof FixedObj ) {
      if( xLength <= 7 && yLength <= 1 ) { return null; }
    }

    // �Փ˂����T�C�h��Ԃ�
    if( isRight ) {
      if( isTop ) {
        return xLength <= yLength ? Side.RIGHT : Side.TOP;
      } else {
        return xLength <= yLength ? Side.RIGHT : Side.BOTTOM;
      }
    } else {
      if( isTop ) {
        return xLength <= yLength ? Side.LEFT : Side.TOP;
      } else {
        return xLength <= yLength ? Side.LEFT : Side.BOTTOM;
      }
    }
  }
}
