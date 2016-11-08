package game.object;

/**
 * ゲームに描写するオブジェクトの内
 * 動かないオブジェクトのスーパークラス
 * @author medysk
 *
 */
public abstract class FixedObj extends Obj {
  // オブジェクトの可視性 true: 見える, false: 見えない
  // キャラクターと衝突が起こると切り替えたりする
  protected boolean isVisibility;
  // 破壊可能か？
  protected boolean isDestory;

  /**
   * 初期化
   * @param positionX
   * @param positionY
   */
  public FixedObj( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /**
   * Characterがオブジェクトの底に衝突した際の処理
   */
  public abstract void bottomAction();

  /**
   * 可視化
   */
  public void visivility() {
    isVisibility = true;
  }

  /**
   * 可視かどうかを返す
   * @return 可視だったら true
   */
  public boolean isVisivility() {
    return isVisibility;
  }

  /**
   * 破壊可能かどうかを返す
   * @return 破壊可能なら true
   */
  public boolean isDestory() {
    return isDestory;
  }

  /* (非 Javadoc)
   * @see game.object.Obj#destructor()
   */
  @Override
  public void destructor() {
    if( isDestory ) {
      super.destructor();
    }
  }
}
