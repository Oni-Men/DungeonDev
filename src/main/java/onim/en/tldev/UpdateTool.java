package onim.en.tldev;

import onim.en.tldev.util.GitRelease;


public class UpdateTool {

  public static void update() {
    DungeonDev.logger.info("Checking for a new version...");
    if (!GitRelease.isLatest()) {
      DungeonDev.logger.info("Current version is not latest. The mod will be updated");
      GitRelease.update();
    }
  }

}
