package onim.en.tldev.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import onim.en.tldev.DungeonDev;

public class GitRelease {

  private static final String BASE = "https://api.github.com/repos/Oni-Men/DungeonDev/releases/";
  private static final Pattern modFilePattern = Pattern.compile("^DungeonDev-[0-9.]{5,}.jar$");

  public static String get(String uri) {
    try {
      URL url = new URL(BASE + uri);
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      return reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static <T> T fetch(String uri, Class<T> type) {
    String json = get(uri);
    if (json == null) {
      return null;
    }
    return new Gson().fromJson(json, type);
  }

  public static LatestResponse fetchLatest() {
    return fetch("latest", LatestResponse.class);
  }

  public static boolean isLatest() {
    LatestResponse latest = fetchLatest();
    return latest == null ? false : latest.tag_name.equals(DungeonDev.VERSION);
  }

  public static void update() {
    LatestResponse latest = fetchLatest();
    
    if (latest == null) {
      DungeonDev.logger.info("Couldn't find a release");
      return;
    }
    
    if (latest.assets.length == 0) {
      DungeonDev.logger.info("Release was found. but assets wasn't include");
      return;
    }
    
    AssetData data = latest.assets[0];
    File modDir = new File(Minecraft.getMinecraft().mcDataDir, "mods");
    File file = new File(modDir, data.name);

    DungeonDev.logger.info("Trying to copy the jar file to " + file.getAbsolutePath());
    
    for (File otherMod : modDir.listFiles()) {
      Matcher matcher = modFilePattern.matcher(otherMod.getName());
      if (matcher.matches()) {
        if (!otherMod.delete()) {
        }
      }
    }

    try {
      FileUtils.copyURLToFile(new URL(data.browser_download_url), file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static class LatestResponse {
    public String url;
    public String tag_name;
    public AssetData[] assets;
  }

  public static class AssetData {
    public String url;
    public String name;
    public String browser_download_url;
  }
}
