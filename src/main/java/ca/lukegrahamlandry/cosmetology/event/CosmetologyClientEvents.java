package ca.lukegrahamlandry.cosmetology.event;

import ca.lukegrahamlandry.cosmetology.client.geo.GeoCosmeticLayer;
import ca.lukegrahamlandry.cosmetology.util.ModListHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;

import java.util.Collection;

public class CosmetologyClientEvents {
    public static void addCosmeticLayers(Collection<PlayerRenderer> renderers){
        for (PlayerRenderer render : renderers){
            if (ModListHelper.isGeckolibAvailable()) render.addLayer(new GeoCosmeticLayer(render));
        }
    }

    /**
     * Must be called on the FMLClientSetupEvent
     * - Adds a RenderLayer to display the player's cosmetics.
     */
    public static void onClientSetup(){
        addCosmeticLayers(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values());
    }
}
