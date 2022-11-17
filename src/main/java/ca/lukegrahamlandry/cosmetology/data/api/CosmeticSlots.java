package ca.lukegrahamlandry.cosmetology.data.api;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Each slot represents a different type of cosmetic.
 * Each player may only have one cosmetic equipped in a slot at a time.
 * Generally all entries of a given slot will render in the same way
 * and thus use the same subclass of CosmeticInfo but this is not required.
 */
public class CosmeticSlots {
    public static ResourceLocation HEAD = new ResourceLocation(CosmetologyApi.MOD_ID, "head");
    public static ResourceLocation CHEST = new ResourceLocation(CosmetologyApi.MOD_ID, "chest");
    public static ResourceLocation LEGS = new ResourceLocation(CosmetologyApi.MOD_ID, "legs");
    public static ResourceLocation FEET = new ResourceLocation(CosmetologyApi.MOD_ID, "feet");

    private static Set<ResourceLocation> ALL = new HashSet<>(Arrays.asList(HEAD, CHEST, LEGS, FEET));

    public static Set<ResourceLocation> getAll(){
        return ALL;
    }

    /**
     * Register a custom slot to be included in the CosmeticSlotArgumentType options
     */
    public static boolean add(ResourceLocation slotId){
        return ALL.add(slotId);
    }
}
