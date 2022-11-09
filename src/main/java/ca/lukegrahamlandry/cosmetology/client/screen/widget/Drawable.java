package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

public class Drawable {
    private final ResourceLocation texture;
    public int screenX;
    public int screenY;
    public final int textureX;
    public final int textureY;
    public final int width;
    public final int height;

    public Drawable(ResourceLocation texture, int screenX, int screenY, int textureX, int textureY, int width, int height){
        this.texture = texture;
        this.screenX = screenX;
        this.screenY = screenY;
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
    }

    public Drawable(ResourceLocation texture, int textureX, int textureY, int width, int height){
        this(texture, 0, 0, textureX, textureY, width, height);
    }

    public void blit(MatrixStack stack, int x, int y){
        this.blit(stack, x, y, textureX, textureY, width, height);
    }

    public void blit(MatrixStack stack){
        this.blit(stack, screenX, screenY, textureX, textureY, width, height);
    }

    public void blit(MatrixStack stack, int screenX, int screenY, int textureX, int textureY, int width, int height) {
        Minecraft.getInstance().getTextureManager().bind(this.texture);
        Screen.blit(stack, screenX, screenY, 0, textureX, textureY, width, height, 256, 256);
    }

    public static class Multi extends Drawable {
        private final Drawable a;
        private final Drawable b;

        public Multi(Drawable a, Drawable b){
            super(null, 0, 0, 0, 0, 0, 0);
            this.a = a;
            this.b = b;
        }

        public void blit(MatrixStack stack, int x, int y){
            a.blit(stack, x, y);
            b.blit(stack, x, y);
        }

        public void blit(MatrixStack stack){
            a.blit(stack);
            b.blit(stack);
        }

        @Override
        public void blit(MatrixStack stack, int screenX, int screenY, int textureX, int textureY, int width, int height) {
            this.a.blit(stack, screenX, screenY, textureX, textureY, width, height);
            this.b.blit(stack, screenX, screenY, textureX, textureY, width, height);
        }
    }
}
