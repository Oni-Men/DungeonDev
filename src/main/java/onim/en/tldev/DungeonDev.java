package onim.en.tldev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import onim.en.tldev.command.DungeonDevCommand;
import onim.en.tldev.gui.GuiManager;
import onim.en.tldev.module.ModuleManager;
import onim.en.tldev.util.TheLowUtil;

@Mod(modid = DungeonDev.MODID, name = DungeonDev.NAME, version = DungeonDev.VERSION,
    clientSideOnly = true)
public class DungeonDev {

  public static final String MODID = "onim.en.tldev";
  public static final String NAME = "Dungeon Dev";
  public static final String VERSION = "1.0.0";

  public static final Logger logger = LogManager.getLogger(NAME);

  private static DungeonDev instance = null;
  
  public static DungeonDev getInstance() {
    return DungeonDev.instance;
  }

  public static void ingameInfo(String msg) {
    ingameInfo(new ChatComponentText(msg));
  }

  public static void ingameInfo(IChatComponent msg) {
    GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
    if (gui != null) {
      gui.getChatGUI().printChatMessage(msg);
    }
  }

  public static boolean isEnable() {
    return TheLowUtil.isPlayingTheLow();
  }

  public DungeonDev() {
    instance = this;
  }

  @SideOnly(Side.CLIENT)
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    // Load and set the mod info
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      UpdateTool.update();
    }));
  }

  @SideOnly(Side.CLIENT)
  @EventHandler
  public void init(FMLInitializationEvent event) {
    ClientCommandHandler.instance.registerCommand(new DungeonDevCommand());

    ModuleManager.registerAll();
    ModuleManager.enableAll();
  }

  @SubscribeEvent
  public void onEntityViewRender(EntityViewRenderEvent event) {
    float partialTick = (float) event.renderPartialTicks;
    GuiManager.renderRegisteredHUD(partialTick);
  }

}
