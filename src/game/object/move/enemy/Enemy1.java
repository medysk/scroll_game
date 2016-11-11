package game.object.move.enemy;

import java.awt.Color;
import java.awt.Graphics;

import game.StagePanel;
import game.object.MoveObj;
import game.object.Obj;
import game.object.move.player.Character;
import game.system.FrameManagement;
import game.system.Side;

/**
 * 敵キャラクター
 * @author medysk
 *
 */
public class Enemy1 extends MoveObj {

  /**
   * 設定の初期化
   * @param positionX 初期位置
   * @param positionY
   */
  public Enemy1(int positionX, int positionY) {
    super(positionX, positionY);
    height = 28;
    width = 28;
    minSpeed = 2;
    maxSpeed = 2;
    fallVelocity = 1;
    maxFallVelocity = height - 1;
    verticalLeap = 12;
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
    if( Math.abs(difference) > StagePanel.WIDTH / 2 ) {
      return;
    }

    if( FrameManagement.isActionFrame(100) ) {
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
    g.setColor( Color.ORANGE);
    g.fillRect(positionX, positionY, width, height);
  }

}
