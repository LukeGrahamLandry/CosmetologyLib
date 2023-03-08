package ca.lukegrahamlandry.cosmetology.client.geo;

import ca.lukegrahamlandry.cosmetology.data.type.GeoModelAdditionCosmetic;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class GeoCosmeticRender extends GeoArmorRenderer<NullItem> {
    private final GeoModelAdditionCosmetic cosmetic;

    public GeoCosmeticRender(GeoModelAdditionCosmetic cosmetic) {
        super(new Model(cosmetic));
        this.cosmetic = cosmetic;
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

    // TODO: probably needs to actually be unique for animations to work
    // think i might also need some of my own packets if geckolib is shadowed
    @Override
    public Integer getUniqueID(NullItem animatable) {
        return 314159;
    }

    static class Model extends AnimatedGeoModel<NullItem> {
        GeoModelAdditionCosmetic cosmetic;
        public Model(GeoModelAdditionCosmetic cosmetic){
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
