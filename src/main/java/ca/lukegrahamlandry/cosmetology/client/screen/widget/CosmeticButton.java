package ca.lukegrahamlandry.cosmetology.client.screen.widget;

import ca.lukegrahamlandry.cosmetology.client.geo.GeoCosmeticRender;
import ca.lukegrahamlandry.cosmetology.client.geo.NullItem;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticSlots;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

import java.util.Arrays;

public class CosmeticButton extends Button {
    protected final CosmeticInfo cosmetic;
    protected final Drawable texture;
    protected final Drawable hoveredTexture;

    public CosmeticButton(CosmeticInfo cosmetic, int x, int y, int width, int height, IPressable press, Drawable texture, Drawable hoveredTexture){
        super(x, y, width, height, new StringTextComponent(""), press, CosmeticButton::tooltip);
        this.cosmetic = cosmetic;
        this.texture = texture;
        this.hoveredTexture = hoveredTexture;
    }

    @Override
    protected boolean clicked(double p_230992_1_, double p_230992_3_) {
        return super.clicked(p_230992_1_, p_230992_3_);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float p_230430_4_) {
        this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        if (this.visible) {
            if (this.isHovered){
                this.hoveredTexture.screenY = this.y;
                this.hoveredTexture.screenX = this.x;
                this.hoveredTexture.blit(stack);
            } else {
                this.texture.screenY = this.y;
                this.texture.screenX = this.x;
                this.texture.blit(stack);
            }

            this.renderCosmetic();
            if (this.isHovered){
                this.renderToolTip(stack, mouseX, mouseY);
            }
        }
    }

    public void renderCosmetic(){
        renderCosmetic(this.cosmetic, this.x + (this.width / 2), this.y + (this.height / 2), this.width, this.height, this.hasGlint(), false);
    }

    private static ItemStack AN_ITEM_STACK = new ItemStack(new NullItem(ArmorMaterial.CHAIN, EquipmentSlotType.CHEST, new Item.Properties()));


    public static void transform(MatrixStack stack, CosmeticInfo cosmetic){
        float slotYShift = 0;
        float scale = 1;
        if (cosmetic.slot.equals(CosmeticSlots.HEAD)){
            slotYShift = -10;
            scale = 0.75F;
        } else if (cosmetic.slot.equals(CosmeticSlots.CHEST)){
            slotYShift = 10;
            scale = 0.75F;
        } else if (cosmetic.slot.equals(CosmeticSlots.LEGS)){
            slotYShift = 30;
        } else if (cosmetic.slot.equals(CosmeticSlots.FEET)){
            slotYShift = 40;
        }

        stack.mulPose(Vector3f.YP.rotationDegrees(30));
        stack.mulPose(Vector3f.XP.rotationDegrees(180));
        stack.mulPose(Vector3f.YP.rotationDegrees(180));
        stack.translate(0, slotYShift, 0);
        stack.scale(scale, scale, 1);
    }
    public static void renderCosmetic(CosmeticInfo cosmetic, int centerX, int centerY, int width, int height, boolean glint, boolean selected){
        // System.out.println("try render "  + cosmetic.id + " model-" + cosmetic.getModel() + " texture-" + cosmetic.getTexture());
        int scale = 30;
        float lookAtX = 0;
        float lookAtY = 0;

        // PlayerEntity entity = Minecraft.getInstance().player;
        // InventoryScreen.renderEntityInInventory(centerX, centerY, scale, lookAtX, lookAtY, entity);

        float f = (float)Math.atan((double)(lookAtX / 40.0F));
        float f1 = (float)Math.atan((double)(lookAtY / 40.0F));
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)centerX, (float)centerY, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        transform(matrixstack, cosmetic);
        matrixstack.scale((float)scale, (float)scale, (float)scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        matrixstack.mulPose(quaternion);
//        float f2 = entity.yBodyRot;
//        float f3 = entity.yRot;
//        float f4 = entity.xRot;
//        float f5 = entity.yHeadRotO;
//        float f6 = entity.yHeadRot;
//        entity.yBodyRot = 180.0F + f * 20.0F;
//        entity.yRot = 180.0F + f * 40.0F;
//        entity.xRot = -f1 * 20.0F;
//        entity.yHeadRot = entity.yRot;
//        entity.yHeadRotO = entity.yRot;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            float p_229084_1_ = 0, p_229084_9_ = 1F, p_229084_2_= 0,p_229084_4_= 0,p_229084_6_= 0,p_229084_8_=0;
            int p_229084_12_ = 15728880;
            Vector3d vector3d = Vector3d.ZERO; // entityrenderer.getRenderOffset(p_229084_1_, p_229084_9_);
            double d2 = p_229084_2_ + vector3d.x();
            double d3 = p_229084_4_ + vector3d.y();
            double d0 = p_229084_6_ + vector3d.z();
            matrixstack.pushPose();
            matrixstack.translate(d2, d3, d0);
                // entityrenderer.render(p_229084_1_, p_229084_8_, p_229084_9_, matrixstack, irendertypebuffer$impl, p_229084_12_);
                GeoCosmeticRender geoRenderer = new GeoCosmeticRender(cosmetic); // TODO: modelCache
                geoRenderer.setCurrentItem(Minecraft.getInstance().player, AN_ITEM_STACK, EquipmentSlotType.CHEST);
                geoRenderer.filterBones();
                IVertexBuilder vertex = ItemRenderer.getArmorFoilBuffer(irendertypebuffer$impl, RenderType.armorCutoutNoCull(cosmetic.getTexture()), false, false);  // last param = isFoil (enchantment glint)
                geoRenderer.render(0, matrixstack, vertex, p_229084_12_);
                //end
            matrixstack.translate(-vector3d.x(), -vector3d.y(), -vector3d.z());

            matrixstack.popPose();
            //end
        });
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
//        entity.yBodyRot = f2;
//        entity.yRot = f3;
//        entity.xRot = f4;
//        entity.yHeadRotO = f5;
//        entity.yHeadRot = f6;
        RenderSystem.popMatrix();
    }

    private static void tooltip(Button button, MatrixStack matrix, int xM, int yM) {
        net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(
                matrix, Arrays.asList(((CosmeticButton)button).getTitle(), ((CosmeticButton)button).getDescription()),
                xM, yM, 400, 200, -1, Minecraft.getInstance().font);
    }

    public boolean hasGlint(){
        return false;
    }

    public TextComponent getTitle(){
        return new StringTextComponent(this.cosmetic.id.toString());
    }

    public TextComponent getDescription(){
        return new StringTextComponent("hello");
    }
}
