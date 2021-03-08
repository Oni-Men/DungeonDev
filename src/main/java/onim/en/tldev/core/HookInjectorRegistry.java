package onim.en.tldev.core;

import java.util.Set;

import com.google.common.collect.HashMultimap;

import net.minecraftforge.fml.common.FMLLog;

public class HookInjectorRegistry {

  private static HashMultimap<String, HookInjector> injectors = HashMultimap.create();

  public static void registerInjector(HookInjector injector) {
    injectors.put(injector.target, injector);
  }

  public static Set<HookInjector> getInjectorsFor(String transformedName) {
    return injectors.get(transformedName);
  }

  public static boolean hasInjectorFor(String transformedName) {
    return injectors.containsKey(transformedName);
  }

  static {
    FMLLog.info("Register all hook injectors");
  }
}
