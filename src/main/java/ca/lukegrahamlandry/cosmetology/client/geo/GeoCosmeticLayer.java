package ca.lukegrahamlandry.cosmetology.client.geo;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticSlots;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import ca.lukegrahamlandry.cosmetology.data.type.GeoModelAdditionCosmetic;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GeoCosmeticLayer extends BipedArmorLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>, BipedModel<AbstractClientPlayerEntity>> {
    PlayerRenderer renderer;
    public GeoCosmeticLayer(PlayerRenderer renderer) {
        super(renderer, null, null);  // null is fine since i never call super.render
        this.renderer = renderer;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        for (DataStore source : CosmetologyApi.getSources()){
            for (CosmeticInfo cosmetic : source.getActive(player.getUUID())) {
                if (cosmetic instanceof GeoModelAdditionCosmetic) {
                    this.renderCosmetic(matrix, buffer, player, light, (GeoModelAdditionCosmetic) cosmetic, partialTicks);
                }
            }
        }
    }

    private static final Map<CosmeticInfo, GeoCosmeticRender> modelCache = new HashMap<>();
    private static ItemStack AN_ITEM_STACK = new ItemStack(new NullItem(ArmorMaterial.CHAIN, EquipmentSlotType.CHEST, new Item.Properties()));

    private void renderCosmetic(MatrixStack matrix, IRenderTypeBuffer buffer, AbstractClientPlayerEntity player, int light, GeoModelAdditionCosmetic cosmetic, float partialTicks) {
        if (!modelCache.containsKey(cosmetic)) modelCache.put(cosmetic, new GeoCosmeticRender(cosmetic));
        GeoCosmeticRender geoRenderer = modelCache.get(cosmetic);

        copyRotations(this.renderer.getModel(), geoRenderer);
        geoRenderer.applyEntityStats(this.renderer.getModel());
        geoRenderer.setCurrentItem(player, AN_ITEM_STACK, EquipmentSlotType.CHEST);
        geoRenderer.filterBones();

        IVertexBuilder vertex = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(cosmetic.getTexture()), false, false);  // last param = isFoil (enchantment glint)
        geoRenderer.render(partialTicks, matrix, vertex, light);
    }

    private static void copyRotations(BipedModel<?> from, BipedModel<?> to) {
        copyRotations(from.head, to.head);
        copyRotations(from.hat, to.hat);
        copyRotations(from.body, to.body);
        copyRotations(from.leftArm, to.leftArm);
        copyRotations(from.rightArm, to.rightArm);
        copyRotations(from.rightLeg, to.rightLeg);
        copyRotations(from.leftLeg, to.leftLeg);
    }

    private static void copyRotations(ModelRenderer from, ModelRenderer to) {
        to.xRot = from.xRot;
        to.yRot = from.yRot;
        to.zRot = from.zRot;
        to.x = from.x;
        to.y = from.y;
        to.z = from.z;
    }
}
