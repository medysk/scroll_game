package game.object;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import game.object.move.player.Character;
import game.object.background.Background;
import game.object.fixed.Ground;

import java.awt.Graphics;

/**
 * ゲームに描写する全てのオブジェクトのスーパークラス
 * サブクラスにMoveObjとFixedObj、Backgoundを持つ
 * サブクラスをインスタンス化する際は create(Obj obj) を使用する
 *
 * @author medysk
 *
 */
public abstract class Obj  implements Cloneable {
  // ###  static変数  ###

  // サブクラスをインスタンス化する際にこの変数に格納する
  private static ConcurrentHashMap<String,Obj> instances = new ConcurrentHashMap<>();

  // ###  instance変数  ###
  // オブジェクトの一意なID
  protected String objId;

  // 初期の位置
  protected int initialPositionX;
  protected int initialPositionY;

  // 現在の座標
  protected int positionX;
  protected int positionY;

  // オブジェクトのサイズ
  protected int height;
  protected int width;

  // 画像か"java.awt.Graphics"なのか
  protected boolean isImg;

  /**
   * Trajectory用のダミーコンストラクタ
   */
  public Obj() {}

  /**
   * Trajectory以外のゲーム描写用のコンストラクタ
   * @param positionX 初期位置
   * @param positionY 初期位置
   */
  public Obj( int positionX, int positionY ) {
    setPosition( positionX, positionY );
    objId = new UID().toString();
  }
  // ########################
  // ###  Static Methods  ###
  // ########################

  public static Obj create( Obj obj ) {
    instances.put( obj.getObjId(), obj );
    return obj;
  }

  public static void overwriteInstances(HashMap<String,Obj> cpInstances) {
    instances.clear();

    cpInstances.forEach( (id,obj) -> {
      try {
        instances.put(id, (Obj) obj.clone() );
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    });
  }

  // TODO: 呼び出すたびに走査しないようにする
  public static Character getCharacter() {
    for(Obj obj : instances.values()) {
      if( obj instanceof Character ) {
        return (Character) obj;
      }
    }
    return null;
  }

  public static ConcurrentHashMap<String, Obj> getInstances() {
    return instances;
  }

  // TODO: 呼び出すたびに走査しないようにする
  public static CopyOnWriteArrayList<String> moveObjIds() {
    List<String> ids = selectIds( obj -> obj instanceof MoveObj );
    return new CopyOnWriteArrayList<String>(ids);
  }

  public static List<String> backgroundIds() {
    return selectIds( obj -> obj instanceof Background );
  }

  public static List<String> groundIds() {
    return selectIds( obj -> obj instanceof Ground );
  }

  public static List<String> fixedObjOtherGroundIds() {
    return selectIds( obj -> {
      return ! (obj instanceof Ground) && obj instanceof FixedObj;
    });
  }

  private static List<String> selectIds(Predicate<Obj> predicate) {
    List<String> list = new ArrayList<>();
    instances.forEach( (id,obj) -> {
      if( predicate.test(obj) ) {
        list.add(id);
      }
    });
    return list;
  }

  // ##########################
  // ###  Instance Methods  ###
  // ##########################

  public HashMap<String,Integer> upperLeft() {
    return new HashMap<String,Integer>(){ {
      put("x", positionX);
      put("y", positionY);
    } };
  }

  public HashMap<String,Integer> upperRight() {
    return new HashMap<String,Integer>(){ {
      put("x", positionX + width);
      put("y", positionY);
    } };
  }

  public HashMap<String,Integer> lowerLeft() {
    return new HashMap<String,Integer>(){ {
      put("x", positionX);
      put("y", positionY + height);
    } };
  }

  public HashMap<String,Integer> lowerRight() {
    return new HashMap<String,Integer>(){ {
      put("x", positionX + width);
      put("y", positionY + height);
    } };
  }

  /**
   * インスタンス削除
   */
  public void destructor() {
    instances.remove(this.objId);
  }

  /**
   * オブジェクトのグラフィック
   * @param g
   */
  public abstract void draw(Graphics g);

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public String getObjId() { return objId; }
  public int getPositionX() { return positionX; }
  public int getPositionY() { return positionY; }
  public int getWidth()    { return width; }
  public int getHeight()   { return height; }

  public void setPosition( int x, int y ) {
    positionX = x;
    positionY = y;
  }
}
