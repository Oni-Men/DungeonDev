package onim.en.tldev.event;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ClickBlockEvent extends Event {

  public BlockPos blockPos;
  public EnumFacing facing;

  public ClickBlockEvent(BlockPos blockPos, EnumFacing facing) {
    this.blockPos = blockPos;
    this.facing = facing;
  }

  @Override
  public boolean isCancelable() {
    return true;
  }

}
