package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import game.object.FixedObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.Map;

/**
 * ゲーム用のメインパネル
 * @author medysk
 *
 */
public class StagePanel extends JPanel {
  // TODO: 設定ファイルから読み込む
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;

  // ゲームに描写するオブジェクトを格納する
  private HashMap<String,Obj> objs = new HashMap<>();

  /**
   * パネルの設定と変数の初期化
   */
  public StagePanel() {
    setSize( new Dimension(WIDTH, HEIGHT) );
    setFocusable( true );
    objs = Obj.getInstances();
  }

  @Override
  public void paintComponent( Graphics g ) {
    super.paintComponent(g);

    // TODO: Backgroundクラスに処理を任せる
    g.setColor( new Color(30, 144, 255) );
    g.fillRect( 0, 0, getWidth(), getHeight() );

    // オブジェクトの描写
    if( objs.isEmpty() ) {
      return;
    }

    // オブジェクトの描写
    objs.forEach( (k, obj) -> {
      Character character = Obj.getCharacter();
      // 不可視オブジェクトの場合、描写しない
      if( obj instanceof FixedObj && ! ((FixedObj)obj).isVisivility() ) {
        return;
      }

      // 画面外のオブジェクトは描写しない
      if( character.getPositionX() + WIDTH / 2 < obj.getPositionX() &&
          character.getPositionX() - WIDTH / 2 > obj.getPositionX() + obj.getWidth() ) {
        return;
      }

      // キャラクターに合わせてマップをスライドさせる
      if( character.getPositionX() < (WIDTH / 2) ) {
        obj.draw( g.create() );
      } else if( character.getPositionX() > (Map.getRightLimit() - WIDTH / 2) ) {
        Graphics ng = g.create();
        ng.translate( - Map.getRightLimit() + (WIDTH), 0);
        obj.draw(ng);
      } else {
        Graphics ng = g.create();
        ng.translate(- character.getPositionX() + (WIDTH / 2), 0);
        obj.draw(ng);
      }
    } );
  }
}
