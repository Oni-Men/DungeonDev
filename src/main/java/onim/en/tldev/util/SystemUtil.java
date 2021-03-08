package onim.en.tldev.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class SystemUtil {

  private static int counter = 0;

  public static boolean setClipboardText(String str) {
    try {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      StringSelection stringSelection = new StringSelection(str);
      clipboard.setContents(stringSelection, stringSelection);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static int getSessionUniqueInt() {
    return counter++;
  }

  public static int uniqueButtonId() {
    return 10000 + getSessionUniqueInt();
  }

}
