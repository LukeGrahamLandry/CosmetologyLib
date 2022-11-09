package ca.lukegrahamlandry.cosmetology.client.screen.widget.btn;

import com.mojang.blaze3d.matrix.MatrixStack;
import ca.lukegrahamlandry.cosmetology.client.screen.widget.Drawable;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class ImgButton extends Button {
    private Drawable texture;
    private Drawable hoveredTexture;
    private Drawable activeTexture;
    public boolean buttonStateActive = false;

    public ImgButton(int x, int y, int width, int height, IPressable onPress, ITooltip onTooltip, Drawable texture, Drawable hoveredTexture, Drawable activeTexture) {
        super(x, y, width, height, new StringTextComponent(""), onPress, onTooltip);
        this.texture = texture;
        this.hoveredTexture = hoveredTexture;
        this.activeTexture = activeTexture;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float p_230430_4_) {
        this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        if (this.visible) {
            Drawable t = this.isHovered ? this.hoveredTexture : (this.buttonStateActive ? this.activeTexture : this.texture);
            t.blit(stack, this.x, this.y);

            if (this.isHovered){
                this.renderToolTip(stack, mouseX, mouseY);
            }
        }
    }
}
