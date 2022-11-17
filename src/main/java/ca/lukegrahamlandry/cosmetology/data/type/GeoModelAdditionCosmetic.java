package ca.lukegrahamlandry.cosmetology.data.type;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.util.ModListHelper;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * A cosmetic that will be rendered by adding a geckolib model to the player.
 * The model parts will move and rotate with the players body parts if the upper parent bones are named correctly.
 * - "helmet", "chestplate", "rightArm", "leftArm", "rightLeg", "leftLeg", "rightBoot", "leftBoot"
 * The 'bones' list defines which of the above bones will be displayed (any other values will be ignored). If the list is null, all will be displayed.
 * - This allows using the same model json to expose multiple slots of the same armor as separate cosmetics.
 * Animations are not yet supported.
 */
public class GeoModelAdditionCosmetic extends CosmeticInfo {
    public ResourceLocation texture;
    public ResourceLocation model;
    public ResourceLocation animation;
    public List<String> bones;

    public GeoModelAdditionCosmetic(ResourceLocation id, ResourceLocation assetPath, ResourceLocation slot, List<String> bones){
        super(id, slot);
        this.texture = new ResourceLocation(assetPath.getNamespace(), "textures/" + assetPath.getPath() + ".png");
        this.model = new ResourceLocation(assetPath.getNamespace(), "geo/" + assetPath.getPath() + ".geo.json");
        this.animation = null;
        this.bones = bones;
    }

    public GeoModelAdditionCosmetic(ResourceLocation id, ResourceLocation slot){
        this(id, id, slot, null);
    }

    public ResourceLocation getTexture(){
        return texture;
    }

    public ResourceLocation getModel(){
        return model;
    }

    public ResourceLocation getAnimation(){
        return animation;
    }

    /**
     * If Geckolib is not installed we will be unable to render.
     * Instead of crashing when someone equips it, return false here and gracefully handle not classloading any geckolib classes.
     */
    @Override
    public boolean isRegisterAllowed() {
        return ModListHelper.isGeckolibAvailable() && super.isRegisterAllowed();
    }
}
