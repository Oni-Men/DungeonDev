package onim.en.tldev.module.normal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.Validate;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import onim.en.tldev.module.DungeonDevModule;
import onim.en.tldev.module.Module;
import onim.en.tldev.util.SystemUtil;

@DungeonDevModule(id = "copy-command-block-content", name = "コマブロの内容コピー")
public class CopyCommandBlockContent extends Module {

  private final int buttonId;

  public CopyCommandBlockContent() {
    this.buttonId = SystemUtil.uniqueButtonId();
  }

  @SubscribeEvent
  public void onInitGui(InitGuiEvent.Post event) {
    try {
      Validate.isTrue(event.gui instanceof GuiCommandBlock);
    } catch (Exception e) {
      return;
    }

    GuiButton button = new GuiButton(this.buttonId, 8, 8, 100, 20, "Copy Command");
    event.buttonList.add(button);
  }

  @SubscribeEvent
  public void onActionPerformed(ActionPerformedEvent.Post event) {
    try {
      Validate.isTrue(event.gui instanceof GuiCommandBlock);
      Validate.isTrue(event.button.id == this.buttonId);
    } catch (Exception e) {
      return;
    }

    GuiCommandBlock gui = (GuiCommandBlock) event.gui;
    String commandText = this.getCommandText(gui);

    if (commandText == null) {
      System.out.println("null");
    }

    SystemUtil.setClipboardText(commandText);
  }

  private String getCommandText(GuiCommandBlock gui) {
    for (Field f : gui.getClass().getDeclaredFields()) {

      if (!Modifier.isPrivate(f.getModifiers()))
        continue;

      if (!GuiTextField.class.isAssignableFrom(f.getType()))
        continue;

      f.setAccessible(true);
      try {
        Object got = f.get(gui);

        if (got instanceof GuiTextField) {
          return ((GuiTextField) got).getText();
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
