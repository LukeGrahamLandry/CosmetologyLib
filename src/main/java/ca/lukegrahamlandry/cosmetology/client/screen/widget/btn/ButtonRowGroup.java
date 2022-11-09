package ca.lukegrahamlandry.cosmetology.client.screen.widget.btn;

import ca.lukegrahamlandry.cosmetology.client.screen.widget.Drawable;
import ca.lukegrahamlandry.cosmetology.client.screen.widget.btn.ImgButton;
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
    public ImgButton leftButton;
    public ImgButton rightButton;
    private int tabOffset = 0;
    public int currentTab = 0;
    private int btnWidth;
    private int btnCount;
    private final Consumer<Button> buttonAdder;
    private final List<Drawable> buttonIcons;
    private Consumer<Integer> onSelect;
    private int btnHeight;
    public Drawable texture;
    public Drawable hoveredTexture;
    public Drawable activeTexture;
    public Drawable leftIcon;
    public Drawable rightIcon;
    int offsetWithLastSelected = 0;

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
        if (this.leftButton == null){
            this.leftButton = new ImgButton(this.x - this.leftIcon.width, this.y, this.leftIcon.width, this.leftIcon.height, (btn) -> this.shift(-1), (a, b, c, d) -> {}, this.leftIcon, this.leftIcon, this.leftIcon);
            this.rightButton = new ImgButton(this.x + (this.btnCount * this.btnWidth), this.y, this.leftIcon.width, this.leftIcon.height, (btn) -> this.shift(1), (a, b, c, d) -> {}, this.rightIcon, this.rightIcon, this.rightIcon);
            this.buttonAdder.accept(this.leftButton);
            this.buttonAdder.accept(this.rightButton);
        }

        this.tabButtons.forEach((b) -> {
            b.visible = false;
            b.active = false;
        });
        this.tabButtons.clear();

        int xOffset = this.x;
        for (int i=0;i<btnCount;i++){
            int finalI = i;
            if (this.buttonIcons.size() <= i + tabOffset) break;
            Drawable icon = this.buttonIcons.get(i + tabOffset);
            ImgButton btn = new ImgButton(xOffset, this.y, this.btnWidth, this.btnHeight, (b) -> this.buttonPress(finalI + this.tabOffset), (a, b, c, d) -> {}, new Drawable.Multi(texture, icon), new Drawable.Multi(hoveredTexture, icon), new Drawable.Multi(activeTexture, icon));
            btn.buttonStateActive = this.currentTab == (i + tabOffset);
            this.tabButtons.add(btn);
            this.buttonAdder.accept(btn);
            xOffset += btnWidth;
        }
    }

    private void shift(int dir) {
        this.tabOffset += dir * this.btnCount;
        this.tabOffset = Math.max(this.tabOffset, 0);
        this.tabOffset = Math.min(this.tabOffset, this.buttonIcons.size());
        this.init();
    }

    private void buttonPress(int index) {
        this.onSelect.accept(index);
        if (this.tabOffset == this.offsetWithLastSelected) this.tabButtons.get(this.currentTab % this.btnCount).buttonStateActive = false;
        this.currentTab = index;
        this.tabButtons.get(this.currentTab % this.btnCount).buttonStateActive = true;
        this.offsetWithLastSelected = this.tabOffset;
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
