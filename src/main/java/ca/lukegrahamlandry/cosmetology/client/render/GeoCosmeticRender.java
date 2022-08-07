package ca.lukegrahamlandry.cosmetology.client.render;

import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class GeoCosmeticRender extends GeoArmorRenderer<NullItem> {
    private final CosmeticInfo cosmetic;

    public GeoCosmeticRender(CosmeticInfo cosmetic) {
        super(new Model(cosmetic));
        this.cosmetic = cosmetic;
        this.headBone = "helmet";
        this.bodyBone = "chestplate";
        this.rightArmBone = "rightArm";
        this.leftArmBone = "leftArm";
        this.rightLegBone = "rightLeg";
        this.leftLegBone = "leftLeg";
        this.rightBootBone = "rightBoot";
        this.leftBootBone = "leftBoot";
    }

    public void filterBones(){
        getGeoModelProvider().getModel(this.cosmetic.getModel());

        IBone headBone = this.getAndHideBone(this.headBone);
        IBone bodyBone = this.getAndHideBone(this.bodyBone);
        IBone rightArmBone = this.getAndHideBone(this.rightArmBone);
        IBone leftArmBone = this.getAndHideBone(this.leftArmBone);
        IBone rightLegBone = this.getAndHideBone(this.rightLegBone);
        IBone leftLegBone = this.getAndHideBone(this.leftLegBone);
        IBone rightBootBone = this.getAndHideBone(this.rightBootBone);
        IBone leftBootBone = this.getAndHideBone(this.leftBootBone);

        boolean ignore = this.cosmetic.bones == null;
        headBone.setHidden(!(ignore || this.cosmetic.bones.contains("headBone")));
        bodyBone.setHidden(!(ignore || this.cosmetic.bones.contains("bodyBone")));
        rightArmBone.setHidden(!(ignore || this.cosmetic.bones.contains("rightArmBone")));
        leftArmBone.setHidden(!(ignore || this.cosmetic.bones.contains("leftArmBone")));
        rightLegBone.setHidden(!(ignore || this.cosmetic.bones.contains("rightLegBone")));
        leftLegBone.setHidden(!(ignore || this.cosmetic.bones.contains("leftLegBone")));
        rightBootBone.setHidden(!(ignore || this.cosmetic.bones.contains("rightBootBone")));
        leftBootBone.setHidden(!(ignore || this.cosmetic.bones.contains("leftBootBone")));
    }

    @Override
    public GeoArmorRenderer<NullItem> applySlot(EquipmentSlotType slot) {
        // TODO: this should do it based on the bones the cosmetic things should be displayed, we dont actially care about the
        return super.applySlot(slot);
    }

    // TODO: probably needs to actually be unique for animations to work
    @Override
    public Integer getUniqueID(NullItem animatable) {
        return 314159;
    }

    static class Model extends AnimatedGeoModel<NullItem> {
        CosmeticInfo cosmetic;
        public Model(CosmeticInfo cosmetic){
            this.cosmetic = cosmetic;
        }

        @Override
        public ResourceLocation getModelLocation(NullItem object) {
            return this.cosmetic.getModel();
        }

        @Override
        public ResourceLocation getTextureLocation(NullItem object) {
            return this.cosmetic.getTexture();
        }

        @Override
        public ResourceLocation getAnimationFileLocation(NullItem animatable) {
            return this.cosmetic.getAnimation();
        }
    }
}
