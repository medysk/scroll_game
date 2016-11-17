package game.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
 * CollisionDetectionから利用する衝突の解析を行うクラス
 * @author medysk
 *
 */
public class CollisionInfoAnalyzer {
  /**
   * 衝突判定の処理
   *
   * @param target
   * @param subject
   */
  public static boolean isCollided( Obj target, Obj subject ) {
    // 衝突していれば true
    return target.upperLeft().get("x")  < subject.upperRight().get("x") &&
           target.lowerRight().get("x") > subject.lowerLeft().get("x")  &&
           target.upperLeft().get("y")  < subject.lowerLeft().get("y")  &&
           target.lowerRight().get("y") > subject.upperRight().get("y");
  }

  /**
   * 衝突情報のリストをもとに詳細な衝突情報を取得する
   * @param target 対象オブジェクト
   * @param ids 衝突を起こした被対象オブジェクトIDのリスト
   * @return CollisionDataのリストを返す
   */
  public static List<CollisionData> createCollisionData(MoveObj target, List<String> ids) {
    // Trajectoryに使用する分母
    int denominator = Math.abs(target.getVectorX()) > Math.abs(target.getVectorY()) ?
        Math.abs(target.getVectorX()) : Math.abs(target.getVectorY());

    int targetMoveCount = 0;
    Obj subject;
    ConcurrentHashMap<String,Obj> objs = Obj.getInstances();
    HashMap<Side,CollisionData> collisionMap = new HashMap<>();
    Trajectory tTrajectory = new Trajectory(target, denominator);
    List<CollisionData> collisionDataList = new ArrayList<>();
    Ground hill = null;

    // 対象のオブジェクトの前回位置から移動先までのループ
    while( targetMoveCount <= denominator ) {
      HashSet<String> completionIds = new HashSet<>();
      targetMoveCount++;
      tTrajectory.increase();

      // 衝突リストのオブジェクトを巡回するループ
      for( String id : ids ) {
        // MoveObjの場合targetと同じ数だけ位置を進める
        if( objs.get(id) instanceof MoveObj ) {
          subject = new Trajectory((MoveObj) objs.get(id), denominator);

          for(int i=0; i<targetMoveCount; i++) {
            ((Trajectory) subject).increase();
          }
        } else {
          subject = objs.get(id);
        }
        // 衝突を起こしていたら
        if( isCollided( tTrajectory, subject ) ) {
          // 坂は別に処理する
          if( objs.get(id) instanceof Uphill || objs.get(id) instanceof Downhill ) {
            // 衝突したオブジェクトのIDを入れる(idsから削除するときに使う)
            completionIds.add(subject.getObjId());
            hill = (Ground)subject;
            continue;
          }

          // 衝突したオブジェクトのIDを入れる(idsから削除するときに使う)
          completionIds.add(subject.getObjId());
          // 衝突位置を取得
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
      // 衝突済みのIDを削除
      completionIds.forEach( id -> ids.remove(ids.indexOf(id)) );
    }

    // 最終的な位置が坂だったら collisionMap の BOTTOM を上書き
    if( hill != null ) {
      CollisionData data = collisionForHill(tTrajectory, hill);
      if( data != null ) {
        collisionMap.put(Side.BOTTOM, data);

        // 坂の登頂付近で壁になっていないFlatに衝突しないようにする
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
   * 坂でGroundに衝突しているか判定する
   * @param collisionMap
   * @param side
   * @param lower
   * @return
   */
  private static Side collisionOnGroundAtHill(
      HashMap<Side,CollisionData> collisionMap, Side side, Ground hill) {
    // サイドが衝突している かつ Ground と衝突している
    if( collisionMap.containsKey(side) &&
        collisionMap.get(side).getSubject() instanceof Ground ) {

      int positionY;
      if( collisionMap.get(side).getSubject() instanceof Flat ) {
        positionY = collisionMap.get(side).getSubject().getPositionY();
      } else {
        positionY = collisionMap.get(side).getSubject().lowerLeft().get("y");
      }

      if( positionY >= hill.getPositionY() ) {
        return side;
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
   * 衝突した側面を調べる
   * @param target 対象オブジェクト
   * @param subject 被対象オブジェクト
   * @return Side型(enum)で返す 判定できない場合は null を返す
   */
  private static Side checkSide( Obj target, Obj subject ) {
    int xLength;
    int yLength;
    boolean isRight;
    boolean isTop;

    // 衝突した範囲を取得
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

    // 衝突した範囲が狭い場合に無効にする
    if( subject instanceof MoveObj ) {
      if( xLength <= 1 && yLength <= 1 ) { return null; }
    } else if( subject instanceof FixedObj ) {
      if( xLength <= 7 && yLength <= 1 ) { return null; }
    }

    // 衝突したサイドを返す
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
