package ca.lukegrahamlandry.cosmetology.data;

import net.minecraft.util.ResourceLocation;

public class CosmeticInfo {
    public ResourceLocation id;
    public ResourceLocation slot;

    public ResourceLocation texture;
    public ResourceLocation model;
    public ResourceLocation animation;

    public ResourceLocation getTexture(){
        return texture == null ? id : texture;
    }

    public ResourceLocation getModel(){
        return model == null ? id : model;
    }

    public ResourceLocation getAnimation(){
        return animation;
    }
}
