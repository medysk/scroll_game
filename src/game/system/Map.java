package game.system;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import game.object.Obj;
import game.object.fixed.BrickBlock;
import game.object.fixed.ClearFlag;
import game.object.fixed.RockBlock;
import game.object.fixed.Downhill;
import game.object.fixed.Flat;
import game.object.fixed.Ground;
import game.object.fixed.Uphill;
import game.object.move.enemy.Enemy1;

/**
 * �Q�[���̃}�b�v���}�b�v�t�@�C������ǂݍ���
 * @author medysk
 *
 */
public class Map {
  private final static ConcurrentHashMap<String,Obj> objs = Obj.getInstances();
  // key: Y���W, value: X���W(�n�ʍŏ㕔)
  private final static HashMap<Integer,Integer> lowerLimit = new HashMap<>();   // Y����̒n�ʂ̕\��
  private static int leftLimit;
  private static int rightLimit;
  private static List<String> groundIds;


  /**
   * �}�b�v���t�@�C������ǂݍ���
   * �}�b�v����ݒ�
   */
//TODO: ������filepath(enum?)���󂯎��
  public static void create() {
    createProc();
    createMapInformation();
  }

  /**
   * �C�ӂ�X���W��̉����l���擾����
   * @param x �C�ӂ�X���W
   * @return �����l
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

  private static void createProc() {
 // TODO: �X�^�u�Ȃ̂ō폜����
    for(int i=0; i<100; i++) {
      if(i*30 >= 900 && i*30 <= 990) continue;
      if(i*30 >= 1290 && i*30 <= 1500) {
        if( i*30 == 1290 ) Obj.create( new Downhill(i*30, 670) );
        if( i*30 == 1500) Obj.create( new Uphill(i*30, 670) );
        continue;
      }
      Obj.create( new Flat( i*30, 670 ) );
    }
    for(int i=0; i<=30; i++) {
        if(i*30+300 >= 900 && i*30+300 <= 990) continue; // ��
      if( i == 0 ) {
        Obj.create( new Uphill( i*30 + 300, 640 ) );
      } else if( i == 30) {
        Obj.create( new Downhill( i*30 + 300, 640 ) );
      } else {
        Obj.create( new Flat( i*30 + 300, 640 ) );
      }
    }
    Obj.create( new Uphill(2970, 640) );

    for(int i=0; i<5; i++) {
      Obj.create( new BrickBlock(i*30 + 650, 480) );
      Obj.create( new BrickBlock(i*30 + 200, 420) );
      Obj.create( new RockBlock(i*30 + 400, 450, true) );
      Obj.create( new RockBlock(i*30 + 400, 420, true) );
      if( i < 2 ) {
        Obj.create( new RockBlock(i*30 + 890, 500, false) );
      }
    }

    for(int i=0; i<8; i++) {
      Obj.create( new Flat( 0, i*30 + 460 ) );
    }

    // �G�L����
    Obj.create( new Enemy1(400, 300) );
    Obj.create( new Enemy1(2300, 300) );
    Obj.create( new Enemy1(2500, 300) );
    Obj.create( new ClearFlag( 2700, 590) );
  }

  private static void createMapInformation() {
    groundIds = Obj.groundIds();
    leftLimit = 0;
    rightLimit = 0;

    groundIds.forEach( id -> {
      int x = objs.get(id).getPositionX();
      int y = objs.get(id).getPositionY();

      // �}�b�v�E�[
      if( rightLimit  < x + objs.get(id).getWidth() ) {
        rightLimit = x + objs.get(id).getWidth();
      }

      // X����̒n�ʍŏ㕔
      if( ! lowerLimit.containsKey( x ) ) {
        // ����X���W���̏�񂪂Ȃ���΁A������o�^
        inputLowerLimit( (Ground)objs.get(id) );
      } else if( lowerLimit.get(x) > y ) {
        // �������㏑��
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
