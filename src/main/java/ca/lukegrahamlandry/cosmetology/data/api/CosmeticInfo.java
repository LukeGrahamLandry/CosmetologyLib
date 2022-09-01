package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CosmeticInfo {
    public ResourceLocation id;
    public ResourceLocation slot;

    public ResourceLocation texture;
    public ResourceLocation model;
    public ResourceLocation animation;
    public List<String> bones = null;

    public CosmeticInfo(ResourceLocation id, ResourceLocation assetPath, ResourceLocation slot, List<String> bones){
        this(assetPath);
        this.id = id;
        this.slot = slot;
        this.bones = bones;
    }

    public CosmeticInfo(ResourceLocation id){
        this.id = id;
        this.texture = new ResourceLocation(id.getNamespace(), "textures/" + id.getPath() + ".png");
        this.model = new ResourceLocation(id.getNamespace(), "geo/" + id.getPath() + ".geo.json");
        this.animation = null;
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
