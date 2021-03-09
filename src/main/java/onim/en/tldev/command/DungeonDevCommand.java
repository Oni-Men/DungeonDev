package onim.en.tldev.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import onim.en.tldev.gui.ModuleSettingGUI;
import onim.en.tldev.module.ModuleManager;
import onim.en.tldev.util.GitRelease;
import onim.en.tldev.util.Messenger;

public class DungeonDevCommand extends CommandBase {

  private static List<String> aliases = Arrays.asList("dev");

  public enum Operation {
    ON("on", "enable"), OFF("of", "disable"), UPDATE("update", "u"), UNKNOWN();

    final String[] aliases;

    Operation(String... args) {
      this.aliases = args;
    }

    public static Operation getSuitable(String text) {
      for (Operation operation : Operation.values()) {
        for (String alias : operation.aliases) {
          if (alias.equalsIgnoreCase(text)) {
            return operation;
          }
        }
      }
      return UNKNOWN;
    }
  }

  @Override
  public String getCommandName() {
    return "dungeondev";
  }

  @Override
  public List<String> getCommandAliases() {
    return aliases;
  }

  @Override
  public String getCommandUsage(ICommandSender sender) {
    return "/dungeondev <Module Name> <on|off>";
  }

  @Override
  public void processCommand(ICommandSender sender, String[] args) throws CommandException {
    if (args.length == 0) {
      // this.process((chat) -> {
      // sender.addChatMessage(chat);
      // });
      Minecraft.getMinecraft().displayGuiScreen(new ModuleSettingGUI());
    } else {
      Operation operation = Operation.getSuitable(args[0]);
      args = Arrays.copyOfRange(args, 1, args.length);
      this.processOperation((text) -> {
        sender.addChatMessage(new ChatComponentText(text));
      }, operation, args);
    }
  }

  // private void process(Messenger<IChatComponent> messenger) {
  // messenger.send(new ChatComponentText(EnumChatFormatting.GREEN + "DungeonDevの情報"));
  // messenger.send(new ChatComponentText(EnumChatFormatting.GRAY + "クリックで有効/無効を切り替え"));
  //
  // Messenger<Module> moduleStateMessenger = (module) -> {
  // StringBuffer buffer = new StringBuffer();
  // buffer.append(module.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED);
  // buffer.append(module.isEnabled() ? "✔" : "✕");
  // buffer.append(module.getName());
  // buffer.append(EnumChatFormatting.GRAY);
  // buffer.append("(" + module.getId() + ")");
  //
  // String command = MessageFormat.format("/dungeondev {0} {1}",
  // module.isEnabled() ? "off" : "on", module.getId());
  //
  // ChatComponentText component = new ChatComponentText(buffer.toString());
  // ChatStyle chatStyle = component.getChatStyle();
  // chatStyle.setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
  // component.setChatStyle(chatStyle);
  //
  // messenger.send(component);
  // };
  //
  // for (Module module : ModuleManager.getModules()) {
  // moduleStateMessenger.send(module);
  // }
  //
  // }

  private void processOperation(Messenger<String> messenger, Operation operation, String[] args) {
    switch (operation) {
      case ON:
        if (args.length != 0) {
          ModuleManager.enableModule(args[0]);
        }
        break;
      case OFF:
        if (args.length != 0) {
          ModuleManager.disableModule(args[0]);
        }
        break;
      case UPDATE:
        GitRelease.update();
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
