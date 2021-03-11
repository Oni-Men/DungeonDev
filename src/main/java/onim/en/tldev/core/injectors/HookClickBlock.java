package onim.en.tldev.core.injectors;

import java.text.MessageFormat;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import onim.en.tldev.core.HookInjector;
import onim.en.tldev.core.ObfuscateType;

public class HookClickBlock extends HookInjector {

  public HookClickBlock() {
    super("net.minecraft.client.multiplayer.PlayerControllerMP");

    this.registerEntry(ObfuscateType.NONE, "clickBlock",
        "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z");

    this.registerEntry(ObfuscateType.OBF, "b", "(Lcj;Lcq;)Z");
  }

  @Override
  public boolean injectHook(InsnList list, ObfuscateType type) {
    InsnList injectings = new InsnList();

    String blockPos = type == ObfuscateType.OBF ? "Lcj;" : "Lnet/minecraft/util/BlockPos;";
    String enumFacing = type == ObfuscateType.OBF ? "Lcq;" : "Lnet/minecraft/util/EnumFacing;";
    String methodDesc = MessageFormat.format("({0}{1})Z", blockPos, enumFacing);

    MethodInsnNode invokeHook = new MethodInsnNode(Opcodes.INVOKESTATIC,
        "onim/en/tldev/DungeonHooks", "onClickBlock", methodDesc, false);

    InsnNode returnNode = new InsnNode(Opcodes.IRETURN);
    LabelNode gotoNode = new LabelNode();
    
    injectings.add(new VarInsnNode(Opcodes.ALOAD, 1));
    injectings.add(new VarInsnNode(Opcodes.ALOAD, 2));
    injectings.add(invokeHook);
    injectings.add(new JumpInsnNode(Opcodes.IFEQ, gotoNode));
    injectings.add(new LdcInsnNode(false));
    injectings.add(returnNode);
    injectings.add(gotoNode);
    
    list.insert(injectings);

    return true;
  }

}
