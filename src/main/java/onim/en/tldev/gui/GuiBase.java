package onim.en.tldev.gui;

public interface GuiBase {

  public String getId();

  public boolean isEnable();

  public String getName();

  public String getDescription();

  public void drawHUD(float partialTick);

}
