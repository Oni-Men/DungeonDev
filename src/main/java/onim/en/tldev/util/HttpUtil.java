package onim.en.tldev.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class HttpUtil {

  public static class LatestResponse {
    public String url;
    public String tag;
    public AssetData[] assets;
  }

  public static class AssetData {
    public String url;
    public String name;
    public String browser_download_url;
  }

  public static String get(String text) {
    try {
      URL url = new URL(text);
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      return reader.lines().collect(Collectors.joining());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static <T> T fetch(String request, Class<T> type) {
    String json = get(request);
    if (json == null) {
      return null;
    }
    return new Gson().fromJson(json, type);
  }
}
