package game.select;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import game.config.GameData;

/**
 * @author medysk
 * ステージセレクトの描写を行う
 */
public class SelectPanel extends JPanel implements Runnable {

  private final String before = "前へ(Z)";    // 「前へ」の表示用文字列
  private final int beforePositionX = 200;   // 「前へ」のX座標
  private final String next = "次へ(X)";       // 「次へ」の表示用文字列
  private final int nextPositionX = 400;      // 「次へ」のX座標
  private final int pagerPositionY = 500;     // ページャーのY座標
  private final int listPositionX = 200;       // ステージリストのY座標のベース
  private final int listBasePositionY = 50;  // ステージリストのX座標
  private final int maxDisplayNum = 5;          // 最大表示数

  private HashMap<String,Color> stageNameMap = new HashMap<>(); // ステージの名前と表示色のマップ
  private StageSelect stageSelect;             // ステージセレクトを管理するクラス

  /**
   * ステージセレクト用のパネルを生成する
   */
  public SelectPanel() {
    setSize( new Dimension(GameData.PANEL_WIDTH, GameData.PANEL_HEIGHT) );
    setFocusable( true );
    stageSelect = new StageSelect(maxDisplayNum);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    background(g);
    Graphics2D g2 = prepareCharacter((Graphics2D)g);

    if( stageNameMap.size() == 0 ) {
      return;
    }

    int rowIdx = 1;
    g2.setColor( new Color(GameData.MENU_TEXT_COLOR));
    g2.drawString("選択（↑↓）  決定（Enter）", 30, listBasePositionY * rowIdx);

    // ステージ選択肢をソートして表示
    List<String> sortedKeys = new ArrayList<String>(stageNameMap.keySet());
    Collections.sort(sortedKeys);
    for( String key : sortedKeys ) {
      rowIdx++;
      g2.setColor( stageNameMap.get(key) );
      g2.drawString( key, listPositionX, listBasePositionY * rowIdx );
    }
    // 次があるなら「次へ」を表示
    g2.setColor(new Color(GameData.MENU_TEXT_COLOR));
    if( stageSelect.hasBeforePage() ) {
      g2.drawString(before, beforePositionX, pagerPositionY);
    }
    // 前があるなら【前へ」を表示
    if( stageSelect.hasNextPage() ) {
      g2.drawString(next, nextPositionX, pagerPositionY);
    }

  }

  @Override
  public void run() {
    while(true) {
      stageSelect.execute();
      stageNameMap = stageSelect.getNames(); // ステージの名前を取得
      // ステージの選択がされているなら、ループを抜けスレッドを終了する
      if( stageSelect.getFilePath() != null ) {
        break;
      }

      this.repaint(); // 再描写
    }
  }

  /**
   * 選択されたファイルのパスを取得
   * @return
   */
  public String getSelectedFilePath() {
    return stageSelect.getFilePath();
  }

  // 文字列描写の準備
  private Graphics2D prepareCharacter(Graphics2D g2) {
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 48) );
    return g2;
  }

  // 背景
  private void background(Graphics g) {
    g.setColor( new Color(GameData.MENU_BG_COLOR) );
    g.fillRect(0, 0, GameData.PANEL_WIDTH, GameData.PANEL_HEIGHT);
  }

}
