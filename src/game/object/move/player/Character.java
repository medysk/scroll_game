package game.object.move.player;

import java.awt.Color;
import java.awt.Graphics;

import game.FieldPanel;
import game.object.MoveObj;
import game.object.fixed.Ground;
import game.system.CollisionDetection;
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
  private CollisionDetection cd;    // 衝突判定用クラス

  public Character( int positionX, int positionY, KeyState keyState ) {
    super( positionX, positionY );
    this.keyState = keyState;
    // TODO: 設定ファイルから読み込む
    height = 60;
    width = 30;
    minSpeed = 1;
    maxSpeed = 10;
    fallVelocity = 1;
    maxFallVelocity = height - 1; //
    verticalLeap = 20;
    cd = new CollisionDetection(this);
    momentum = new Momentum( this ); // Momentumは破壊的な操作を行う
  }

  /* (非 Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void execute() {
    super.execute();
    action();
    super.move();
    cd.execute();
    isFlying = ! cd.onFixedObj();
    // 位置補正
    positionCorrection();
    positionWithinLimit();
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
   * キャラクターの動作
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

  /* (非 Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void draw( Graphics g ) {
    g.setColor( Color.BLUE );
    g.fillRect( positionX, positionY, width, height );
  }

  /**
   * オブジェクトが重ならないようにする
   */
  private void positionCorrection() {
    cd.forEach( obj -> {
//      if( obj.getSubjectObj() instanceof Ground ) { return; }
      switch (obj.getSide()) {
      case TOP:
        positionY = obj.getCollisionPositionY();
        break;
      case LEFT:
        positionX = obj.getCollisionPositionX();
        break;
      case BOTTOM:
        positionY = obj.getCollisionPositionY();
        break;
      case RIGHT:
        positionX = obj.getCollisionPositionX();
        break;
      }
    });
  }
}

