package onim.en.tldev.command;

import java.text.MessageFormat;
import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import onim.en.tldev.module.Module;
import onim.en.tldev.module.Module.State;
import onim.en.tldev.module.ModuleManager;
import onim.en.tldev.util.Messenger;

public class DungeonDevCommand extends CommandBase {

  public static HashMap<IChatComponent, Integer> chatToChatLineID = Maps.newHashMap();

  @Override
  public String getCommandName() {
    return "dungeondev";
  }

  @Override
  public String getCommandUsage(ICommandSender sender) {
    return "/dungeondev <Module Name> <on|off>";
  }

  @Override
  public void processCommand(ICommandSender sender, String[] args) throws CommandException {
    Messenger<IChatComponent> messenger = (chat) -> {
      sender.addChatMessage(chat);
    };

    switch (args.length) {
      case 0:
        this.process(messenger);
        break;
      case 1:
        break;
      case 2:
        this.processModuleState(messenger, args[0], args[1]);
        break;
      default:
        break;
    }
  }

  private void process(Messenger<IChatComponent> messenger) {
    messenger.send(new ChatComponentText(EnumChatFormatting.GREEN + "DungeonDevの情報"));
    messenger.send(new ChatComponentText(EnumChatFormatting.GRAY + "クリックで有効/無効を切り替え"));

    Messenger<Module> moduleStateMessenger = (module) -> {
      StringBuffer buffer = new StringBuffer();
      buffer.append(module.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED);
      buffer.append(module.isEnabled() ? "✔" : "✕");
      buffer.append(module.getName());
      buffer.append(EnumChatFormatting.GRAY);
      buffer.append("(" + module.getId() + ")");

      String command = MessageFormat.format("/dungeondev {0} {1}", module.getId(), module.isEnabled() ? "off": "on");
      
      ChatComponentText component = new ChatComponentText(buffer.toString());
      ChatStyle chatStyle = component.getChatStyle();
      chatStyle.setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
      component.setChatStyle(chatStyle);

      int id = chatToChatLineID.size() + 1;
      chatToChatLineID.put(component, id);
      Minecraft.getMinecraft().ingameGUI.getChatGUI()
          .printChatMessageWithOptionalDeletion(component, id);
    };
    
    for (Module module : ModuleManager.getModules()) {
      moduleStateMessenger.send(module);
    }

  }
  
  private void processModuleState(Messenger<IChatComponent> messenger, String moduleId,
      String stateText) {
    State state = Module.State.getState(stateText);
    switch (state) {
      case ENABLE:
        ModuleManager.enableModule(moduleId);
        break;
      case DISABLE:
        ModuleManager.disableModule(moduleId);
        break;
      default:
        break;
    }
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 0;
  }

}
