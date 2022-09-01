package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import com.mojang.blaze3d.matrix.MatrixStack;
import mod.cosmetics.mcbg.ModMain;
import mod.cosmetics.mcbg.client.screen.CosmeticSelectScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class CosmeticsScrollGrid extends ExtendedList<CosmeticsScrollGrid.Entry> {
    private final Consumer<Button> buttonAdder;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    protected List<CosmeticInfo> cosmetics = new ArrayList<>();
    int buttonCount;

    public CosmeticsScrollGrid(Consumer<Button> buttonAdder, int x, int y, int width, int height){
        super(Minecraft.getInstance(), width, height, y, y + height, 48);
        this.x0 = x;
        this.x1 = this.x0 + width;
        this.buttonAdder = buttonAdder;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonCount = Math.floorDiv(width, 38);

        this.setRenderBackground(false);
        this.setRenderHeader(false, 0);
        this.setRenderSelection(false);
        this.setRenderTopAndBottom(false);
    }

    public void setCosmetics(List<CosmeticInfo> sources){
        if (Minecraft.getInstance().screen == null) return;

        this.cosmetics = new ArrayList<>(sources);
        this.cosmetics.addAll(this.cosmetics);
        this.cosmetics.addAll(this.cosmetics);
        this.cosmetics.addAll(this.cosmetics);

        while (!this.children().isEmpty()){
            this.remove(0);
        }

        Minecraft.getInstance().screen.children().removeIf((btn) -> btn instanceof CosmeticButton);

        int xIndex = 0;
        int shift = 38;

        Drawable hoveredTexture = new Drawable(CosmeticSelectScreen.WIDGET_TEXTURE, 0, 0, 0, 48, 38, 48);
        Drawable texture = new Drawable(CosmeticSelectScreen.WIDGET_TEXTURE, 0, 0, 0, 0, 38, 48);
        List<Button> toAdd = new ArrayList<>();
        for (CosmeticInfo info : this.cosmetics){
            CosmeticButton b = new CosmeticButton(info, this.x + (xIndex * shift), this.y, 38, 48, (button) -> this.onPress(info), texture, hoveredTexture);
            toAdd.add(b);
            xIndex++;
            if (xIndex >= this.buttonCount){
                xIndex = 0;
                Collections.reverse(toAdd);  // øso the later ones don't overlap tool tips
                this.addEntry(new Entry(new ArrayList<>(toAdd)));
                toAdd.forEach(this.buttonAdder);
                toAdd.clear();
            }
        }

        if (xIndex != 0){
            this.addEntry(new Entry(new ArrayList<>(toAdd)));
        }
    }

    protected void onPress(CosmeticInfo info) {
        UUID player = Minecraft.getInstance().player.getUUID();
        boolean isWearing = ModMain.clientCosmeticsData.getActive(player).contains(info);

        if (isWearing){
            ModMain.clientCosmeticsData.clearCosmetic(player, info.id);
        } else {
            ModMain.clientCosmeticsData.set(player, info.id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class Entry extends ExtendedList.AbstractListEntry<CosmeticsScrollGrid.Entry> {
        List<Widget> buttons;
        public Entry(List<Widget> buttons){
            this.buttons = buttons;
        }

        @Override
        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            this.buttons.forEach((p_238519_5_) -> {
                p_238519_5_.y = p_230432_3_;
                p_238519_5_.render(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
            });
        }
    }

    protected int getScrollbarPosition() {
        return this.x + (this.buttonCount * 38) + 10;
    }

    public int getRowWidth() {
        return this.width;
    }

    protected boolean isFocused() {
        return true;
    }
}
