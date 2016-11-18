package game.object;

import java.util.function.Consumer;

import config.GameData;
import game.Stage;
import game.system.CollisionData;
import game.system.CollisionManager;

/**
 * @author medysk
 * ゲームに描写するオブジェクトの内
 * 動作のあるオブジェクトのスーパークラス
 */
public abstract class MoveObj extends Obj {

  protected boolean isFlying; // オブジェクトが飛んでいるか？
  protected int minSpeed;     // 移動速度
  protected int currentSpeed; // 現在の速度
  protected int maxSpeed;     // 最大速度( 自オブジェクト以外の要因で超えることがある )
  protected int fallVelocity; // 落下速度
  protected int maxFallVelocity; // 最大落下速度 オブジェクトの height より小さくする
  protected int verticalLeap; // ジャンプ力
  protected int vectorX;      // ベクトル
  protected int vectorY;
  protected int prePositionX; // 前回の位置
  protected int prePositionY;
  protected CollisionManager cm;    // 衝突判定用クラス

  public MoveObj( int positionX, int positionY ) {
    super( positionX, positionY );
    cm = new CollisionManager(this);
    setPrePositionX(positionX);
    setPrePositionY(positionY);
  }

  /**
   * オブジェクトの実行用メソッド
   */
  public void execute() {
    // Characterがゲームから除外された場合、動作を行わない
    if( Obj.getCharacter() == null ) { return; }
    // 画面下に落ちた場合、ゲームから除外する
    if( positionY > GameData.PANEL_HEIGHT ) {
      destructor();
    }
    fall();
    action();
    move();

    cm.execute();                 // 衝突判定
    isFlying = ! cm.onFixedObj(); // 空中判定

    positionCorrection();         // 位置補正
  }

  // ###  Abstract methods  ###

 /**
   * MoveObjの動作を実装する
   */
  abstract protected void action();

  // ###  Instance methods  ###

  /**
   * オブジェクトを移動させるためのメソッド
   */
  public void move() {
    positionX += vectorX;
    positionY += vectorY;
  }

  // オブジェクトの移動確認用メソッド
  public boolean isUpMove()    { return vectorY < 0; }
  public boolean isRightMove() { return vectorX > 0; }
  public boolean isDownMove()  { return vectorY > 0; }
  public boolean isLeftMove()  { return vectorX < 0; }

  // ###  Accessors  ###

  /**
   * @return オブジェクトが飛んでいればtrue
   */
  public boolean isFlying() {
    return isFlying;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    MoveObj obj = (MoveObj)super.clone();
    obj.setCollisionManager(obj);
    return obj;
  }

  // getter
  public int getVectorX() { return vectorX; }
  public int getVectorY() { return vectorY; }
  public int getPrePositionX() { return prePositionX; }
  public int getPrePositionY() { return prePositionY; }
  public int getMinSpeed() { return minSpeed; }
  public int getMaxSpeed() { return maxSpeed; }
  public int getVerticalLeap() { return verticalLeap; }
  public int getFallVelocity() { return fallVelocity; }

  // setter
  public void setPrePositionX(int x) { prePositionX = x; }
  public void setPrePositionY(int y) { prePositionY = y; }
  public void setVectorX(int px) { vectorX = px; }
  public void setVectorY(int px) { vectorY = px; }
  public void setCollisionManager(MoveObj obj) { cm = new CollisionManager(obj); }

  // ###  Protected methods  ###

  /**
  * ジャンプ処理
  */
  protected void jump() {
    vectorY -= verticalLeap + Math.abs( vectorX / 2 );
  }

  /**
   * 前回位置の更新
   */
  protected void updatePrePosition() {
    prePositionX = positionX;
    prePositionY = positionY;
  }

  /**
   * MoveObjのサブクラスから、衝突処理を実装するときに利用する。
   * @param cons 衝突処理のコールバック関数
   */
  protected void collisionHandling(Consumer<CollisionData> cons) {
    cm.forEach( data -> {
      // 自インスタンスとの衝突ではない場合、処理を飛ばす
      if( data.getTarget() != this) {
        return;
      }
      cons.accept( data );
    } );
  }

  // ###  Private methods  ###

  /**
  * 落下処理
  */
  private void fall() {
    if( isFlying ) {
      vectorY += fallVelocity;
      if( vectorY > maxFallVelocity ) {
        vectorY = maxFallVelocity;
      }
    } else {
      vectorY = fallVelocity;
    }
  }

  /**
   * オブジェクトが重ならないようにする
   */
  private void positionCorrection() {
    cm.forEach( data -> {
      // 通過オブジェクトの場合、次のdataへ
      if( data.getSubject() instanceof FixedObj &&
          ((FixedObj) data.getSubject()).canPassing() ) {
        return;
      }

      switch (data.getSide()) {
      case TOP:
        positionY = data.getCollisionPositionY();
        break;
      case LEFT:
        positionX = data.getCollisionPositionX();
        break;
      case BOTTOM:
        positionY = data.getCollisionPositionY();
        break;
      case RIGHT:
        positionX = data.getCollisionPositionX();
        break;
      }
    });
  }
}
