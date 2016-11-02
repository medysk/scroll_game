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

  public FixedObj( int positionX, int positionY ) {
    super( positionX, positionY );
  }

  /* (�� Javadoc)
   * @see game.object.Obj#destructor()
   */
  @Override
  public void destructor(String id) {
    if( isDestory ) {
      super.destructor(id);
    }
  }
}
