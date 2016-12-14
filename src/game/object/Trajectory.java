package game.object;

import java.awt.Graphics;
import java.util.HashMap;

/**
 * @author medysk
 * オブジェクトの移動中の軌道を管理する
 * Objのサブクラスだがゲームには描写しない特別なクラス
 * MoveObjのラッパークラス
 */
public class Trajectory extends Obj {
  private HashMap<String,Double> firstPosition = new HashMap<>();
  private HashMap<String,Double> currentPosition = new HashMap<>();
  private HashMap<String,Double> distance = new HashMap<>();
  private MoveObj obj;

  /**
   * 初期化を行う
   * @param obj 軌道を管理したい MoveObjのインスタンス
   * @param denominator 軌道の最小値を決めるための分母
   */
  public Trajectory(MoveObj obj,int denominator) {
    this.obj = obj;

    width = obj.getWidth();
    height = obj.getHeight();

    firstPosition.put("x", (double) obj.getPrePositionX());
    firstPosition.put("y", (double) obj.getPrePositionY());

    currentPosition.put("x", firstPosition.get("x"));
    currentPosition.put("y", firstPosition.get("y"));

    distance.put("x", (double)obj.getVectorX() / denominator);
    distance.put("y", (double)obj.getVectorY() / denominator);
  }

  /**
   * currentPosition を進める
   */
  public void increase() {
    currentPosition.put("x", currentPosition.get("x") + distance.get("x"));
    currentPosition.put("y", currentPosition.get("y") + distance.get("y"));
  }

  /**
   * @return currentPosition を 四捨五入して返す
   */
  public HashMap<String,Integer> roundedCorrentPosition() {
    return new HashMap<String,Integer>() {
      {
        put( "x", (int)Math.round(currentPosition.get("x")) );
        put( "y", (int)Math.round(currentPosition.get("y")) );
      }
    };
  }

  public HashMap<String,Integer> upperLeft() {
    return new HashMap<String,Integer>(){ {
      put( "x", roundedCorrentPosition().get("x") );
      put( "y", roundedCorrentPosition().get("y") );
    } };
  }

  public HashMap<String,Integer> upperRight() {
    return new HashMap<String,Integer>(){ {
      put( "x", roundedCorrentPosition().get("x") + obj.getWidth() );
      put( "y", roundedCorrentPosition().get("y") );
    } };
  }

  public HashMap<String,Integer> lowerLeft() {
    return new HashMap<String,Integer>(){ {
      put( "x", roundedCorrentPosition().get("x") );
      put( "y", roundedCorrentPosition().get("y") + obj.getHeight() );
    } };
  }

  public HashMap<String,Integer> lowerRight() {
    return new HashMap<String,Integer>(){ {
      put( "x", roundedCorrentPosition().get("x") + obj.getWidth() );
      put( "y", roundedCorrentPosition().get("y") + obj.getHeight() );
    } };
  }

  // 自身のIDではなくラップしているMoveObjのIDを返す
  /* (非 Javadoc)
   * @see game.object.Obj#getObjId()
   */
  public String getObjId() {
    return obj.getObjId();
  }

  public MoveObj getMoveObj() {
    return obj;
  }

  @Override
  public void draw(Graphics g) {} // 使用しない

}
