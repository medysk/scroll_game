package game.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
 * isFlying = ! cd.onFixedObj();
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
   * 衝突情報を格納したリストを巡回し、コールバック関数の
   * 引数に衝突情報を渡す
   * @param cons コールバック関数
   */
  public void forEach( Consumer<CollisionData> cons ) {
    if( collisionDataList.isEmpty() ) { return; }
    collisionDataList.forEach( data -> {
      cons.accept( data );
    } );
  }

  /**
   * execute()を実行した時点で衝突を起こしているかどうか
   * @return 衝突を起こしていたら true
   */
  public boolean isCollided() {
    return ! collisionDataList.isEmpty();
  }

  /**
   * execute()を実行した時点で、キャラクターが" FixedObj に乗っているか
   * @return FixedObj に乗っていれば true
   */
  public boolean onFixedObj() {
    return findCollisionData( data -> {
      return data.getSubject() instanceof FixedObj &&
          data.getSide() == Side.BOTTOM;
    });
  }

  /**
   * execute()を実行した時点で、キャラクターが" Ground に乗っているか
   * @return Ground に乗っていれば true
   */
  public boolean onGround() {
    return findCollisionData( data -> {
      return data.getSubject() instanceof Ground &&
          data.getSide() == Side.BOTTOM;
    });
  }

  /**
   * execute()を実行した時点で、キャラクターが" Uphill と衝突を起こしているか
   * @return 衝突を起こしていれば true
   */
  public boolean onUphill() {
    return findCollisionData( data -> data.getSubject() instanceof Uphill );
  }

  /**
   * execute()を実行した時点で、キャラクターが" Downhill と衝突を起こしているか
   * @return 衝突を起こしていれば true
   */
  public boolean onDownhill() {
    return findCollisionData( data -> data.getSubject() instanceof Downhill );
  }

  // public (enum Side) collidingWith~~

  // ###  Private Methods  ###

  /**
   * 衝突リストを巡回し、衝突データをコールバック関数に渡す
   * コールバック関数は、衝突データを受け取り条件を指定する(返り値はboolean型)
   * @param predicate boolean型を返すコールバック関数
   * @return コールバック関数が一度でも true を返すと true
   */
  private boolean findCollisionData( Predicate<CollisionData> predicate ) {
    // オブジェクトが衝突を起こしていなければ false
    if( ! isCollided() ) { return false; }

    for( CollisionData data : collisionDataList ) {
      if( predicate.test( data ) ) { return true; }
    }
    return false;
  }
}
