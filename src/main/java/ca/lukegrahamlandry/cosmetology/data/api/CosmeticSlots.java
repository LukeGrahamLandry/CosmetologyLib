package ca.lukegrahamlandry.cosmetology.data.api;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CosmeticSlots {
    public static ResourceLocation HEAD = new ResourceLocation(CosmetologyApi.MOD_ID, "head");
    public static ResourceLocation CHEST = new ResourceLocation(CosmetologyApi.MOD_ID, "chest");
    public static ResourceLocation LEGS = new ResourceLocation(CosmetologyApi.MOD_ID, "legs");
    public static ResourceLocation FEET = new ResourceLocation(CosmetologyApi.MOD_ID, "feet");

    private static Set<ResourceLocation> ALL = new HashSet<>(Arrays.asList(HEAD, CHEST, LEGS, FEET));

    public static Set<ResourceLocation> getAll(){
        return ALL;
    }

    public static boolean add(ResourceLocation slotId, ResourceLocation renderType){
        return ALL.add(slotId);
    }
}
