package game.system;

/**
 * �Q�[���̃t���[�����̊Ǘ�����
 * �܂��A���t���[���Ɉ�x���삷��@�\���񋟂���( �^�U�l��n������ )
 *
 * @author medysk
 *
 */
public class FrameManagement {
  private static long frameCount = 0;  // �t���[����

  /**
   * num ��Ɉ�xtrue��Ԃ�
   * @param num
   * @return
   */
  public static boolean isActionFrame(int interval) {
    return (frameCount % interval) == 0;
  }

  /**
   * �C�ӂ̃t���[���܂ŏ������~�߂�(�s��)�Ƃ��Ɏg�p����
   * @param untilAfterSleepFrame ���t���[���� + �~�߂����t���[����
   * @return ���݂̃t���[�����������̃t���[�������傫����� true ��Ԃ�
   */
  public static boolean isExceedFrame( long untilAfterSleepFrame ) {
    return frameCount > untilAfterSleepFrame;
  }

  /**
   * �t���[�����J�E���g�̃C���N�������g
   */
  public static void increment() {
    frameCount++;
  }

  /**
   * getter
   * @return
   */
  public static long getFrameCount() {
    return frameCount;
  }
}
