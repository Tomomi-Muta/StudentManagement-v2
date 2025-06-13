package raisetech.student.management.handler;

import java.util.function.Supplier;

public class ExceptionHandler {

  /**
   * 戻り値がある処理を例外処理付きで実行します。
   * @param action 実行する処理
   * @param defaultValue 例外発生時に返すデフォルト値
   * @return 処理の結果、またはデフォルト値
   */
  public static <T> T handle(Supplier<T> action, T defaultValue) {
    try {
      return action.get();
    } catch (Exception e) {
      System.err.println("[例外発生] " + e.getClass().getSimpleName() + ": " + e.getMessage());
      e.printStackTrace();
      return defaultValue;
    }
  }

  /**
   * 戻り値がない処理を例外処理付きで実行します。
   * @param action 実行する処理
   */
  public static void handleVoid(Runnable action) {
    try {
      action.run();
    } catch (Exception e) {
      System.err.println("[例外発生] " + e.getClass().getSimpleName() + ": " + e.getMessage());
      e.printStackTrace();
    }
  }
}
