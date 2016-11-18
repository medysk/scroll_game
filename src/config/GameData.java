package config;

/**
 * @author medysk
 * 設定データ
 */
// TODO: yamlかjson5かcsonに置き換える
public class GameData {
  public static final int SYSTEM_SLEEP = 15;    // 1フレームごとのスリープ（ミリ秒）
  public static final int MAP_FILE_VALID_RECORD = 20; // マップファイルの有効行数

  public static final int BASE_OBJ_WIDTH = 30;  // ステージ配置オブジェクト(地面、ブロック)
  public static final int BASE_OBJ_HEIGHT = 30; // ステージ配置オブジェクト(地面、ブロック)

  public static final int PANEL_WIDTH = 1000;   // パネルの幅
  public static final int PANEL_HALF_WIDTH = PANEL_WIDTH / 2;
  public static final int PANEL_HEIGHT =
      MAP_FILE_VALID_RECORD * BASE_OBJ_HEIGHT;   // パネルの高さ

  public static final int BACKGROUND_COLOR = 0x1E90FF; // 背景色

  public static final int CHARACTER_WIDTH = 28;     // キャラクターのサイズ
  public static final int CHARACTER_HEIGHT = 50;    // キャラクターのサイズ
  public static final int CHARACTER_MIN_SPEED = 1;  // 最小スピード
  public static final int CHARACTER_MAX_SPEED = 10; // 最大スピード
  public static final int CHARACTER_FALL_VELOCITY = 1;      // 落下速度増加値
  public static final int CHARACTER_MAX_FALL_VELOCITY = 27; // 最大落下速度
  public static final int CHARACTER_VERTICAL_LEAP = 20;     // ジャンプ力
  public static final int CHARACTER_COLOR = 0x0000ff;       // カラー

  public static final int ENEMY1_WIDTH = 28;    // サイズ
  public static final int ENEMY1_HEIGHT = 28;   // サイズ
  public static final int ENEMY1_MIN_SPEED = 1; // 最小スピード
  public static final int ENEMY1_MAX_SPEED = 2; // 最大スピード
  public static final int ENEMY1_FALL_VELOCITY = 1;       // 落下速度増加値
  public static final int ENEMY1_MAX_FALL_VELOCITY = 27;  // 最大落下速度
  public static final int ENEMY1_VERTICAL_LEAP = 12;      // ジャンプ力
  public static final int ENEMY1_JUMP_INTERVAL = 100;     // ジャンプ間隔
  public static final int ENEMY1_COLOR = 0xff4500;        // カラー

  public static final int BRICK_BLOCK_MAIN_COLOR = 0x8b4513;  // メインの色
  public static final int BRICK_BLOCK_FRAME_COLOR = 0x3c3c3c; // 枠の色
  public static final int BRICK_BLOCK_LINE_COLOR = 0x606060;  // ラインの色

  public static final int ROCK_BLOCK_MAIN_COLOR = 0x808080;   // メインの色
  public static final int ROCK_BLOCK_FRAME_COLOR = 0x202020;  // 枠の色
}
