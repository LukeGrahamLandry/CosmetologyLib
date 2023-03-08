package ca.lukegrahamlandry.cosmetology.util;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;

/**
 * Helper methods for detecting if certain mods are available to render cosmetics.
 * I can't just ask Forge since their code may be shadowed without loading them as a mod.
 * The canFindClass check can cope with the target being relocated by shadowing (I checked).
 * Allow soft dependencies by always checking the relevant method here before classloading anything from a library.
 */
public class ModListHelper {
    public static boolean isGeckolibAvailable(){
        return canFindClass("software.bernie.geckolib3.GeckoLib");
    }

    public static boolean isForge(){
        return canFindClass("net.minecraftforge.fml.ModList");
    }

    public static void printMissingMods(){
        if (!isGeckolibAvailable()) CosmetologyApi.errorLog("Mod 'geckolib3' is unavailable. GeoModelAdditionCosmetic is disabled.");
    }

    private static boolean canFindClass(String className){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
