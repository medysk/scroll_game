package game.select;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import game.config.GameData;
import game.system.Key;
import game.system.KeyState;

public class StageSelect {
  private String stageFileDirPath; // ステージの設定ファイルのディレクトリパス
  private KeyState keyState;          // キー状態
  private List<File> fileList;         // ステージの設定ファイルを格納
  private int index;                        // ステージファイルの指定用インデックス
  private String filePath;              // 選択されたファイルのパスを格納
  private int fileCount;                  // ファイル数
  private int maxDisplayNum;           // 最大表示数

  private int first;                        // 画面に表示する最初のファイルのインデックス
  private int last;                          // 画面に表示する最後のファイルのインデックス

  /**
   * 渡された値をもとにステージの選択肢を用意する
   * @param maxDisplayNum 最大表示数
   */
  public StageSelect(int maxDisplayNum) {
    stageFileDirPath = GameData.STAGE_FILE_DIR_PATH;
    fileList = stageFileList();
    fileCount = fileList.size();
    keyState = KeyState.getInstance();
    this.maxDisplayNum = maxDisplayNum;
    execute();
  }

  /**
   * 画面に表示するファイルのインデックスのセット
   * キー操作によるインデックスの変更やファイルの選択
   */
  public void execute() {
    int[] rangeIdx = displayFileIndex();
    first = rangeIdx[0];
    last = rangeIdx[1];

    if( keyState.isKeyPressed(Key.UP) && index > first ) {
      index--;
      keyState.clear(Key.UP);
    } else if( keyState.isKeyPressed(Key.DOWN) &&  index < last ) {
      index++;
      keyState.clear(Key.DOWN);
    } else if( keyState.isKeyPressed(Key.ENTER) ||
                    keyState.isKeyPressed(Key.SPACE) ) {
      filePath = fileList.get(index).getPath();
    }

    if( keyState.isKeyPressed(Key.Z) && hasBeforePage() ) {
      index -= maxDisplayNum;
      keyState.clear(Key.Z);
    } else if ( keyState.isKeyPressed(Key.X) && hasNextPage() ) {
      index += maxDisplayNum - (index % maxDisplayNum);
      keyState.clear(Key.X);
    }

    try {
      TimeUnit.MILLISECONDS.sleep(GameData.SYSTEM_SLEEP);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * ステージ名一覧を返す
   * @return keyはステージ名の文字列、valueはステージ名の色
   */
  public HashMap<String,Color> getNames() {
    HashMap<String,Color> names = new HashMap<>();

    for(int i=first; i<=last; i++) {
      int colorCode = (i == index) ?
          GameData.CHOICES_FOCUS_TEXT_COLOR  : GameData.CHOICES_TEXT_COLOR;
      String fileName = fileList.get(i).getName().split("\\.")[0]; // 拡張子を取り除く
      names.put(fileName, new Color(colorCode));
    }
    return names;
  }

  /**
   * ユーザーが選択したステージファイルのパスを返す
   * @return
   */
  public String getFilePath() {
    return filePath == null ? null : filePath;
  }

  /**
   * ファイルリストの次ページがあるかを判定する
   * @return 次ページがあるならtrue
   */
  public boolean hasNextPage() {
    return last != fileCount - 1;
  }

  /**
   * ファイルリストの前ページがあるかを判定する
   * @return 前ページがあるならtrue
   */
  public boolean hasBeforePage() {
    return first > 0;
  }

  // 表示させるステージのインデックスを返す
  // 返り値の配列は0番目が最初の表示、1番目が最後の表示
  private int[] displayFileIndex() {
    int max = fileList.size() - 1;
    int[] result = new int[2];

    if(max < maxDisplayNum) {
      result[0] = 0;
      result[1] = max;
    } else {
      // indexよりも小さい maxDisplayChoices の最大の倍数
      result[0] = index - (index % maxDisplayNum);
      // indexよりも大きい maxDisplayChoices の最小の倍数 -1
      int prevMax = index + maxDisplayNum - (index % maxDisplayNum) - 1;
      result[1] = max < prevMax ? max : prevMax;
    }

    return result;
  }

  // ステージファイルのリストを作成
  private List<File> stageFileList() {
    File[] files = new File(stageFileDirPath).listFiles( (dir,name) -> {
      return name.matches("^stage[0-9]+.conf");
    } );
    List<File> result = Arrays.asList(files);
    Collections.sort(result);
    return result;
  }
}
