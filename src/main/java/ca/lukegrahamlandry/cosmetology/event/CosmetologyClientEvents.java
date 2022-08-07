package ca.lukegrahamlandry.cosmetology.event;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.client.render.GeoCosmeticLayer;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Collection;

public class CosmetologyClientEvents {
    public static void addCosmeticLayers(Collection<PlayerRenderer> renderers){
        for (PlayerRenderer render : renderers){
            render.addLayer(new GeoCosmeticLayer(render));
        }
    }
}
