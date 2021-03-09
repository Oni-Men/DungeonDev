package onim.en.tldev.gui.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import onim.en.tldev.gui.DungeonDevGUI;

public class Section extends DungeonDevGUI {

  private String title;
  private List<DungeonDevGUI> elements;
  
  public Section(String title) {
    this.title = title;
    this.elements = new ArrayList<>();
  }

  public Section(String title, Collection<DungeonDevGUI> initial) {
    this.title = title;
    this.elements = new ArrayList<>(initial);
  }

  public void add(DungeonDevGUI e) {
    this.elements.add(e);
  }

  public int render() {
    int i = 0;

    DungeonDevGUI.drawText(title, 0, 0);

    i += 12;
    GlStateManager.translate(8, 12, 0);

    for (DungeonDevGUI element : elements) {
      i += element.render();
      GlStateManager.translate(0, element.getHeight(), 0);
    }

    return i;
  }

  @Override
  public void onClick(int x, int y) {
    int i = 12;
    for (DungeonDevGUI e : this.elements) {
      if (i < y && y < i + e.getHeight()) {
        e.onClick(x, y - i);
        break;
      }
      i += e.getHeight();
    }
  }

  @Override
  public int getWidth() {
    return 100;
  }

  @Override
  public int getHeight() {
    return 12 + this.elements.stream().mapToInt(e -> e.getHeight()).sum();
  }

}
