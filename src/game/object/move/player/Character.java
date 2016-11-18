package game.object.move.player;

import java.awt.Color;
import java.awt.Graphics;

import config.GameData;
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
 * @author medysk
 * 自キャラクター
 */
public class Character extends MoveObj {

  private KeyState keyState; // キーの状態(押されているかどうか)
  private Momentum momentum; // オブジェクトの勢いを調整するクラス

  public Character( int positionX, int positionY ) {
    super( positionX, positionY );
    height = GameData.CHARACTER_HEIGHT;
    width = GameData.CHARACTER_WIDTH;
    minSpeed = GameData.CHARACTER_MIN_SPEED;
    maxSpeed = GameData.CHARACTER_MAX_SPEED;
    fallVelocity = GameData.CHARACTER_FALL_VELOCITY;
    maxFallVelocity = GameData.CHARACTER_MAX_FALL_VELOCITY;
    verticalLeap = GameData.CHARACTER_VERTICAL_LEAP;
    momentum = new Momentum( this ); // Momentumは破壊的(副作用)な操作を行う
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
    g.setColor( new Color(GameData.CHARACTER_COLOR) );
    g.fillRect( positionX, positionY, width, height );
  }

  // ###  Protected methods  ###

  /* (非 Javadoc)
   * @see game.object.MoveObj#action()
   */
  @Override
  protected void action() {
    // キーボードの→が押された
    if( keyState.isKeyPressed( Key.RIGHT.getName() ) ) {
      momentum.rightVectorIncrease();
    } else if( isRightMove() ) {
      momentum.rightVectorDecrease();
    }
    // キーボードの←が押された
    if( keyState.isKeyPressed( Key.LEFT.getName() ) ) {
      momentum.leftVectorIncrease();
    } else if( isLeftMove() ) {
      momentum.leftVectorDecrease();
    }
    // キーボードの↑が押された
    if( keyState.isKeyPressed( Key.UP.getName() ) ) {
      if( ! isFlying ) { jump(); }
    }
  }

  @Override
  public void destructor() {
    // echoのX座標を割り出す
    int x;
    if( positionX < GameData.PANEL_HALF_WIDTH ) {
      x = positionX;
    } else if( positionX > Map.getRightLimit() - GameData.PANEL_HALF_WIDTH ) {
      int clearance = Map.getRightLimit() - positionX;
      x = GameData.PANEL_WIDTH - clearance;
    } else {
      x = GameData.PANEL_HALF_WIDTH;
    }

    Stage.echo("下手すぎる", x - 50, positionY -30, 1, 800);
    super.destructor();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Character obj = (Character)super.clone();
    obj.setCollisionManager(obj);
    obj.setMomentum(obj);
    obj.setKeyState(Stage.getKeyState());
    return obj;
  }

  public void setMomentum(Character character) {
    momentum = new Momentum(character);
  }

  public void setKeyState(KeyState keyState) {
    this.keyState = keyState;
  }

  // ###  Private methods  ###

  /**
   * FixedObjとの衝突時処理
   * @param data 衝突情報
   */
  private void collisionHandlingForFixedObj( CollisionData data ) {
    // オブジェクトのTOPに衝突した かつ ジャンプ中(上昇中)
    if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {

      ((FixedObj) data.getSubject()).bottomEvent();

      vectorY = - vectorY / 3; // 頭がぶつかり跳ね返る
    }

    // クリアフラッグと衝突したらステージクリア
    if( data.getSubject() instanceof Flag ) {
      ((FixedObj) data.getSubject()).event();
    }
  }

  /**
   * MoveObjとの衝突時処理
   * @param data 衝突情報
   */
  private void collisionHandlingForMoveObj( CollisionData data ) {
    if( data.getSide() == Side.BOTTOM ) {
      // 踏んだら敵を倒す
      if( keyState.isKeyPressed( Key.UP.getName() ) ) {
        vectorY = - verticalLeap - vectorY / 3;
      } else {
        vectorY = - vectorY / 3;
      }
      data.getSubject().destructor();
    } else {
      // TODO: キャラクター爆破グラフィック
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

