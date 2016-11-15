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

import game.Stage;
import game.object.Obj;
import game.object.fixed.Downhill;
import game.object.fixed.Flat;
import game.object.fixed.Ground;
import game.object.fixed.RockBlock;
import game.object.fixed.Uphill;
import game.object.move.player.Character;

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

  private static FileReader fr;
  private static BufferedReader br;


  /**
   * �}�b�v���t�@�C������ǂݍ���
   * �}�b�v����ݒ�
   */
  public static void create(String filePath) {
    List<String[]> records = readFile(filePath);

    createObj(records);

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

  /**
   * �t�@�C����ǂݍ���
   * @param filePath
   * @return �t�@�C����1�s�����X�g��1�v�f�A�z��ɂ�1�s�𕪊������v�f������
   */
  private static List<String[]> readFile(String filePath) {
    try {
      fr = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      System.out.println("�t�@�C�����J���܂���B�w�肵���p�X���Ԉ���Ă��܂��B");
    }
    br = new BufferedReader(fr);

    String line;
    int lineLength = 0;
    List<String[]> records = new ArrayList<>();

    try {
      // map{n}.conf ��20�s�܂ł��}�b�v�̋L�q�G���A�Ƃ��Ă���
      while ( (line = br.readLine()) != null && records.size() <= 20) {
        records.add( line.split("") );
        if(lineLength < records.get(records.size() - 1).length ) {
          lineLength = records.get(records.size() - 1).length;
        }
      }

      if( records.size() < 20 ) {
        throw new IOException("map{n}.conf ��20�s�����ł�");
      }

      br.close();
      fr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return records;
  }

  private static void createObj( List<String[]> records ) {
    // TODO: �ݒ�t�@�C������ǂݍ���
    final int baseHeight = 30;
    final int baseWidth = 30;
    int recordLength = 0;
    int columnLength;

    // �}�b�v�t�@�C��1�s���Ƃ̃��[�v
    for( String[] record : records ) {
      int y = baseHeight * recordLength++; // �I�u�W�F�N�g��Y���W
      int x = 0;                           // �I�u�W�F�N�g��X���W

      columnLength = 0;
      // �}�b�v�t�@�C��1�s�̈ꕶ�����Ƃ̃��[�v
      for( String symbol : record ) {
        x = baseWidth * columnLength++;

        if( ! ObjMap.containsKey(symbol) ) { continue; }

        Obj obj = instantiation(symbol, x, y);
        // �p�����[�^���K�v�ȃC���X�^���X�Ȃ�A�Z�b�g����
        if(obj instanceof Character) {
          ((Character) obj).setKeyState(Stage.getKeyState());
        } else if( symbol.equals("I") ) {
          ((RockBlock) obj).invisibility();
        }

        // Ground�̃T�u�N���X��Block�̃T�u�N���X�ȊO�͈ʒu�␳���s��
        positionCorrection(obj);
      }
    }
  }

  // �ʒu�␳
  private static void positionCorrection(Obj obj) {
    // TODO: 30�̓x�[�X�̃I�u�W�F�N�g�T�C�Y�@�ݒ�t�@�C������ǂݍ���
    int dif = obj.getHeight() - 30;
    obj.setPosition(obj.getPositionX(), obj.getPositionY() - dif);
  }

  // �C���X�^���X�̍쐬
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
