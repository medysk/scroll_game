package game.system;

import game.object.MoveObj;
import game.object.Obj;

/**
 * オブジェクトの衝突が起こった際に、衝突状況を保持するための構造体
 * コンストラクタの引数( MoveObj(対象), Obj(被対象), Side(対象の衝突面) ) を渡す
 * @author medysk
 *
 */
public class CollisionData {
  private MoveObj targetObj;  // 衝突の検査対象
  private Obj subjectObj;     // 衝突の検査対象と衝突を起こしたオブジェクト
  private Side side;          // 衝突が起こった際に検査対象を主とした衝突位置
  private int collisionPositionX;      // 検査対象が衝突を起こした位置
  private int collisionPositionY;      // 検査対象が衝突を起こした位置

  public CollisionData( MoveObj targetObj, Obj subjectObj, Side side,
                        int positionX, int positionY) {
    this.targetObj = targetObj;
    this.subjectObj = subjectObj;
    this.side = side;
    this.collisionPositionX = positionX;
    this.collisionPositionY = positionY;
  }

  // ###  Accecors  ###

  public MoveObj getTarget() { return targetObj; }
  public Obj getSubject()    { return subjectObj; }
  public Side getSide()         { return side; }
  public int getCollisionPositionX()   { return collisionPositionX; }
  public int getCollisionPositionY()   { return collisionPositionY; }
}
