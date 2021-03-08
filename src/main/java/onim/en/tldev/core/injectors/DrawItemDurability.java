package onim.en.tldev.core.injectors;

import org.objectweb.asm.tree.InsnList;

import onim.en.tldev.core.HookInjector;
import onim.en.tldev.core.ObfuscateType;

public class DrawItemDurability extends HookInjector {

  public DrawItemDurability() {
    super("net.minecraft.client.gui.GuiContainer");
  }

  @Override
  public boolean injectHook(InsnList list, ObfuscateType type) {
    // TODO Auto-generated method stub
    return false;
  }

}
