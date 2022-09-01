package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class PlayerCosmeticsPreviewWidget extends Widget {
    public PlayerCosmeticsPreviewWidget(int x, int y) {
        super(x, y, 1, 1, new StringTextComponent(""));
    }

    @Override
    public void render(MatrixStack p_230430_1_, int xMouse, int yMouse, float p_230430_4_) {
        int scale = 30;
        int xShift = 51;
        int yShift = 75;
        InventoryScreen.renderEntityInInventory(this.x + xShift, this.y + yShift, scale, (float)(this.x + xShift) - xMouse, (float)(this.y + yShift - 50) - yMouse, Minecraft.getInstance().player);
    }
}
