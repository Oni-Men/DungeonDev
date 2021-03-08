package onim.en.tldev;

import javax.swing.JFrame;

import onim.en.tldev.util.HttpUtil;


public class UpdateTool extends JFrame {

  private static final String UPDATE_URL = "htttps://github.com/";

  public static void main(String[] args) {
    UpdateTool updateTool = new UpdateTool("Dungeon Dev - Update Tool");
    
    HttpUtil.get(null);
  }

  public UpdateTool(String title) {
    super(title);
  }

}
