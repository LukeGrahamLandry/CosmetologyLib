package ca.lukegrahamlandry.cosmetology.event;

import ca.lukegrahamlandry.cosmetology.client.geo.GeoCosmeticLayer;
import net.minecraft.client.renderer.entity.PlayerRenderer;

import java.util.Collection;

public class CosmetologyClientEvents {
    public static void addCosmeticLayers(Collection<PlayerRenderer> renderers){
        for (PlayerRenderer render : renderers){
            render.addLayer(new GeoCosmeticLayer(render));
        }
    }
}
