package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchBar extends TextFieldWidget {
    public SearchBar(int x, int y, int width, int height) {
        super(Minecraft.getInstance().font, x, y, width, height, new StringTextComponent("search"));
    }

    public String getSearchTerm(){
        return this.getValue();
    }

    // TODO: support lang translations
    public List<CosmeticInfo> filter(List<CosmeticInfo> all){
        List<CosmeticInfo> results = new ArrayList<>(all);
        results.removeIf((info) -> !info.id.getPath().startsWith(getSearchTerm().toLowerCase(Locale.ROOT)));
        return results;
    }
}
