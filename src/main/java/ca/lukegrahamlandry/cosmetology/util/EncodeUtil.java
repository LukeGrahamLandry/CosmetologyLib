package ca.lukegrahamlandry.cosmetology.util;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;

public class EncodeUtil {
    /*
     *  A Gson encoder with some common type adapters I need.
     *  - By default resource locations are json encoded as {path: "", namespace: ""} but json cannot have objects as keys
     *  - Since I want to serialize the generic cosmetic info but be able to get it back as the correct subtype, it saves its classname as a field
     */
    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(CosmeticInfo.class, new CosmeticInfo.Serializer())
            .create();
}
