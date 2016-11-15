package game.system;

import java.util.HashMap;

/**
 * マップファイルのオブジェクト記号とオブジェクトのクラスをマッピングする
 * @author medysk
 *
 */
public class ObjMap {
  private static HashMap<String,Class<?>> objMap = new HashMap<>();

  /**
   * マッピング情報の一覧
   */
  private enum ObjMapList {
    SAVE_FLAG    ("s", "game.object.fixed.SaveFlag"),
    CLEAR_FLAG   ("G", "game.object.fixed.ClearFlag"),
    FLAT         ("-", "game.object.fixed.Flat"),
    UPHILL       ("/", "game.object.fixed.Uphill"),
    DOWNHILL     ("\\", "game.object.fixed.Downhill"),
    BRICK_BLOCK  ("b", "game.object.fixed.BrickBlock"),
    ROCK_BLOCK_I ("B", "game.object.fixed.RockBlock"),
    ROCK_BLOCK_F ("I", "game.object.fixed.RockBlock"),
    CHARACTER    ("S", "game.object.move.player.Character"),
    ENEMY1       ("1", "game.object.move.enemy.Enemy1");

    private String symbol;
    private String className;

    ObjMapList(String symbol, String className) {
      this.symbol = symbol;
      this.className = className;
    }

    public String getSymbol() { return symbol; }
    public String getClassName() { return className; }
  }

  // マッピング情報の一覧をもとに、HashMapに値をセットする
  static {
    for( ObjMapList map : ObjMapList.values() ) {
      try {
        objMap.put( map.getSymbol(), Class.forName(map.getClassName()) );
      } catch (ClassNotFoundException e) {
        System.out.println("objMapListのマッピング情報が誤っています");
        e.printStackTrace();
      }
    }
  }

  public static boolean containsKey(String symbol) {
    return objMap.containsKey(symbol);
  }

  /**
   * マップファイルの記号に対応するクラスを取得する
   * @param symbol
   * @return
   */
  public static Class<?> get(String symbol) {
    return objMap.get(symbol);
  }

}
