package game.object;

/**
 * �Q�[���ɕ`�ʂ���I�u�W�F�N�g�̓�
 * �����Ȃ��I�u�W�F�N�g�̃X�[�p�[�N���X
 * @author medysk
 *
 */
public abstract class FixedObj extends Obj {
  // �I�u�W�F�N�g�̉��� true: ������, false: �����Ȃ�
  // �L�����N�^�[�ƏՓ˂��N����Ɛ؂�ւ����肷��
  protected boolean isVisibility;
  // �j��\���H
  protected boolean isDestory;
  // �Փˉ\���H
  protected boolean canCollision;
  // �ʉ߉\���H
  protected boolean canPassing;

  /**
   * ������
   * @param positionX
   * @param positionY
   */
  public FixedObj( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /**
   * Character���I�u�W�F�N�g�ɏՓ˂����ۂ̏���
   */
  public abstract void event();

  /**
   * Character���I�u�W�F�N�g�̒�ɏՓ˂����ۂ̏���
   */
  public abstract void bottomEvent();

  /**
   * ����
   */
  public void visivility() {
    isVisibility = true;
  }

  /**
   * �����ǂ�����Ԃ�
   * @return ���������� true
   */
  public boolean isVisivility() {
    return isVisibility;
  }

  /**
   * �Փˏ������s�����ǂ���
   * @return �Փ˂���Ȃ� true
   */
  public boolean canCollision() {
    return canCollision;
  }

  /**
   * MoveObj���ʉ߂ł��邩�ǂ���
   * @return
   */
  public boolean canPassing() {
    return canPassing;
  }

  /**
   * �j��\���ǂ�����Ԃ�
   * @return �j��\�Ȃ� true
   */
  public boolean isDestory() {
    return isDestory;
  }

  /* (�� Javadoc)
   * @see game.object.Obj#destructor()
   */
  @Override
  public void destructor() {
    if( isDestory ) {
      super.destructor();
    }
  }
}
