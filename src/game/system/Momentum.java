package game.system;

import game.object.MoveObj;

/**
 * @author medysk
 * MoveObjの動きの勢いを調整し、オブジェクトの移動距離を破壊的(副作用)に変更する
 */
public class Momentum {
  private MoveObj obj;
  private int minSpeed;
  private int maxSpeed;
  private int interval = 5;

  public Momentum( MoveObj obj ) {
    this.obj = obj;
    minSpeed = obj.getMinSpeed();
    maxSpeed = obj.getMaxSpeed();
  }

  /**
  * MoveObjの右方向の増加を調整する破壊的なメソッド
  */
  public void rightVectorIncrease() {
    if( obj.isLeftMove() ) {
      // オブジェクトが左方向に進んでいる場合、ストップする
      obj.setVectorX( 0 );
    } else if( obj.isRightMove() ) {
      // オブジェクトが右方向に進んでいる場合、maxSpeedまで段階的に加速
      obj.setVectorX( acceleratedCalc() );
    } else {
      obj.setVectorX( minSpeed );
    }
  }

  /**
  * MoveObjの左方向の増加を調整する破壊的なメソッド
  */
  public void leftVectorIncrease() {
    if( obj.isRightMove() ) {
      // オブジェクトが右方向に進んでいる場合、ストップする
      obj.setVectorX( 0 );
    } else if( obj.isLeftMove() ) {
      obj.setVectorX( - acceleratedCalc() ); // マイナス
    } else {
      obj.setVectorX( - minSpeed ); // マイナス
    }
  }

  /**
  * MoveObjの左方向の減少を調整する破壊的なメソッド
  */
  public void rightVectorDecrease() {
    obj.setVectorX( decelerationCalc() ); // マイナス
  }

  /**
  * MoveObjの左方向の減少を調整する破壊的なメソッド
  */
  public void leftVectorDecrease() {
    obj.setVectorX( - decelerationCalc() );
  }

  // ***  Private Methods ###

  /**
  * 加速中の移動距離を返す
  * @return 移動距離の"絶対値"を返す
  */
  private int acceleratedCalc() {
    // オブジェクトが任意の方向に継続的に進んでいる場合、maxSpeedまで段階的に加速
    // 処理は runFrame回に一度実行する
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
    // オブジェクトが進んでいる時、キーを離すと停止まで段階的に減速させる
    // 処理は runFrame回に一度実行する
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
