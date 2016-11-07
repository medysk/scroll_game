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
 * �Q�[���ɕ`�ʂ���S�ẴI�u�W�F�N�g�̃X�[�p�[�N���X
 * �T�u�N���X��MoveObj��FixedObj�ABackgound������
 * �T�u�N���X���C���X�^���X������ۂ� create(Obj obj) ���g�p����
 *
 * @author medysk
 *
 */
public abstract class Obj {
  // ###  static�ϐ�  ###
  // �T�u�N���X���C���X�^���X������ۂɂ��̕ϐ��Ɋi�[����
  private static final HashMap<String,Obj> instances = new HashMap<>();

  // �T�u�N���X���C���X�^���X������ۂɎ�ޕʂ�ID���i�[����
  private static final List<String> moveObjIds = new ArrayList<>();
  private static final List<String> fixedObjOtherGroundIds = new ArrayList<>();
  private static final List<String> groundIds = new ArrayList<>();
  private static final List<String> backgroundIds = new ArrayList<>();

  private static Character character;

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
   * �C���X�^���X�폜�p���\�b�h
   * @param id Obj�T�u�N���X�̃C���X�^���X�Ɉ�ӂɗ^������objId��n��
   */
  public void destructor(String id) {
    instances.remove(id);
  }

  /**
   * �I�u�W�F�N�g�̃O���t�B�b�N�p���\�b�h
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
   * �C���X�^���X��id��Obj�T�u�N���X�̎�ނ��Ƃɕ����A
   * ���ꂼ��̃��X�g�Ɋi�[����
   * @param id Obj�T�u�N���X�̃C���X�^���X�Ɉ�ӂɗ^������objId��n��
   */
  private static void storeId(Obj obj) {
    if( obj instanceof MoveObj ) {
      moveObjIds.add( obj.getObjId() );
    } else if( obj instanceof Background ) {
      backgroundIds.add( obj.getObjId() );
    } else if( obj instanceof Ground ) {  // Ground�N���X��FixedObj�̃T�u�N���X
      groundIds.add( obj.getObjId() );
    } else if( obj instanceof FixedObj ) {
      fixedObjOtherGroundIds.add( obj.getObjId() );
    }
  }
}
