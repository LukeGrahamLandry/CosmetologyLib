package ca.lukegrahamlandry.cosmetology.client.screen;

import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import com.mojang.blaze3d.matrix.MatrixStack;
import ca.lukegrahamlandry.cosmetology.client.screen.widget.CosmeticsScrollGrid;
import ca.lukegrahamlandry.cosmetology.client.screen.widget.SearchBar;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

// dont do the actual positioning, just the helper methods the widgets need to call
public class CosmeticSelectScreen extends Screen {
    protected List<DataStore> sources;

    public CosmeticSelectScreen(List<DataStore> cosmeticsData, CosmeticsScrollGrid cosmeticsDisplay) {
        super(new StringTextComponent("cosmetics"));
        this.sources = cosmeticsData;
        this.cosmeticsDisplay = cosmeticsDisplay;
    }

    protected SearchBar search;
    protected CosmeticsScrollGrid cosmeticsDisplay;

    @Override
    protected void init() {
        super.init();

        this.search = new SearchBar(0, 0, 100, 20);
        this.cosmeticsDisplay.setCosmetics(this.getAllCosmetics());

        this.addButton(this.search);
        this.setInitialFocus(this.search);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
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
