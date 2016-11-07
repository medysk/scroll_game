package game;

import java.util.concurrent.TimeUnit;

import game.Frame;
import game.FieldPanel;
import game.object.MoveObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.FrameManagement;
import game.system.KeyState;
import game.system.Map;

/**
 * プログラム実行用のクラス
 * @author medysk
 *
 */
public class Game implements Runnable {
  private static Frame frame;           // GUI用のフレーム
  private static FieldPanel fieldPanel; // ゲーム描写用のメインパネル
  private static Character character;   // 自キャラ
  private static KeyState keyState;     // キー入力管理
  private static Thread game;           // スレッド用クラス

  // 初期化、設定、実行
  static {
    Map.create();
    frame = new Frame();
    fieldPanel = new FieldPanel();
    keyState = new KeyState();
    fieldPanel.addKeyListener(keyState);
    character = (Character) Obj.create( new Character( 20, 20, keyState ) ); // TODO: 初期位置は設定ファイルから読み込む
    fieldPanel.setCharacter(character);
  }

  /**
   * Main Method
   */
  public static void main(String[] args){
    FrameManagement.increment();
    game = new Thread( new Game() );

    frame.setParams( fieldPanel );
    game.start();
  }

  /**
   * スレッドの実装メソッド
   * ゲームの起点
   */
  public void run() {

    while(true) {
      FrameManagement.increment();

      Obj.getMoveObjIds().forEach( id -> {
        ((MoveObj) Obj.getInstances().get(id)).execute();
      });

      fieldPanel.repaint();      // メインパネルの再描写

      // ゲームの速度に影響する処理
      // TODO: ゲームの基本機能を実装したのち、調整する また、設定ファイルから読み込む
      try {
//        TimeUnit.MILLISECONDS.sleep(15);
        TimeUnit.MILLISECONDS.sleep(15);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }
}
