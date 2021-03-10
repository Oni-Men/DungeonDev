package onim.en.tldev.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import onim.en.tldev.gui.component.Section;
import onim.en.tldev.gui.component.ToggleButton;
import onim.en.tldev.module.Module;
import onim.en.tldev.module.ModuleManager;

public class ModuleSettingGUI extends GuiScreen {

  private List<Section> sections;
  private int frames = 0;

  public ModuleSettingGUI() {
    this.sections = new ArrayList<>();
  }

  @Override
  public void initGui() {
    super.initGui();
    sections.clear();

    for (String category : ModuleManager.getCategories()) {
      Section section = new Section(EnumChatFormatting.DARK_GREEN + "• " + category);
      for (Module module : ModuleManager.getCategoryModules(category)) {
        ToggleButton toggleButton = new ToggleButton(module.getName(), module.isEnabled());
        toggleButton.setOnClick(b -> {
          if (b) {
            ModuleManager.enableModule(module);
          } else {
            ModuleManager.disableModule(module);
          }
        });
        section.add(toggleButton);
      }
      sections.add(section);
    }
  }

  @Override
  public void onGuiClosed() {
    super.onGuiClosed();

    ModuleManager.saveModuleSettings();
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    super.drawScreen(mouseX, mouseY, partialTicks);

    this.frames++;

    DungeonDevGUI.textCenter();
    GlStateManager.pushMatrix();

    int x = Math.min(128, (int) Math.sqrt(frames * 1000));

    GlStateManager.translate(width - x, 0, 0);

    this.drawGradientRect(0, 0, 128, this.height, 0xCC505C6A, 0xCC789426);
    GlStateManager.translate(0, 16, 0);
    DungeonDevGUI.drawText(EnumChatFormatting.GREEN + "Dungeon Dev", 64, 0);
    
    DungeonDevGUI.textLeft();
    GlStateManager.translate(8, 16, 0);

    for (Section section : sections) {
      int i = section.render();
      GlStateManager.translate(0, i, 0);
    }

    GlStateManager.popMatrix();
  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    super.mouseClicked(mouseX, mouseY, mouseButton);

    int i = 32;

    if (mouseX < this.width - 128) {
      return;
    }

    for (Section section : sections) {
      if (i < mouseY && mouseY < i + section.getHeight()) {
        section.onClick(mouseX, mouseY - i);
        break;
      }
      i += section.getHeight();
    }

  }

  @Override
  public void drawBackground(int tint) {
    super.drawBackground(tint);
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

  @Override
  public void updateScreen() {
    super.updateScreen();
  }

}
