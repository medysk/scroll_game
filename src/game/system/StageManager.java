package game.system;

import java.util.HashMap;

import game.object.Obj;

/**
 * �X�e�[�W��Obj�̏�Ԃ̕ۑ��A�ǂݍ��݂��s��
 * @author medysk
 *
 */
public class StageManager {
  private static HashMap<String,Obj> cpInstances;
  private static long frameCount;

  /**
   * �Z�[�u
   */
  public static void save() {
    cpInstances = new HashMap<>();
    FrameManager.getFrameCount();

    Obj.getInstances().forEach( (id,obj) -> {
      try {
        cpInstances.put(id, (Obj) obj.clone() );
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    });

  }

  /**
   * ���[�h
   */
  public static void load() {
    Obj.overwriteInstances(cpInstances);
    FrameManager.setFrameCount(frameCount);
  }
}

