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
 * オブジェクト同士の衝突判定用クラス
 * 実行はMoveObjのインスタンスをexecuteメソッドに渡すことで
 * そのオブジェクトと衝突しているオブジェクトがあるか判定する
 *
 * 使用例
 * // 判定をしたいオブジェクトから呼び出して利用する
 * CollisionDetection cd = new CollisionDetection( this )
 * cd.execute(); // 衝突判定
 * isFlying = ! cd.onGround();
 *
 * @author medysk
 *
 */
public class CollisionDetection {
  // 衝突が起こった際に、衝突状況を格納する
  private List<CollisionData> collisionDataList;
  // 衝突判定を行う対象のオブジェクト
  private MoveObj target;
  // 対象のオブジェクトと衝突しているかを判定したい被対象のオブジェクト群
  private static final HashMap<String,Obj> objs = Obj.getInstances();


  /**
   * @param obj 衝突判定を行いたいオブジェクト
   */
  public CollisionDetection(MoveObj target) {
    this.target = target;
  }

  /**
   * 衝突判定を行うメソッド
   */
  public void execute() {
    // (仮の)衝突が起こったオブジェクトのIDを入れる
    List<String> preCollisionIds = new ArrayList<>();
    collisionDataList = new ArrayList<>();  // 初期化

    objs.forEach( (objId, subject) -> {
      // 同じオブジェクトの場合、次のオブジェクトへ
      if( target == subject ) { return; }
      // 不可視オブジェクトの場合、次のオブジェクトへ
      if( subject instanceof FixedObj && ! ((FixedObj) subject).isVisivility() ) {
        return;
      }

      // 衝突していた場合、仮の衝突リストに入れる
      if( CollisionInfoAnalyzer.isCollided(target, subject) ) {
        preCollisionIds.add( subject.getObjId() );
      }
    });
    // 衝突が起こっていなかったら処理を戻す
    if( preCollisionIds.isEmpty() ) { return; }

    // 最初の衝突(サイド毎)が起こった位置を取得
    collisionDataList = CollisionInfoAnalyzer.
        createCollisionData(target, preCollisionIds);
  }

  /**
   * 衝突情報を格納したリストを巡回する
   * @param cons コールバック関数
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
    // オブジェクトが衝突を起こしていなければ false
    if( ! isCollided() ) { return false; }

    for( CollisionData data : collisionDataList ) {
      if( predicate.test( data ) ) { return true; }
    }
    return false;
  }
}
