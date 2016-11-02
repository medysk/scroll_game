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

  public FixedObj( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /* (非 Javadoc)
   * @see game.object.Obj#destructor()
   */
  @Override
  public void destructor(String id) {
    if( isDestory ) {
      super.destructor(id);
    }
  }
}
