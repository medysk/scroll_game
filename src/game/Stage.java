package game;

import java.util.concurrent.TimeUnit;

import javax.swing.JLayeredPane;

import game.Frame;
import game.StagePanel;
import game.config.GameData;
import game.object.MoveObj;
import game.object.Obj;
import game.system.FrameManager;
import game.system.KeyState;
import game.system.Map;
import game.system.StageManager;

/**
 * @author medysk
 * プログラム実行用のクラス
 */
public class Stage implements Runnable {
  private static Frame frame;           // GUI用のフレーム
  private static StagePanel stagePanel; // ゲーム描写用のメインパネル
  private static KeyState keyState;     // キー入力管理
  private static Thread stage;          // スレッド用クラス
  private static boolean isClear;       // クリアフラグ
  private static int deathCount;        // 死亡回数

  // 初期化、設定、実行
  static {
    keyState = new KeyState();
    Map.create("config/map/map1.conf");
    stagePanel = new StagePanel();
    frame = new Frame(stagePanel);
    stagePanel.addKeyListener(keyState);
    isClear = false;
    deathCount = 0;
  }

  /**
   * Main Method
   */
  public static void main(String[] args){
    FrameManager.increment();
    stage = new Thread( new Stage() );

    echo("Game Start", 400, 100, 4, 300);
    StageManager.save();
    stage.start();
  }

  /**
   * スレッドの実装メソッド
   * ゲームの起点
   */
  public void run() {
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

      stagePanel.repaint();      // メインパネルの再描写

      // ステージクリアしたらスレッドを終了
      if( isClear ) { break; }

      // ゲームの速度
      // TODO: ゲームの基本機能を実装したのち、調整する また、設定ファイルから読み込む
      try {
        TimeUnit.MILLISECONDS.sleep(GameData.SYSTEM_SLEEP);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }

  /**
   * ステージを最初から、もしくはセーブポイントから開始する
   */
  public static void restart() {
    deathCount++;
    StageManager.load();
    echo("restart", 450, 100, 4, 300);
  }

  /**
   * ステージクリアの処理
   */
  public static void clear() {
    isClear = true;
    echo("Game Clear", 400, 100, 5, 300);
    String msg = "あなたの死亡回数は" + deathCount + "回です";
    echo(msg, 400, 100, 1, 5000);
  }

  /**
   * 文字列を描写
   * @param msg 文字列
   * @param x X座標
   * @param y Y座標
   * @param flassing 点滅回数
   * @param milliSeconds 点滅時間(ミリ秒)
   */
  public static void echo( String msg, int x, int y, int flashing, int milliSeconds ) {
    HeadingPanel headingPanel = new HeadingPanel(msg, x, y);

    for( int i=0; i<flashing*2; i++) {
      // 文字列を点滅させる
      if( i % 2 == 0) {
        frame.addLayer(headingPanel, JLayeredPane.POPUP_LAYER);
      }
      try {
        TimeUnit.MILLISECONDS.sleep(milliSeconds);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
      frame.removeLayer(headingPanel);
      stagePanel.repaint();
    }
    headingPanel = null;
  }

  /**
   * @return KeyStateのインスタンス
   */
  public static KeyState getKeyState() {
    return keyState;
  }
}
