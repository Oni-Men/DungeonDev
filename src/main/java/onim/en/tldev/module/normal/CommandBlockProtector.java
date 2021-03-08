package onim.en.tldev.module.normal;

import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import onim.en.tldev.module.DungeonDevModule;
import onim.en.tldev.module.Module;

@DungeonDevModule(id = "command-block-protector", name = "コマブロ保護")
public class CommandBlockProtector extends Module {

  private HashMap<BlockPos, Long> commandBlockClicked = Maps.newHashMap();

  @SubscribeEvent
  public void onBlockBreak(BlockEvent.BreakEvent event) {
    Block block = event.state.getBlock();
    BlockPos pos = event.pos;
    long current = System.currentTimeMillis();
    if (Block.getIdFromBlock(block) == 137) {
      if (commandBlockClicked.containsKey(pos)) {
        Long clicked = commandBlockClicked.remove(pos);
        event.setCanceled(current - clicked > 3000);
      } else {
        commandBlockClicked.put(pos, current);
        event.setCanceled(true);
      }
    }
  }

}
