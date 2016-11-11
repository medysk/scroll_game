package game;

import java.util.concurrent.TimeUnit;

import javax.swing.JLayeredPane;

import game.Frame;
import game.StagePanel;
import game.object.MoveObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.FrameManagement;
import game.system.KeyState;
import game.system.Map;
import game.system.StageManager;

/**
 * プログラム実行用のクラス
 * @author medysk
 *
 */
public class Stage implements Runnable {
  private static Frame frame;           // GUI用のフレーム
  private static StagePanel stagePanel;     // ゲーム描写用のメインパネル
  private static KeyState keyState;     // キー入力管理
  private static Thread stage;          // スレッド用クラス

  // 初期化、設定、実行
  static {
    keyState = new KeyState();
    Obj.create( new Character( 20, 20, keyState ) ); // TODO: 初期位置は設定ファイルから読み込む
    Map.create();
    stagePanel = new StagePanel();
    frame = new Frame(stagePanel);
    stagePanel.addKeyListener(keyState);
  }

  /**
   * Main Method
   */
  public static void main(String[] args){
    FrameManagement.increment();
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
      FrameManagement.increment();

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

      // ゲームの速度
      // TODO: ゲームの基本機能を実装したのち、調整する また、設定ファイルから読み込む
      try {
        TimeUnit.MILLISECONDS.sleep(15);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }

  /**
   * ステージを最初から、もしくはセーブポイントから開始する
   */
  public static void restart() {
//    stage.interrupt();
    StageManager.load();
    echo("restart", 450, 100, 4, 300);
  }

  /**
   * ステージクリアの処理
   */
  public void clear() {

  }

  /**
   * 文字列を描写
   * @param str 文字列
   * @param x X座標
   * @param y Y座標
   */
  public static void echo( String str, int x, int y, int flashing, int milliSeconds ) {
    HeadingPanel headingPanel = new HeadingPanel(str, x, y);

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
}
