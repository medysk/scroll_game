package game.system;

import game.object.MoveObj;
import game.object.Obj;

/**
 * �I�u�W�F�N�g�̏Փ˂��N�������ۂɁA�Փˏ󋵂�ێ����邽�߂̍\����
 * �R���X�g���N�^�̈���( MoveObj(�Ώ�), Obj(��Ώ�), Side(�Ώۂ̏Փ˖�) ) ��n��
 * @author medysk
 *
 */
public class CollisionData {
  private MoveObj targetObj;  // �Փ˂̌����Ώ�
  private Obj subjectObj;     // �Փ˂̌����ΏۂƏՓ˂��N�������I�u�W�F�N�g
  private Side side;          // �Փ˂��N�������ۂɌ����Ώۂ���Ƃ����Փˈʒu
  private int collisionPositionX;      // �����Ώۂ��Փ˂��N�������ʒu
  private int collisionPositionY;      // �����Ώۂ��Փ˂��N�������ʒu

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
