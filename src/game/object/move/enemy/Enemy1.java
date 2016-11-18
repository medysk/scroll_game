package game.object.move.enemy;

import java.awt.Color;
import java.awt.Graphics;

import config.GameData;
import game.object.MoveObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.FrameManager;
import game.system.Side;

/**
 * @author medysk
 * 敵キャラクター
 */
public class Enemy1 extends MoveObj {

  /**
   * 設定の初期化
   * @param positionX 初期位置
   * @param positionY
   */
  public Enemy1(int positionX, int positionY) {
    super(positionX, positionY);
    height = GameData.ENEMY1_HEIGHT;
    width = GameData.ENEMY1_WIDTH;
    minSpeed = GameData.ENEMY1_MIN_SPEED;
    maxSpeed = GameData.ENEMY1_MAX_SPEED;
    fallVelocity = GameData.ENEMY1_FALL_VELOCITY;
    maxFallVelocity = GameData.ENEMY1_MAX_FALL_VELOCITY;
    verticalLeap = GameData.ENEMY1_VERTICAL_LEAP;
  }

  /* (非 Javadoc)
   * @see game.object.MoveObj#execute()
   */
  @Override
  public void execute() {
    super.execute();

    // 衝突処理
    collisionHandling( data -> {
   // オブジェクトのTOPに衝突した かつ ジャンプ中(上昇中)
      if( data.getSide() == Side.TOP && (isFlying && vectorY < 0) ) {
        vectorY = - vectorY / 3;
      }

      if( data.getSubject() instanceof Character ) {
        if( data.getSide() != Side.TOP ) {
          data.getSubject().destructor();
        }
      }
    });

    // 位置補正後に前回位置を更新
    updatePrePosition();
  }

  /* (非 Javadoc)
   * @see game.object.MoveObj#action()
   */
  @Override
  protected void action() {
    // キャラクターが近ずくと動き始める
    int difference = Obj.getCharacter().getPositionX() - positionX;
    if( Math.abs(difference) > GameData.PANEL_HALF_WIDTH ) {
      return;
    }

    if( FrameManager.isActionFrame( GameData.ENEMY1_JUMP_INTERVAL ) ) {
      jump();
    }

    vectorX = Obj.getCharacter().getPositionX() < positionX ?
        - minSpeed : minSpeed;
  }

  /* (非 Javadoc)
   * @see game.object.Obj#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics g) {
    g.setColor( new Color(GameData.ENEMY1_COLOR) );
    g.fillRect(positionX, positionY, width, height);
  }

}
