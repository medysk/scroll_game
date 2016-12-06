package game.stage;

import java.util.concurrent.TimeUnit;

import game.Game;
import game.config.GameData;
import game.object.MoveObj;
import game.object.Obj;
import game.system.FrameManager;
import game.system.LoadStage;
import game.system.StageManager;

/**
 * @author medysk
 * ステージ実行用のクラス
 */
public class Stage implements Runnable {
  private static Stage stage;       // インスタンスのリターン用の変数
  private StagePanel stagePanel; // ステージパネル
  private boolean isClear;            // クリアフラグ
  private int deathCount;              // 死亡回数

  /**
   * ステージの処理を行う
   * @param filePath
   */
  public Stage( String filePath ) {
    Obj.clearInstances();
    FrameManager.initialize();
    stage = this;
    LoadStage.create( filePath);
    StageManager.save();
    isClear = false;
  }

  @Override
  public void run() {
    Game.echo("Game Start", 400, 100, 4, 300);

    while(true) {
      FrameManager.increment();

      Obj.moveObjIds().forEach( id -> {
        // インスタンスがゲームから取り除かれた場合スキップ
        if( ! Obj.getInstances().containsKey(id) ) {
          return;
        }
        ((MoveObj) Obj.getInstances().get(id)).execute();
      });

      // プレイヤーがゲームから除外されたらリスタート
      if( Obj.getCharacter() == null ) {
        restart();
      }

      stagePanel.repaint();      // ステージパネルの再描写

      // ステージクリアしたらスレッドを終了
      if( isClear ) { break; }

      // ゲームの速度
      try {
        TimeUnit.MILLISECONDS.sleep(GameData.SYSTEM_SLEEP);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * ステージを最初から、もしくはセーブポイントから開始する
   */
  public void restart() {
    deathCount++;
    StageManager.load();
    Game.echo("restart", 450, 100, 4, 300);
  }

  /**
   * ステージクリアの処理
   */
  public void clear() {
    Game.echo("Game Clear", 400, 100, 5, 300);
    String msg = "あなたの死亡回数は" + deathCount + "回です";
    Game.echo(msg, 400, 100, 1, 2000);
    Game.echo("ステージセレクトに戻ります",  500, 100, 1, 1000);
    isClear = true;
  }

  /**
   * ステージ描写用のパネルをセットする
   * @param stagePanel
   */
  public void setStagePanel(StagePanel stagePanel) {
    this.stagePanel = stagePanel;
  }

  /**
   * インスタンスを返す
   * @return
   */
  public static Stage getInstance() {
    return stage;
  }

}
