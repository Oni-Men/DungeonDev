package onim.en.tldev.module.normal;

import org.apache.commons.lang3.Validate;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import onim.en.tldev.module.DungeonDevModule;
import onim.en.tldev.module.Module;
import onim.en.tldev.util.SystemUtil;

@DungeonDevModule(id = "clipboard-xyz", name = "座標取得の自動コピー")
public class ClipboardXYZ extends Module {

  private static final String CHAT_TEXT = "座標取得§r";

  @SubscribeEvent
  public void onClientChatReceived(ClientChatReceivedEvent event) {
    try {
      Validate.isTrue(event.message.getFormattedText().equals(CHAT_TEXT));

      ClickEvent clickEvent = event.message.getChatStyle().getChatClickEvent();

      Validate.notNull(clickEvent);
      Validate.isTrue(clickEvent.getAction() == Action.SUGGEST_COMMAND);

      if (SystemUtil.setClipboardText(clickEvent.getValue())) {
        event.message.appendText(EnumChatFormatting.GREEN + " ✔");
      } else {
        event.message.appendText(EnumChatFormatting.RED + " ✕");
      }
    } catch (Exception e) {
      return;
    }
  }

  @SubscribeEvent
  public void onMouseInputPre(MouseInputEvent.Pre event) {
    if (!Mouse.getEventButtonState())
      return;

    if (Mouse.getEventButton() != 0)
      return;

    Minecraft mc = Minecraft.getMinecraft();
    IChatComponent chat = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

    ClickEvent clickEvent = null;
    try {
      Validate.notNull(chat);
      Validate.isTrue(chat.getFormattedText().equals(CHAT_TEXT));

      clickEvent = chat.getChatStyle().getChatClickEvent();

      Validate.notNull(clickEvent);
      Validate.isTrue(clickEvent.getAction() == Action.SUGGEST_COMMAND);
    } catch (Exception e) {
      return;
    }

    if (SystemUtil.setClipboardText(clickEvent.getValue())) {
      chat.appendText(EnumChatFormatting.GREEN + "✔");
    } else {
      chat.appendText(EnumChatFormatting.RED + " ✕");
    }
  }
}
