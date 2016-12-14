package game;

import java.util.concurrent.TimeUnit;

import javax.swing.JLayeredPane;

import game.Frame;
import game.select.SelectPanel;
import game.stage.Stage;
import game.stage.StagePanel;
import game.system.KeyState;

public class Game {

  private static Frame frame;                  // フレーム
  private static KeyState keyState;        // キー入力管理
  private static StagePanel stagePanel; // ステージパネル

  static {
    frame = new Frame();
    keyState = new KeyState();
  }

  public static void main(String[] args) {
    while( true ) {
      SelectPanel selectPanel = new SelectPanel();
      frame.addLayer(selectPanel, JLayeredPane.DEFAULT_LAYER);

      Thread selectThread = new Thread(selectPanel);
      selectThread.start();
      mainThreadPause(selectThread);
      frame.removeLayer(selectPanel);

      stagePanel = new StagePanel();
      String stageFilePath = selectPanel.getSelectedFilePath();
      Stage stage = new Stage(stageFilePath);
      stage.setStagePanel(stagePanel);
      Thread stageThread = new Thread(stage);
      frame.addLayer(stagePanel, JLayeredPane.DEFAULT_LAYER);

      stageThread.start();
      mainThreadPause(stageThread);
      frame.removeLayer(stagePanel);
    }
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

    for( int i=0; i<flashing * 2; i++ ) {
      // 文字列を点滅させる
      if( i % 2 == 0 ) {
        frame.addLayer(headingPanel, JLayeredPane.POPUP_LAYER);
      }
      try {
        TimeUnit.MILLISECONDS.sleep(milliSeconds);
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }
      frame.removeLayer(headingPanel);
    }
    headingPanel = null;
  }

  /**
   * @return KeyStateのインスタンス
   */
  public KeyState getKeyState() {
    return keyState;
  }

  // 引数のスレッドが終了するまでメインスレッド停止
  private static void mainThreadPause( Thread thread ) {
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
