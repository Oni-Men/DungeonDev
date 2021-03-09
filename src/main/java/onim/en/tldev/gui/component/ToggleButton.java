package onim.en.tldev.gui.component;

import java.util.function.Consumer;

import net.minecraft.util.EnumChatFormatting;
import onim.en.tldev.gui.DungeonDevGUI;

public class ToggleButton extends DungeonDevGUI {

  private String label;
  private String prefix;
  private boolean state;
  private Consumer<Boolean> action;

  public ToggleButton(String label) {
    this(label, false);
  }

  public ToggleButton(String label, boolean state) {
    this.label = label;
    this.state = state;

    this.syncPrefix();
  }

  public int render() {
    DungeonDevGUI.textLeft();
    DungeonDevGUI.drawText(prefix + " " + label, 0, 0);
    return this.getHeight();
  }

  @Override
  public int getWidth() {
    return DungeonDevGUI.textWidth(this.prefix + this.label);
  }

  @Override
  public int getHeight() {
    return 12;
  }

  @Override
  public void onClick(int x, int y) {
    this.state = !this.state;
    this.syncPrefix();
    this.action.accept(this.state);
  }

  public void setOnClick(Consumer<Boolean> action) {
    this.action = action;
  }

  private void syncPrefix() {
    this.prefix = this.state ? EnumChatFormatting.GREEN + "✔" : EnumChatFormatting.RED + "✕";
  }
}
