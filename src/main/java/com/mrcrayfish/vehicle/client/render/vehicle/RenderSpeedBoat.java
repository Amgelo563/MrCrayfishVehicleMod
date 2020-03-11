package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.common.Seat;
import com.mrcrayfish.vehicle.entity.VehicleProperties;
import com.mrcrayfish.vehicle.entity.vehicle.SpeedBoatEntity;
import com.mrcrayfish.vehicle.util.RenderUtil;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

/**
 * Author: MrCrayfish
 */
public class RenderSpeedBoat extends AbstractRenderVehicle<SpeedBoatEntity>
{
    @Override
    public void render(SpeedBoatEntity entity, float partialTicks)
    {
        //Render the body
        this.renderDamagedPart(entity, SpecialModels.SPEED_BOAT_BODY.getModel());

        GlStateManager.pushMatrix();
        GlStateManager.translated(0, 0.215, -0.125);
        GlStateManager.rotatef(-45F, 1, 0, 0);
        GlStateManager.translated(0, 0.02, 0);
        float wheelAngle = entity.prevWheelAngle + (entity.wheelAngle - entity.prevWheelAngle) * partialTicks;
        float wheelAngleNormal = wheelAngle / 45F;
        float turnRotation = wheelAngleNormal * 15F;
        GlStateManager.rotatef(turnRotation, 0, 1, 0);
        RenderUtil.renderColoredModel(SpecialModels.GO_KART_STEERING_WHEEL.getModel(), ItemCameraTransforms.TransformType.NONE, false, -1);
        GlStateManager.popMatrix();
    }

    @Override
    public void applyPlayerModel(SpeedBoatEntity entity, PlayerEntity player, PlayerModel model, float partialTicks)
    {
        model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-85F);
        model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(20F);
        model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-85F);
        model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-20F);

        float wheelAngle = entity.prevWheelAngle + (entity.wheelAngle - entity.prevWheelAngle) * partialTicks;
        float wheelAngleNormal = wheelAngle / 45F;
        float turnRotation = wheelAngleNormal * 6F;
        model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-65F - turnRotation);
        model.bipedRightArm.rotateAngleY = (float) Math.toRadians(-7F);
        model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-65F + turnRotation);
        model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(7F);
    }

    @Override
    public void applyPlayerRender(SpeedBoatEntity entity, PlayerEntity player, float partialTicks)
    {
        int index = entity.getSeatTracker().getSeatIndex(player.getUniqueID());
        if(index != -1)
        {
            VehicleProperties properties = entity.getProperties();
            Seat seat = properties.getSeats().get(index);
            Vec3d seatVec = seat.getPosition().add(0, properties.getAxleOffset() + properties.getWheelOffset(), 0).scale(properties.getBodyPosition().getScale()).mul(-1, 1, 1).scale(0.0625);
            double scale = 32.0 / 30.0;
            double offsetX = -seatVec.x * scale;
            double offsetY = (seatVec.y + player.getYOffset()) * scale + 24 * 0.0625; //Player is 2 blocks high tall but renders at 1.8 blocks tall
            double offsetZ = seatVec.z * scale;

            GlStateManager.translated(offsetX, offsetY, offsetZ);
            float currentSpeedNormal = (entity.prevCurrentSpeed + (entity.currentSpeed - entity.prevCurrentSpeed) * partialTicks) / entity.getMaxSpeed();
            float turnAngleNormal = (entity.prevTurnAngle + (entity.turnAngle - entity.prevTurnAngle) * partialTicks) / entity.getMaxTurnAngle();
            GlStateManager.rotatef(turnAngleNormal * currentSpeedNormal * 15F, 0, 0, 1);
            GlStateManager.rotatef(-8F * Math.min(1.0F, currentSpeedNormal), 1, 0, 0);
            GlStateManager.translated(-offsetX, -offsetY, -offsetZ);
        }
    }

    @Override
    protected boolean shouldRenderFuelLid()
    {
        return false;
    }
}
