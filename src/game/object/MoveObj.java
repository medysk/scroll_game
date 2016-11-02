package game.object;

/**
 * ゲームに描写するオブジェクトの内
 * 動作のあるオブジェクトのスーパークラス
 * @author medysk
 *
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

  public MoveObj( int positionX, int positionY ) {
    super( positionX, positionY );
    setPrePositionX(positionX);
    setPrePositionY(positionY);
  }

  /**
   * オブジェクトの実行用メソッド
   */
  public void execute() {
    fall();
  }

  /**
   * オブジェクトを移動させるためのメソッド
   */
  public void move() {
    prePositionX = positionX;
    prePositionY = positionY;
    positionX += vectorX;
    positionY += vectorY;
  }

  /**
  * 落下処理
  */
  public void fall() {
    if( isFlying ) {
      vectorY += fallVelocity;
      if( vectorY > maxFallVelocity ) {
        vectorY = maxFallVelocity;
      }
    } else {
      vectorY = 0;
//      vectorY = fallVelocity;
    }
  }

  /**
  * ジャンプ処理
  */
  public void jump() {
    vectorY -= verticalLeap + Math.abs( vectorX / 2 );
  }

  // オブジェクトの移動確認用メソッド
  public boolean isUpMove()    { return vectorY < 0; }
  public boolean isRightMove() { return vectorX > 0; }
  public boolean isDownMove()  { return vectorY > 0; }
  public boolean isLeftMove()  { return vectorX < 0; }

  // ###  Accessorss  ###

  /**
   * getter
   * @return オブジェクトが飛んでいればtrue
   */
  public boolean isFlying() {
    return isFlying;
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

}
