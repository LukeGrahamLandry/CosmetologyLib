package ca.lukegrahamlandry.cosmetology.data.api;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.util.EncodeUtil;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

/**
 * A single cosmetic.
 * Subclasses will define how it will be rendered in game.
 */
public abstract class CosmeticInfo {
    public final ResourceLocation id;
    public final ResourceLocation slot;
    public final String clazz;

    public CosmeticInfo(ResourceLocation id, ResourceLocation slot){
        this.id = id;
        this.slot = slot;
        this.clazz = this.getClass().getName();
    }

    /**
     * Cosmetics may refuse to be registered if a mod required for their rendering is not present.
     */
    public boolean isRegisterAllowed(){
        return true;
    }

    public void guiButtonRender(int centerX, int centerY, int width, int height, boolean glint, boolean selected) {
        CosmetologyApi.errorLog("Cannot render cosmetic button for: " + this);
    }

    public TextComponent getDisplayTitle() {
        return new TranslationTextComponent("cosmetic." + this.id.getNamespace() + "." + this.id.getPath());
    }

    public TextComponent getDisplayDescription() {
        return new TranslationTextComponent("cosmetic." + this.id.getNamespace() + "." + this.id.getPath() + ".desc");
    }

    /**
     * Saves type information when encoding as json so when we decode a CosmeticInfo we get the correct subclass.
     */
    public static class Serializer implements JsonDeserializer<CosmeticInfo>, JsonSerializer<CosmeticInfo> {
        /**
         * If you add a subclass of CosmeticInfo, then later move or rename it
         * but want to still maintain compatibility with old json exports of your cosmetics,
         * add an entry here that redirects your old class to the new class (will only fire if the old class cannot be found).
         * Make sure you set reasonable defaults if you change the data representation.
         * This can also be used to compress your json representations by saving a shorter unique string instead of the full class name.
         */
        public static Map<String, Class<? extends CosmeticInfo>> LEGACY_FIXERS = new HashMap<>();
        public CosmeticInfo deserialize(JsonElement input, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            String className = input.getAsJsonObject().get("clazz").getAsString();
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                if (LEGACY_FIXERS.containsKey(className)){
                    clazz = LEGACY_FIXERS.get(className);
                } else {
                    CosmetologyApi.errorLog("Cannot deserialize CosmeticInfo json: " + input);
                    e.printStackTrace();
                    return null;
                }
            }

            // not a loop because typeAdapters are registered for specific classes not automatically all subclasses
            return (CosmeticInfo) GSON.fromJson(input, clazz);
        }

        public JsonElement serialize(CosmeticInfo input, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
            return GSON.toJsonTree(input);
        }
    }
}
