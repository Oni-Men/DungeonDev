package onim.en.tldev.core;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.tree.InsnList;

public abstract class HookInjector {

  static class MethodIdentifier {
    public final String methodName;
    public final String methodDesc;

    public MethodIdentifier(String methodName, String methodDesc) {
      this.methodName = methodName;
      this.methodDesc = methodDesc;
    }
  }

  public final String target;
  private final Map<ObfuscateType, MethodIdentifier> map;

  public HookInjector(String target) {
    this.map = new HashMap<>();
    this.target = target;
  }

  public void registerEntry(ObfuscateType type, String methodName, String methodDesc) {
    map.put(type, new MethodIdentifier(methodName, methodDesc));
    // FMLDeobfuscatingRemapper remapper = FMLDeobfuscatingRemapper.INSTANCE;
    //
    // String mapMethodName = remapper.mapMethodName(this.target, methodName, methodDesc);
    // String mapMethodDesc = remapper.mapMethodDesc(methodDesc);
    //
    // map.put(ObfuscateType.NONE, new MethodIdentifier(mapMethodName, mapMethodDesc));
  }

  public MethodIdentifier getEntry(ObfuscateType type) {
    return map.get(type);
  }

  public abstract boolean injectHook(InsnList list, ObfuscateType type);

}
