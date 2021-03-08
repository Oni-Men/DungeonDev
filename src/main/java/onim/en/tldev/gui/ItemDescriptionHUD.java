package onim.en.tldev.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class ItemDescriptionHUD extends Gui implements GuiBase {

  public ItemDescriptionHUD() {
  }

  @Override
  public String getId() {
    return "hud.itemdesc";
  }

  /**
   * if this method returns true. registry calls drawHUD every render timing
   */
  @Override
  public boolean isEnable() {
    return true;
  }

  @Override
  public String getName() {
    return "Item Description";
  }

  @Override
  public String getDescription() {
    return "Display description of item that player holds";
  }

  @Override
  public void drawHUD(float partialTick) {
    Minecraft mc = Minecraft.getMinecraft();
    if (mc == null)
      return;

    EntityPlayerSP player = mc.thePlayer;

    if (player == null)
      return;

    ItemStack heldItem = player.getHeldItem();

    if (heldItem == null)
      return;

    NBTTagCompound rootCompound = heldItem.getTagCompound();

    parseAndDraw(rootCompound, 0);
  }

  private void parseAndDraw(NBTTagCompound compound, int line) {
    for (String key : compound.getKeySet()) {

      NBTBase tag = compound.getTag(key);

      if (tag instanceof NBTTagCompound) {
        parseAndDraw((NBTTagCompound) tag, line);
      } else {

      }

    }
  }

}
