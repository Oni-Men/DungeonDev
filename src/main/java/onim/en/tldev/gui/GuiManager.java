package onim.en.tldev.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import onim.en.tldev.DungeonDev;

public class GuiManager {

  private static final Map<String, GuiBase> hudMap = new HashMap<String, GuiBase>();

  public static void registerHUD(GuiBase hud) {
    hudMap.put(hud.getId(), hud);
  }

  public static void unregisterHUD(GuiBase hud) {
    unregisterHUD(hud.getId());
  }

  public static void unregisterHUD(String id) {
    if (hudMap.containsKey(id)) {
      hudMap.remove(id);
      return;
    }
  }

  public static void renderRegisteredHUD(float partialTick) {
    if (!DungeonDev.isEnable())
      return;
    GuiManager.getHUDSet().forEach(hud -> {
      if (hud.isEnable()) {
        hud.drawHUD(partialTick);
      }
    });
  }

  public static Map<String, GuiBase> getHUDMap() {
    return hudMap;
  }

  public static Collection<GuiBase> getHUDSet() {
    return getHUDMap().values();
  }
}
