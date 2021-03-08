package onim.en.tldev.module;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Maps;
import com.google.common.reflect.ClassPath;

import net.minecraftforge.common.MinecraftForge;
import onim.en.tldev.DungeonDev;
import onim.en.tldev.util.JavaUtil;

public class ModuleManager {

  private static final String PACKAGE_NAME = "onim.en.tldev.module";

  private static HashMap<String, Module> idToModule = Maps.newHashMap();
  private static HashMap<String, Module> nameToModule = Maps.newHashMap();

  public static void registerAll() {
    try {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      ClassPath.from(loader).getTopLevelClassesRecursive(PACKAGE_NAME).stream()
          .map(info -> info.load())
          .forEach(c -> {
            register(c);
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void register(Class<?> target) {
    try {
      Validate.isTrue(JavaUtil.isSubClassOf(Module.class, target));
    } catch (Exception e) {
      return;
    }

    try {
      Constructor<?> constructor = target.getConstructor();
      constructor.setAccessible(true);
      Module module = (Module) constructor.newInstance();
      register(module);
    } catch (Exception e) {
      DungeonDev.logger.fatal("FAILED TO CREATE INSTANCE OF " + target.getName());
      e.printStackTrace();
    }
  }

  public static void register(Module module) {
    Class<? extends Module> target = module.getClass();
    DungeonDevModule annotation = target.getDeclaredAnnotation(DungeonDevModule.class);
    if (annotation == null) {
      return;
    }

    boolean wasIdSet = JavaUtil.setFieldValueRecursion(module, "id", annotation.id());
    boolean wasNameSet = JavaUtil.setFieldValueRecursion(module, "name", annotation.name());
    boolean wasCategorySet =
        JavaUtil.setFieldValueRecursion(module, "category", annotation.category());
    
    if (!wasIdSet || !wasNameSet || !wasCategorySet) {
      DungeonDev.logger.warn("Failed to register module: " + annotation.id());
      return;
    }

    idToModule.put(annotation.id(), module);
    nameToModule.put(annotation.name(), module);

    DungeonDev.logger.info("Module Registered: " + module.getName());
  }

  public static Module getModule(String moduleId) {
    return idToModule.get(moduleId);
  }

  public static Collection<Module> getModules() {
    return idToModule.values();
  }

  public static void enableModule(Module module) {
    if (module == null) {
      return;
    }

    if (module.isEnabled()) {
      return;
    }

    MinecraftForge.EVENT_BUS.register(module);
    module.onEnable();
    DungeonDev.logger.info("Enable Module: " + module.getName());
  }

  public static void enableModule(String moduleId) {
    enableModule(getModule(moduleId));
  }

  public static void enableAll() {
    for (Module module : getModules()) {
      enableModule(module);
    }
  }

  public static void disableModule(Module module) {
    if (module == null) {
      return;
    }

    if (!module.isEnabled()) {
      return;
    }

    MinecraftForge.EVENT_BUS.unregister(module);
    module.onDisable();
    DungeonDev.logger.info("Disable Modle: " + module.getName());
  }

  public static void disableModule(String moduleId) {
    disableModule(getModule(moduleId));
  }

  public static void disableAll() {
    for (Module module : getModules()) {
      disableModule(module);
    }
  }
}
