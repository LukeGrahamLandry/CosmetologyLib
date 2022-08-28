package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class CosmeticsScrollGrid {
    private final Consumer<Button> buttonAdder;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    protected List<CosmeticInfo> cosmetics = new ArrayList<>();
    protected List<CosmeticButton> buttons = new ArrayList<>();
    public CosmeticsScrollGrid(Consumer<Button> buttonAdder, int x, int y, int width, int height){

        this.buttonAdder = buttonAdder;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setCosmetics(List<CosmeticInfo> sources){
        this.cosmetics = sources;

        for (Button b : buttons){
            b.visible = false;
            b.active = false;
        }
        buttons.clear();

        int yPos = 0;
        for (CosmeticInfo info : this.cosmetics){
            CosmeticButton b = new CosmeticButton(info, this.x + 0, this.y + yPos, 50, 50, (button) -> this.onPress(info));
            this.buttons.add(b);
            this.buttonAdder.accept(b);
            yPos += 80;
        }
    }

    protected abstract void onPress(CosmeticInfo info);
}
