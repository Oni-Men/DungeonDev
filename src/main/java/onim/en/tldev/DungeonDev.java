package onim.en.tldev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import onim.en.tldev.command.DungeonDevCommand;
import onim.en.tldev.gui.ModuleSettingGUI;
import onim.en.tldev.module.ModuleManager;
import onim.en.tldev.util.TheLowUtil;

@Mod(modid = DungeonDev.MODID, name = DungeonDev.NAME, version = DungeonDev.VERSION,
    clientSideOnly = true)
public class DungeonDev {

  public static final String MODID = "onim.en.tldev";
  public static final String NAME = "Dungeon Dev";
  public static final String VERSION = "0.0.1";

  public static Path configPath;
  public static final Logger logger = LogManager.getLogger(NAME);
  public static final KeyBinding keyBindDevSettings =
      new KeyBinding(MODID + ".settings", Keyboard.KEY_P, MODID);

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
    configPath = event.getModConfigurationDirectory().toPath().resolve("DungeonDev");
    try {
      Files.createDirectories(configPath);
    } catch (IOException e) {
      return;
    }
  }

  @SideOnly(Side.CLIENT)
  @EventHandler
  public void init(FMLInitializationEvent event) {
    ClientCommandHandler.instance.registerCommand(new DungeonDevCommand());
    ClientRegistry.registerKeyBinding(keyBindDevSettings);
    ModuleManager.registerAll();
    ModuleManager.loadModuleSettings();
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void onKeyInput(InputEvent.KeyInputEvent event) {
    if (keyBindDevSettings.isPressed()) {
      Minecraft.getMinecraft().displayGuiScreen(new ModuleSettingGUI());
    }
  }
}
