package game.object;

import java.util.function.Consumer;

import game.StagePanel;
import game.system.CollisionData;
import game.system.CollisionManager;

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
  protected CollisionManager cm;    // �Փ˔���p�N���X

  public MoveObj( int positionX, int positionY ) {
    super( positionX, positionY );
    cm = new CollisionManager(this);
    setPrePositionX(positionX);
    setPrePositionY(positionY);
  }

  /**
   * �I�u�W�F�N�g�̎��s�p���\�b�h
   */
  public void execute() {
    // Character���Q�[�����珜�O���ꂽ�ꍇ�A������s��Ȃ�
    if( Obj.getCharacter() == null ) { return; }

    fall();
    action();
    move();

    cm.execute();                 // �Փ˔���
    isFlying = ! cm.onFixedObj(); // �󒆔���

    positionCorrection();         // �ʒu�␳
  }

  // ###  Abstract methods  ###

 /**
   * MoveObj�̓������������
   */
  abstract protected void action();

  // ###  Instance methods  ###

  /**
   * �I�u�W�F�N�g���ړ������邽�߂̃��\�b�h
   */
  public void move() {
    positionX += vectorX;
    positionY += vectorY;

    // ��ʉ��ɗ������ꍇ�A�Q�[�����珜�O����
    if( positionY > StagePanel.HEIGHT ) {
      positionY = StagePanel.HEIGHT;
      destructor();
    }
  }

  // �I�u�W�F�N�g�̈ړ��m�F�p���\�b�h
  public boolean isUpMove()    { return vectorY < 0; }
  public boolean isRightMove() { return vectorX > 0; }
  public boolean isDownMove()  { return vectorY > 0; }
  public boolean isLeftMove()  { return vectorX < 0; }

  // ###  Accessors  ###

  /**
   * @return �I�u�W�F�N�g�����ł����true
   */
  public boolean isFlying() {
    return isFlying;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    MoveObj obj = (MoveObj)super.clone();
    obj.setCollisionManager(obj);
    return obj;
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
  public void setCollisionManager(MoveObj obj) { cm = new CollisionManager(obj); }

  // ###  Protected methods  ###

  /**
  * �W�����v����
  */
  protected void jump() {
    vectorY -= verticalLeap + Math.abs( vectorX / 2 );
  }

  /**
   * �O��ʒu�̍X�V
   */
  protected void updatePrePosition() {
    prePositionX = positionX;
    prePositionY = positionY;
  }

  /**
   * MoveObj�̃T�u�N���X����A�Փˏ�������������Ƃ��ɗ��p����B
   * @param cons �Փˏ����̃R�[���o�b�N�֐�
   */
  protected void collisionHandling(Consumer<CollisionData> cons) {
    cm.forEach( data -> {
      // ���C���X�^���X�Ƃ̏Փ˂ł͂Ȃ��ꍇ�A�������΂�
      if( data.getTarget() != this) {
        return;
      }
      cons.accept( data );
    } );
  }

  // ###  Private methods  ###

  /**
  * ��������
  */
  private void fall() {
    if( isFlying ) {
      vectorY += fallVelocity;
      if( vectorY > maxFallVelocity ) {
        vectorY = maxFallVelocity;
      }
    } else {
      vectorY = fallVelocity;
    }
  }

  /**
   * �I�u�W�F�N�g���d�Ȃ�Ȃ��悤�ɂ���
   */
  private void positionCorrection() {
    cm.forEach( data -> {
      // �s���I�u�W�F�N�g�̏ꍇ�A����data��
      if( data.getSubject() instanceof FixedObj &&
          ! ((FixedObj) data.getSubject()).isVisivility() ) {
        return;
      }

      switch (data.getSide()) {
      case TOP:
        positionY = data.getCollisionPositionY();
        break;
      case LEFT:
        positionX = data.getCollisionPositionX();
        break;
      case BOTTOM:
        positionY = data.getCollisionPositionY();
        break;
      case RIGHT:
        positionX = data.getCollisionPositionX();
        break;
      }
    });
  }
}
