package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import ca.lukegrahamlandry.cosmetology.client.screen.widget.btn.CosmeticButton;
import ca.lukegrahamlandry.cosmetology.data.DataStoreImpl;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.function.Consumer;

public class CosmeticsScrollGrid extends ExtendedList<CosmeticsScrollGrid.Entry> {
    private final Consumer<Button> buttonAdder;
    public final int x;
    public final int y;
    private final int width;
    private final int height;
    private final DataStoreImpl backingDataStore;
    protected List<CosmeticInfo> cosmetics = new ArrayList<>();
    int buttonCount;
    protected List<Runnable> tooltips = new ArrayList<>();
    public Drawable hoveredTexture;
    public Drawable texture;
    public Drawable activeTexture;
    public Drawable favouriteStar;
    private final OnEquipCallback equipCallback;
    private final OnFavouriteCallback favouriteCallback;
    private Map<ResourceLocation, CosmeticButton> btns = new HashMap<>();

    public CosmeticsScrollGrid(Consumer<Button> buttonAdder, int x, int y, int width, int height, DataStoreImpl backingDataStore, OnEquipCallback sendEquipPacket, OnFavouriteCallback sendFavouritePacket){
        super(Minecraft.getInstance(), width, height, y, y + height, 49);
        this.x0 = x;
        this.x1 = this.x0 + width;
        this.buttonAdder = buttonAdder;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonCount = Math.floorDiv(width, 38);
        this.backingDataStore = backingDataStore;
        this.equipCallback = sendEquipPacket;
        this.favouriteCallback = sendFavouritePacket;

        this.setRenderBackground(false);
        this.setRenderHeader(false, 0);
        this.setRenderSelection(false);
        this.setRenderTopAndBottom(false);
    }

    public void setCosmetics(List<CosmeticInfo> sources){
        if (Minecraft.getInstance().screen == null) return;

        this.cosmetics = new ArrayList<>();
        this.cosmetics.addAll(sources);

        this.btns.clear();
        Minecraft.getInstance().screen.children().removeIf((btn) -> btn instanceof CosmeticButton);
        while (!this.children().isEmpty()){
            this.remove(0);
        }

        int xIndex = 0;
        int btnWidth = 39;
        int btnHeight = 49;
        
        List<Button> toAdd = new ArrayList<>();
        for (CosmeticInfo info : this.cosmetics){
            CosmeticButton b = new CosmeticButton(info, this.x + (xIndex * btnWidth), this.y, 39, 50, (button) -> this.setAsActive(info), (button) -> this.setAsFavourite(info), this.tooltips::add, texture, hoveredTexture, activeTexture, favouriteStar);
            b.buttonStateActive = this.backingDataStore.getActive(Minecraft.getInstance().player.getUUID()).contains(info);
            b.active = this.backingDataStore.hasUnlocked(Minecraft.getInstance().player.getUUID(), info.id);
            b.buttonStateFavourite = this.backingDataStore.isFavourite(Minecraft.getInstance().player.getUUID(), info.id);
            toAdd.add(b);
            btns.put(info.id, b);
            xIndex++;
            if (xIndex >= this.buttonCount){
                xIndex = 0;
                this.addEntry(new Entry(new ArrayList<>(toAdd)));
                toAdd.forEach(this.buttonAdder);
                toAdd.clear();
            }
        }

        if (xIndex != 0){
            Collections.reverse(toAdd);
            this.addEntry(new Entry(new ArrayList<>(toAdd)));
            toAdd.forEach(this.buttonAdder);
        }
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.tooltips.forEach(Runnable::run);
        this.tooltips.clear();
    }

    protected void setAsActive(CosmeticInfo info) {
        UUID player = Minecraft.getInstance().player.getUUID();
        boolean isWearing = this.backingDataStore.getActive(player).contains(info);

        if (isWearing){
            this.backingDataStore.clearCosmetic(player, info.id);
            this.equipCallback.sync(info.slot, null);
        } else {
            this.backingDataStore.set(player, info.id);
            this.equipCallback.sync(info.slot, info.id);
        }

        // remake buttons to update state
        this.setCosmetics(this.cosmetics);
    }

    protected void setAsFavourite(CosmeticInfo info) {
        UUID player = Minecraft.getInstance().player.getUUID();
        if (this.backingDataStore.isFavourite(player, info.id)){
            this.backingDataStore.unfavourite(player, info.id);
        } else {
            this.backingDataStore.favourite(player, info.id);
        }
        this.favouriteCallback.sync(info.slot, info.id, this.backingDataStore.isFavourite(player, info.id));

        // update state
        this.btns.get(info.id).buttonStateFavourite = this.backingDataStore.isFavourite(player, info.id);
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


    public interface OnEquipCallback {
        void sync(ResourceLocation slot, ResourceLocation id);
    }

    public interface OnFavouriteCallback {
        void sync(ResourceLocation slot, ResourceLocation id, boolean isFavourite);
    }
}
