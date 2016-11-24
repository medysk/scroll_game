package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;

import game.config.GameData;
import game.object.FixedObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.Map;

/**
 * @author medysk
 * ゲーム用のメインパネル
 */
public class StagePanel extends JPanel {
  private static final int width = GameData.PANEL_WIDTH;
  private static final int harfWidth = GameData.PANEL_HALF_WIDTH;
  private static final int height = GameData.PANEL_HEIGHT;

  // ゲームに描写するオブジェクトを格納する
  private ConcurrentHashMap<String,Obj> objs = new ConcurrentHashMap<>();

  /**
   * パネルの設定と変数の初期化
   */
  public StagePanel() {
    setSize( new Dimension(width, height) );
    setFocusable( true );
    objs = Obj.getInstances();
  }

  @Override
  public void paintComponent( Graphics g ) {
    super.paintComponent(g);

    // TODO: Backgroundクラスに処理を任せる
    g.setColor( new Color(GameData.BACKGROUND_COLOR) );
    g.fillRect( 0, 0, getWidth(), getHeight() );

    // オブジェクトの描写
    if( objs.isEmpty() ) {
      return;
    }

    Character character = Obj.getCharacter();

    if( character == null ) {
      return;
    }

    // オブジェクトの描写
    objs.forEach( (k, obj) -> {
      // 不可視オブジェクトの場合、描写しない
      if( obj instanceof FixedObj && ! ((FixedObj)obj).isVisivility() ) {
        return;
      }

      // 画面外のオブジェクトは描写しない
      if( character.getPositionX() + harfWidth < obj.getPositionX() &&
          character.getPositionX() - harfWidth > obj.getPositionX() + obj.getWidth() ) {
        return;
      }

      // キャラクターに合わせてマップをスライドさせる
      if( character.getPositionX() < harfWidth ) {
        obj.draw( g.create() );
      } else if( character.getPositionX() > (Map.getRightLimit() - harfWidth) ) {
        Graphics ng = g.create();
        ng.translate( - Map.getRightLimit() + width, 0);
        obj.draw(ng);
      } else {
        Graphics ng = g.create();
        ng.translate(- character.getPositionX() + harfWidth, 0);
        obj.draw(ng);
      }
    } );
  }
}
