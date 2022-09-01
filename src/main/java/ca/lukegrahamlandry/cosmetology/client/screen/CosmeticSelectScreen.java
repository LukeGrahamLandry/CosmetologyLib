package ca.lukegrahamlandry.cosmetology.client.screen;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import com.mojang.blaze3d.matrix.MatrixStack;
import mod.cosmetics.mcbg.ModMain;
import mod.cosmetics.mcbg.client.screen.widget.CosmeticsScrollGrid;
import mod.cosmetics.mcbg.client.screen.widget.PlayerCosmeticsPreviewWidget;
import mod.cosmetics.mcbg.client.screen.widget.SearchBar;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

// don't do the actual positioning, just the helper methods the widgets need to call
public class CosmeticSelectScreen extends Screen {
    protected List<DataStore> sources;
    public static ResourceLocation WIDGET_TEXTURE = new ResourceLocation(ModMain.MOD_ID, "textures/gui/elements.png");

    public CosmeticSelectScreen(List<DataStore> cosmeticsData) {
        super(new StringTextComponent("cosmetics"));
        this.sources = cosmeticsData;
    }

    protected SearchBar search;
    protected CosmeticsScrollGrid cosmeticsDisplay;
    protected PlayerCosmeticsPreviewWidget playerPreview;

    @Override
    protected void init() {
        super.init();

        this.search = new SearchBar(0, 0, 100, 20);
        this.cosmeticsDisplay = new CosmeticsScrollGrid(this::addWidget, 0, 30, 200, 200);
        this.cosmeticsDisplay.setCosmetics(this.getAllCosmetics());
        this.playerPreview = new PlayerCosmeticsPreviewWidget(200, 0);

        this.addButton(this.playerPreview);
        this.addButton(this.search);
        this.setInitialFocus(this.search);
        this.addWidget(this.cosmeticsDisplay);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.cosmeticsDisplay.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    protected List<CosmeticInfo> getAllCosmetics() {
        List<CosmeticInfo> c = new ArrayList<>();
        this.sources.forEach((data) -> c.addAll(data.getAll()));
        // TODO: filter based on which are unlocked
        return c;
    }

    protected void updateSearch(){
        this.cosmeticsDisplay.setCosmetics(this.search.filter(this.getAllCosmetics()));
    }

    @Override
    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        boolean result = super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
        this.updateSearch();
        return result;
    }
}
