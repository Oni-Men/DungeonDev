package onim.en.tldev.module.normal;

import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import onim.en.tldev.event.ClickBlockEvent;
import onim.en.tldev.module.DungeonDevModule;
import onim.en.tldev.module.Module;

@DungeonDevModule(id = "command-block-protector", name = "コマブロ保護")
public class CommandBlockProtector extends Module {

  private HashMap<BlockPos, Long> commandBlockClicked = Maps.newHashMap();

  @SubscribeEvent
  public void onClickBlock(ClickBlockEvent event) {
    long current = System.currentTimeMillis();
    World world = Minecraft.getMinecraft().theWorld;

    if (world == null) {
      return;
    }

    Block block = world.getBlockState(event.blockPos).getBlock();

    if (Block.getIdFromBlock(block) != 137) {
      return;
    }

    Long clicked = commandBlockClicked.get(event.blockPos);
    if (clicked == null) {
      commandBlockClicked.put(event.blockPos, current);
      event.setCanceled(true);
      return;
    }

    if (!Minecraft.getMinecraft().gameSettings.keyBindAttack.isPressed()) {
      event.setCanceled(true);
      return;
    }

    commandBlockClicked.remove(event.blockPos);
    event.setCanceled(current - clicked > 3000);
  }
}
