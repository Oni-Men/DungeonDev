package onim.en.tldev;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import onim.en.tldev.event.ClickBlockEvent;

public class DungeonHooks {

  public static boolean onClickBlock(BlockPos blockPos, EnumFacing facing) {
    ClickBlockEvent event = new ClickBlockEvent(blockPos, facing);
    return MinecraftForge.EVENT_BUS.post(event);
  }

}
