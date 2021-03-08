package onim.en.tldev.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

public class TheLowUtil {

  private static final String THELOW_SCOREBOARD_TITLE =
      EnumChatFormatting.AQUA + "===== The Low =====";

  public static boolean isPlayingTheLow() {
    Minecraft minecraft = Minecraft.getMinecraft();
    ServerData currentServerData = minecraft.getCurrentServerData();

    if (currentServerData == null) {
      return false;
    }

    if (!currentServerData.serverIP.equalsIgnoreCase("mc.eximradar.jp")) {
      return false;
    }

    WorldClient world = minecraft.theWorld;

    if (world == null) {
      return false;
    }

    Scoreboard scoreboard = world.getScoreboard();

    if (scoreboard == null) {
      return false;
    }

    ScoreObjective displaySlot = scoreboard.getObjectiveInDisplaySlot(1);

    if (displaySlot == null) {
      return false;
    }

    return THELOW_SCOREBOARD_TITLE.equals(displaySlot.getDisplayName());
  }

}
