package game.object;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public abstract class Obj {
  // ###  static変数  ###
  // サブクラスをインスタンス化する際にこの変数に格納する
  private static final HashMap<String,Obj> instances = new HashMap<>();

  // サブクラスをインスタンス化する際に種類別にIDを格納する
  private static final List<String> moveObjIds = new ArrayList<>();
  private static final List<String> fixedObjOtherGroundIds = new ArrayList<>();
  private static final List<String> groundIds = new ArrayList<>();
  private static final List<String> backgroundIds = new ArrayList<>();

  private static Character character;

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
  // ###  Static Methods  ###
  public static Obj create( Obj obj ) {
    if( obj instanceof Character ) {
      character = (Character) obj;
    }
    instances.put( obj.getObjId(), obj );
    storeId( obj );
    return obj;
  }

  // ###  Instance Methods  ###

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
   * インスタンス削除用メソッド
   * @param id Objサブクラスのインスタンスに一意に与えられるobjIdを渡す
   */
  public void destructor(String id) {
    instances.remove(id);
  }

  /**
   * オブジェクトのグラフィック用メソッド
   * @param g
   */
  public abstract void draw(Graphics g);


  // ### Accessors ###

  public static Character getCharacter() {
    return character;
  }
  public static HashMap<String,Obj> getInstances() {
    return instances;
  }
  public static List<String> getMoveObjIds() {
    return moveObjIds;
  }
  public static List<String> getBackgroundIds() {
    return backgroundIds;
  }
  public static List<String> getGroundIds() {
    return groundIds;
  }
  public static List<String> getFixedObjOtherGroundIds() {
    return fixedObjOtherGroundIds;
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

  // ###  Private Methods  ###

  /**
   * インスタンスのidをObjサブクラスの種類ごとに分け、
   * それぞれのリストに格納する
   * @param id Objサブクラスのインスタンスに一意に与えられるobjIdを渡す
   */
  private static void storeId(Obj obj) {
    if( obj instanceof MoveObj ) {
      moveObjIds.add( obj.getObjId() );
    } else if( obj instanceof Background ) {
      backgroundIds.add( obj.getObjId() );
    } else if( obj instanceof Ground ) {  // GroundクラスはFixedObjのサブクラス
      groundIds.add( obj.getObjId() );
    } else if( obj instanceof FixedObj ) {
      fixedObjOtherGroundIds.add( obj.getObjId() );
    }
  }
}
