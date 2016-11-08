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
 * 自キャラクター
 * @author medysk
 *
 */
public class Character extends MoveObj {
  private KeyState keyState; // キーの状態(押されているかどうか)
  private Momentum momentum; // オブジェクトの勢いを調整するクラス

  public Character( int positionX, int positionY, KeyState keyState ) {
    super( positionX, positionY );
    this.keyState = keyState;
    // TODO: 設定ファイルから読み込む
    height = 50;
    width = 28;
    minSpeed = 1;
    maxSpeed = 10;
    fallVelocity = 1;
    maxFallVelocity = height - 1; //
    verticalLeap = 20;
    momentum = new Momentum( this ); // Momentumは破壊的な操作を行う
  }

  /* (非 Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void execute() {
    super.execute();

    // マップの両端から出ないようにする
    positionWithinLimit();

    // 衝突処理
    collisionHandling( data -> {
      // 衝突したオブジェクトがFixedObjかMoveObjかで、処理を分ける
      if( data.getSubject() instanceof FixedObj ) {
        collisionHandlingForFixedObj(data);
      } else if( data.getSubject() instanceof MoveObj ) {
        collisionHandlingForMoveObj(data);
      }
    });

    // 位置補正後に前回位置を更新
    updatePrePosition();

  }

  /* (非 Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void draw( Graphics g ) {
    g.setColor( Color.BLUE );
    g.fillRect( positionX, positionY, width, height );
  }

  // ###  Protected methods  ###

  /* (非 Javadoc)
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
   * FixedObjとの衝突時処理
   * @param data 衝突情報
   */
  private void collisionHandlingForFixedObj( CollisionData data ) {
    // オブジェクトのTOPに衝突した かつ ジャンプ中(上昇中)
    if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {

      ((FixedObj) data.getSubject()).bottomAction();

      vectorY = - vectorY / 3; // 頭がぶつかり跳ね返る
    }
  }

  /**
   * MoveObjとの衝突時処理
   * @param data 衝突情報
   */
  private void collisionHandlingForMoveObj( CollisionData data ) {
    if( ! (data.getSubject() instanceof Enemy1) ) {
      return;
    }

    if( data.getSide() == Side.BOTTOM ) {
      // 踏んだら敵を倒す
      if( keyState.isKeyPressed( Key.UP.getName() ) ) {
        vectorY = - verticalLeap - vectorY / 3;
      } else {
        vectorY = - vectorY / 3;
      }

      data.getSubject().destructor();
    } else {
      // TODO: GameOverロジックを実装する
      destructor();
    }
  }


  /**
   * リミット外に移動できないようにする
   */
  private void positionWithinLimit() {
    if( positionX < Map.getLeftLimit() ) {
      positionX = Map.getLeftLimit();
    } else if( (positionX + width) > Map.getRightLimit() ) {
      positionX = Map.getRightLimit() - width;
    }
  }
}

