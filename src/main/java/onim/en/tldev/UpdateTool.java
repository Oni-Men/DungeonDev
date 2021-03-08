package onim.en.tldev;

import onim.en.tldev.util.GitRelease;


public class UpdateTool {

  public static void update() {
    DungeonDev.logger.info("Checking for new version...");
    if (!GitRelease.isLatest()) {
      GitRelease.update();
    }
  }

}
