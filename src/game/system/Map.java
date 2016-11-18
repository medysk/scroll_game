package game.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import config.GameData;
import game.Stage;
import game.object.Obj;
import game.object.fixed.Downhill;
import game.object.fixed.Flat;
import game.object.fixed.Ground;
import game.object.fixed.RockBlock;
import game.object.fixed.Uphill;
import game.object.move.player.Character;

/**
 * @author medysk
 * ゲームのマップをマップファイルから読み込む
 */
public class Map {
  private final static ConcurrentHashMap<String,Obj> objs = Obj.getInstances();
  // key: Y座標, value: X座標(地面最上部)
  private final static HashMap<Integer,Integer> lowerLimit = new HashMap<>();   // Y軸上の地面の表面
  private static int leftLimit;
  private static int rightLimit;
  private static List<String> groundIds;

  private static FileReader fr;
  private static BufferedReader br;


  /**
   * マップをファイルから読み込み
   * マップ情報を設定
   */
  public static void create(String filePath) {
    List<String[]> records = readFile(filePath);

    createObj(records);

    createMapInformation();
  }

  /**
   * 任意のX座標上の下限値を取得する
   * @param x 任意のX座標
   * @return 下限値
   */
  public static int getLowerLimit( int x ) throws NullPointerException {
    return lowerLimit.get(x);
  }

  /**
   * getter
   * @return
   */
  public static int getLeftLimit() {
    return leftLimit;
  }

  /**
   * getter
   * @return
   */
  public static int getRightLimit() {
    return rightLimit;
  }

  // ###  Private Methods  ###

  /**
   * ファイルを読み込む
   * @param filePath
   * @return ファイルの1行がリストの1要素、配列には1行を分割した要素が入る
   */
  private static List<String[]> readFile(String filePath) {
    try {
      fr = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      System.out.println("ファイルが開けません。指定したパスが間違っています。");
    }
    br = new BufferedReader(fr);

    String line;
    int lineLength = 0;
    List<String[]> records = new ArrayList<>();

    try {
      int lines = GameData.MAP_FILE_VALID_RECORD; // マップファイルの有効行数
      while ( (line = br.readLine()) != null && records.size() <= lines) {
        records.add( line.split("") );
        if(lineLength < records.get(records.size() - 1).length ) {
          lineLength = records.get(records.size() - 1).length;
        }
      }

      if( records.size() < lines ) {
        throw new IOException("map{n}.conf が" + lines + "行未満です");
      }

      br.close();
      fr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return records;
  }

  private static void createObj( List<String[]> records ) {
    final int baseWidth = GameData.BASE_OBJ_WIDTH;
    final int baseHeight = GameData.BASE_OBJ_HEIGHT;
    int recordLength = 0;
    int columnLength;

    // マップファイル1行ごとのループ
    for( String[] record : records ) {
      int y = baseHeight * recordLength++; // オブジェクトのY座標
      int x = 0;                           // オブジェクトのX座標

      columnLength = 0;
      // マップファイル1行の一文字ごとのループ
      for( String symbol : record ) {
        x = baseWidth * columnLength++;

        if( ! ObjMap.containsKey(symbol) ) { continue; }

        Obj obj = instantiation(symbol, x, y);
        // パラメータが必要なインスタンスなら、セットする
        if(obj instanceof Character) {
          ((Character) obj).setKeyState(Stage.getKeyState());
        } else if( symbol.equals("I") ) {
          ((RockBlock) obj).invisibility();
        }

        // GroundのサブクラスとBlockのサブクラス以外は位置補正を行う
        positionCorrection(obj);
      }
    }
  }

  // 位置補正
  private static void positionCorrection(Obj obj) {
    int dif = obj.getHeight() - GameData.BASE_OBJ_HEIGHT;
    obj.setPosition(obj.getPositionX(), obj.getPositionY() - dif);
  }

  // インスタンスの作成
  private static Obj instantiation(String symbol, int x, int y) {
    Constructor<?> constructor = null;
    Obj obj = null;

    Class<?>[] types = { int.class, int.class };
    try {
      constructor = ObjMap.get(symbol).getConstructor(types);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }

    Object[] args = { x, y };
    try {
      obj = Obj.create( (Obj) constructor.newInstance(args) );
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (ReflectiveOperationException  e) {
      e.printStackTrace();
    }

    return obj;
  }

  private static void createMapInformation() {
    groundIds = Obj.groundIds();
    leftLimit = 0;
    rightLimit = 0;

    groundIds.forEach( id -> {
      int x = objs.get(id).getPositionX();
      int y = objs.get(id).getPositionY();

      // マップ右端
      if( rightLimit  < x + objs.get(id).getWidth() ) {
        rightLimit = x + objs.get(id).getWidth();
      }

      // X軸上の地面最上部
      if( ! lowerLimit.containsKey( x ) ) {
        // あるX座標中の情報がなければ、高さを登録
        inputLowerLimit( (Ground)objs.get(id) );
      } else if( lowerLimit.get(x) > y ) {
        // 高さを上書き
        inputLowerLimit( (Ground)objs.get(id) );
      }
    });
  }

  private static void inputLowerLimit( Ground ground ) {
    int x = ground.getPositionX();
    for( int i=0; i<ground.getWidth(); i++ ) {
      if( ground instanceof Flat ) {
        lowerLimit.put( x + i, ground.getPositionY() );
      } else if( ground instanceof Uphill ) {
        int y = ground.getPositionY() - i + ground.getHeight();
        lowerLimit.put( x + i, y );
      } else if( ground instanceof Downhill ) {
        lowerLimit.put( x + i, ground.getPositionY() + i );
      }
    }
  }
}
