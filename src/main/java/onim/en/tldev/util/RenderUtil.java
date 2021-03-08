package onim.en.tldev.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtil {

  public static void renderRect(int x, int y, int width, int height, int color) {

    float a = (float) (color >> 24 & 255) / 255F;
    float r = (float) (color >> 16 & 255) / 255F;
    float g = (float) (color >> 8 & 255) / 255F;
    float b = (float) (color & 255) / 255F;

    GlStateManager.disableTexture2D();
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();

    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

    GlStateManager.color(r, g, b, a);

    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer buffer = tessellator.getWorldRenderer();
    buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    buffer.func_181662_b(x, y + height, 1);
    buffer.func_181662_b(x + width, y + height, 1);
    buffer.func_181662_b(x + width, y, 1);
    buffer.func_181662_b(x, y, 1);

    tessellator.draw();

    GlStateManager.disableBlend();
    GlStateManager.enableTexture2D();
  }

}
