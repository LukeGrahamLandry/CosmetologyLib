package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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

    public List<CosmeticInfo> filter(List<CosmeticInfo> all){
        String search = getSearchTerm().toLowerCase(Locale.ROOT);
        List<CosmeticInfo> results = new ArrayList<>(all);
        results.removeIf((info) -> !info.id.getPath().startsWith(search) && !new TranslationTextComponent("cosmetic." + info.id.getNamespace() + "." + info.id.getPath()).getString().toLowerCase(Locale.ROOT).startsWith(search));
        return results;
    }
}
