package game.system;

import java.util.HashMap;

import game.object.Obj;

/**
 * ステージのObjの状態の保存、読み込みを行う
 * @author medysk
 *
 */
public class StageManager {
  private static HashMap<String,Obj> cpInstances;
  private static long frameCount;

  /**
   * セーブ
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
   * ロード
   */
  public static void load() {
    Obj.overwriteInstances(cpInstances);
    FrameManager.setFrameCount(frameCount);
  }
}

