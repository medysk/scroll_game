package game.object;

import java.awt.Graphics;
import java.util.HashMap;

/**
 * �I�u�W�F�N�g�̈ړ����̋O�����Ǘ�����
 * Obj�̃T�u�N���X�����Q�[���ɂ͕`�ʂ��Ȃ����ʂȃN���X
 * MoveObj�̃��b�p�[�N���X
 * @author medysk
 *
 */
public class Trajectory extends Obj {
  private HashMap<String,Double> firstPosition = new HashMap<>();
  private HashMap<String,Double> currentPosition = new HashMap<>();
  private HashMap<String,Double> distance = new HashMap<>();
  private MoveObj obj;

  /**
   * ���������s��
   * @param obj �O�����Ǘ������� MoveObj�̃C���X�^���X
   * @param denominator �O���̍ŏ��l�����߂邽�߂̕���
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
   * currentPosition ��i�߂�
   */
  public void increase() {
    currentPosition.put("x", currentPosition.get("x") + distance.get("x"));
    currentPosition.put("y", currentPosition.get("y") + distance.get("y"));
  }

  /**
   * @return currentPosition �� �l�̌ܓ����ĕԂ�
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

  // ���g��ID�ł͂Ȃ����b�v���Ă���MoveObj��ID��Ԃ�
  /* (�� Javadoc)
   * @see game.object.Obj#getObjId()
   */
  public String getObjId() {
    return obj.getObjId();
  }

  public MoveObj getMoveObj() {
    return obj;
  }

  @Override
  public void draw(Graphics g) {} // �g�p���Ȃ�

}
