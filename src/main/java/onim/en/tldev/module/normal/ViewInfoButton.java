package onim.en.tldev.module.normal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import onim.en.tldev.module.DungeonDevModule;
import onim.en.tldev.module.Module;
import onim.en.tldev.util.SystemUtil;

@DungeonDevModule(id = "view-info-button", name = "ViewInfoを実行するボタン")
public class ViewInfoButton extends Module {

  private final int buttonId = SystemUtil.uniqueButtonId();

  @SubscribeEvent
  public void onInitGui(InitGuiEvent.Post event) {
    boolean isSurvivalInventory = event.gui instanceof GuiInventory;
    boolean isCreativeInventory = event.gui instanceof GuiContainerCreative;

    if (isSurvivalInventory || isCreativeInventory) {
      GuiButton button = new GuiButton(buttonId, 8, 8, 100, 20, "Viewinfo");
      event.buttonList.add(button);
    }
  }

  @SubscribeEvent
  public void onActionPerformed(ActionPerformedEvent event) {
    if (event.button.id == buttonId) {
      Minecraft.getMinecraft().thePlayer.sendChatMessage("/tl");
    }
  }
}
