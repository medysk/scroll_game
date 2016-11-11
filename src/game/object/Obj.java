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
 * �Q�[���ɕ`�ʂ���S�ẴI�u�W�F�N�g�̃X�[�p�[�N���X
 * �T�u�N���X��MoveObj��FixedObj�ABackgound������
 * �T�u�N���X���C���X�^���X������ۂ� create(Obj obj) ���g�p����
 *
 * @author medysk
 *
 */
public abstract class Obj  implements Cloneable {
  // ###  static�ϐ�  ###

  // �T�u�N���X���C���X�^���X������ۂɂ��̕ϐ��Ɋi�[����
  private static ConcurrentHashMap<String,Obj> instances = new ConcurrentHashMap<>();

  // ###  instance�ϐ�  ###
  // �I�u�W�F�N�g�̈�ӂ�ID
  protected String objId;

  // �����̈ʒu
  protected int initialPositionX;
  protected int initialPositionY;

  // ���݂̍��W
  protected int positionX;
  protected int positionY;

  // �I�u�W�F�N�g�̃T�C�Y
  protected int height;
  protected int width;

  // �摜��"java.awt.Graphics"�Ȃ̂�
  protected boolean isImg;

  /**
   * Trajectory�p�̃_�~�[�R���X�g���N�^
   */
  public Obj() {}

  /**
   * Trajectory�ȊO�̃Q�[���`�ʗp�̃R���X�g���N�^
   * @param positionX �����ʒu
   * @param positionY �����ʒu
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
   * �C���X�^���X�폜
   */
  public void destructor() {
    instances.remove(this.objId);
  }

  /**
   * �I�u�W�F�N�g�̃O���t�B�b�N
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
