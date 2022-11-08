package ca.lukegrahamlandry.cosmetology.client.screen.widget.btn;

import ca.lukegrahamlandry.cosmetology.client.screen.widget.Drawable;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ButtonRowGroup extends Widget {
    private List<ImgButton> tabButtons = new ArrayList<>();
    private ImageButton leftButton;
    private ImageButton rightButton;
    private int tabOffset = 0;
    private int currentTab = 0;
    private int btnWidth;
    private int btnCount;
    private final Consumer<Button> buttonAdder;
    private final List<Drawable> buttonIcons;
    private Consumer<Integer> onSelect;
    private int btnHeight;
    public Drawable texture;
    public Drawable hoveredTexture;
    public Drawable activeTexture;

    public ButtonRowGroup(int x, int y, int btnWidth, int btnHeight, int btnCount, Consumer<Button> buttonAdder, List<Drawable> buttonIcons, Consumer<Integer> onSelect) {
        super(x, y, 1, 1, new StringTextComponent(""));
        this.btnWidth = btnWidth;
        this.btnHeight = btnHeight;
        this.btnCount = btnCount;
        this.buttonAdder = buttonAdder;
        this.buttonIcons = buttonIcons;
        this.onSelect = onSelect;
    }

    public void init() {
        this.tabButtons.forEach((b) -> {
            b.visible = false;
            b.active = false;
        });
        this.tabButtons.clear();

        int xOffset = this.x;
        for (int i=0;i<btnCount;i++){
            int finalI = i;
            Drawable icon = this.buttonIcons.get(i + tabOffset);
            ImgButton btn = new ImgButton(this.x + xOffset, this.y, this.btnWidth, this.btnHeight, (b) -> this.buttonPress(finalI + this.tabOffset), (a, b, c, d) -> {}, new Drawable.Multi(texture, icon), new Drawable.Multi(hoveredTexture, icon), new Drawable.Multi(activeTexture, icon));
            btn.buttonStateActive = this.currentTab == (i + tabOffset);
            this.tabButtons.add(btn);
            this.buttonAdder.accept(btn);
            xOffset += btnWidth;
        }
    }

    private void buttonPress(int index) {

    }

    public static class TabBtnData {
        Drawable icon;
        private ResourceLocation data;

        public TabBtnData(Drawable icon, ResourceLocation data){
            this.icon = icon;
            this.data = data;
        }
    }
}
