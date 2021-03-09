package onim.en.tldev.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class DungeonDevGUI {

  public enum TextAlign {
    LEFT, RIGHT, CENTER;
  }
  public static TextAlign align;

  public boolean visible;

  public boolean isVisible() {
    return this.visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public abstract int render();

  public abstract int getWidth();

  public abstract int getHeight();

  public void onClick(int x, int y) {

  }

  public static void drawText(String text, int x, int y) {
    Minecraft minecraft = Minecraft.getMinecraft();
    FontRenderer fontRenderer = minecraft.fontRendererObj;
    if (fontRenderer == null) {
      return;
    }
    int width = fontRenderer.getStringWidth(text);
    switch (align) {
      case CENTER:
        fontRenderer.drawStringWithShadow(text, x - width / 2, y, 0xFFFFFF);
        break;
      case LEFT:
        fontRenderer.drawStringWithShadow(text, x, y, 0xFFFFFF);
        break;
      case RIGHT:
        fontRenderer.drawStringWithShadow(text, x - width, y, 0xFFFFFF);
        break;
      default:
        break;
    }
  }

  public static int textWidth(String text) {
    Minecraft minecraft = Minecraft.getMinecraft();
    FontRenderer fontRenderer = minecraft.fontRendererObj;
    return fontRenderer == null ? 0 : fontRenderer.getStringWidth(text);
  }

  public static void textCenter() {
    align = TextAlign.CENTER;
  }

  public static void textLeft() {
    align = TextAlign.LEFT;
  }

  public static void textRight() {
    align = TextAlign.RIGHT;
  }
}
