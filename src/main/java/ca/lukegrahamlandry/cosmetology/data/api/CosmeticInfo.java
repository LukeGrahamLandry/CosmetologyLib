package ca.lukegrahamlandry.cosmetology.data.api;

import com.google.gson.*;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.List;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

public abstract class CosmeticInfo {
    public final ResourceLocation id;
    public final ResourceLocation slot;
    public final String clazz;

    public CosmeticInfo(ResourceLocation id, ResourceLocation slot){
        this.id = id;
        this.slot = slot;
        this.clazz = this.getClass().getName();
    }

    public static class Serializer implements JsonDeserializer<CosmeticInfo>, JsonSerializer<CosmeticInfo> {
        public CosmeticInfo deserialize(JsonElement input, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            String className = input.getAsJsonObject().get("clazz").getAsString();
            try {
                Class<?> clazz = Class.forName(className);
                return (CosmeticInfo) GSON.fromJson(input, clazz);
            } catch (ClassNotFoundException e) {
                System.out.println("Cannot deserialize CosmeticInfo json: " + input);
                e.printStackTrace();
                return null;
            }
        }

        public JsonElement serialize(CosmeticInfo input, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
            return GSON.toJsonTree(input);
        }
    }
}
