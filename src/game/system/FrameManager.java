package game.system;

/**
 * @author medysk
 * ゲームのフレーム数の管理する
 * また、数フレームに一度動作する機能も提供する( 真偽値を渡すだけ )
 */
public class FrameManager {
  private static long frameCount = 0;  // フレーム数

  /**
   * num 回に一度trueを返す
   * @param num
   * @return
   */
  public static boolean isActionFrame(int interval) {
    return (frameCount % interval) == 0;
  }

  /**
   * 任意のフレームまで処理を止める(行う)ときに使用する
   * @param untilAfterSleepFrame 現フレーム数 + 止めたいフレーム数
   * @return 現在のフレーム数を引数のフレーム数が大きければ true を返す
   */
  public static boolean isExceedFrame( long untilAfterSleepFrame ) {
    return frameCount > untilAfterSleepFrame;
  }

  /**
   * フレーム数カウントのインクリメント
   */
  public static void increment() {
    frameCount++;
  }

  /**
   * getter
   * @return
   */
  public static long getFrameCount() {
    return frameCount;
  }

  public static void setFrameCount( long fc ) {
    frameCount = fc;
  }
}
