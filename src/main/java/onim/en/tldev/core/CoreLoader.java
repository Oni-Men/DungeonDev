package onim.en.tldev.core;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion("1.8.8")
@TransformerExclusions("onim.en.tldev.core")
public class CoreLoader implements IFMLLoadingPlugin {

  public static File location;

  @Override
  public String[] getASMTransformerClass() {
    return new String[] {"onim.en.tldev.core.CoreTransformer"};
  }

  @Override
  public String getModContainerClass() {
    return null;
  }

  @Override
  public String getSetupClass() {
    return null;
  }

  @Override
  public void injectData(Map<String, Object> data) {
    if (data.containsKey("coremodLocation")) {
      location = (File) data.get("coremodLocation");
    }
  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }

}
