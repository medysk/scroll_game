package game.object;

/**
 * �Q�[���ɕ`�ʂ���I�u�W�F�N�g�̓�
 * ����̂���I�u�W�F�N�g�̃X�[�p�[�N���X
 * @author medysk
 *
 */
public abstract class MoveObj extends Obj {

  protected boolean isFlying; // �I�u�W�F�N�g�����ł��邩�H
  protected int minSpeed;     // �ړ����x
  protected int currentSpeed; // ���݂̑��x
  protected int maxSpeed;     // �ő呬�x( ���I�u�W�F�N�g�ȊO�̗v���Œ����邱�Ƃ����� )
  protected int fallVelocity; // �������x
  protected int maxFallVelocity; // �ő嗎�����x �I�u�W�F�N�g�� height ��菬��������
  protected int verticalLeap; // �W�����v��
  protected int vectorX;      // �x�N�g��
  protected int vectorY;
  protected int prePositionX; // �O��̈ʒu
  protected int prePositionY;

  public MoveObj( int positionX, int positionY ) {
    super( positionX, positionY );
    setPrePositionX(positionX);
    setPrePositionY(positionY);
  }

  /**
   * �I�u�W�F�N�g�̎��s�p���\�b�h
   */
  public void execute() {
    fall();
  }

  /**
   * �I�u�W�F�N�g���ړ������邽�߂̃��\�b�h
   */
  public void move() {
    prePositionX = positionX;
    prePositionY = positionY;
    positionX += vectorX;
    positionY += vectorY;
  }

  /**
  * ��������
  */
  public void fall() {
    if( isFlying ) {
      vectorY += fallVelocity;
      if( vectorY > maxFallVelocity ) {
        vectorY = maxFallVelocity;
      }
    } else {
      vectorY = 0;
//      vectorY = fallVelocity;
    }
  }

  /**
  * �W�����v����
  */
  public void jump() {
    vectorY -= verticalLeap + Math.abs( vectorX / 2 );
  }

  // �I�u�W�F�N�g�̈ړ��m�F�p���\�b�h
  public boolean isUpMove()    { return vectorY < 0; }
  public boolean isRightMove() { return vectorX > 0; }
  public boolean isDownMove()  { return vectorY > 0; }
  public boolean isLeftMove()  { return vectorX < 0; }

  // ###  Accessorss  ###

  /**
   * getter
   * @return �I�u�W�F�N�g�����ł����true
   */
  public boolean isFlying() {
    return isFlying;
  }

  // getter
  public int getVectorX() { return vectorX; }
  public int getVectorY() { return vectorY; }
  public int getPrePositionX() { return prePositionX; }
  public int getPrePositionY() { return prePositionY; }
  public int getMinSpeed() { return minSpeed; }
  public int getMaxSpeed() { return maxSpeed; }
  public int getVerticalLeap() { return verticalLeap; }
  public int getFallVelocity() { return fallVelocity; }

  // setter
  public void setPrePositionX(int x) { prePositionX = x; }
  public void setPrePositionY(int y) { prePositionY = y; }
  public void setVectorX(int px) { vectorX = px; }
  public void setVectorY(int px) { vectorY = px; }

}
