package ca.lukegrahamlandry.cosmetology.data.type;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import net.minecraft.util.ResourceLocation;

import java.util.List;

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
}
