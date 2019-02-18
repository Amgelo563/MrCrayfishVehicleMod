package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.common.entity.PartPosition;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityLandVehicle;
import com.mrcrayfish.vehicle.init.ModItems;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class EntityCouch extends EntityLandVehicle implements IEntityRaytraceable
{
    public static final float AXLE_OFFSET = -1.0F;
    public static final float WHEEL_OFFSET = 4.375F;
    public static final PartPosition BODY_POSITION = new PartPosition(0, 0, 0.1, 0, 0, 0, 1.0);
    private static final Vec3d HELD_OFFSET_VEC = new Vec3d(2.0, 2.0, 0.0);
    private static final Vec3d TRAILER_OFFSET_VEC = new Vec3d(0.0, 0.0, -0.25); //TODO may be able to get rid of this

    public EntityCouch(World worldIn)
    {
        super(worldIn);
        this.setMaxSpeed(10);
        this.setSize(1.0F, 1.0F);
        this.setWheelOffset(WHEEL_OFFSET);
        this.setBodyPosition(BODY_POSITION);
        this.setHeldOffset(HELD_OFFSET_VEC);
        this.setTrailerOffset(TRAILER_OFFSET_VEC);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientInit()
    {
        super.onClientInit();
        body = new ItemStack(Item.getByNameOrId("cfm:couch_jeb"), 1, 0);
        wheel = new ItemStack(ModItems.WHEEL);
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.atvEngineMono;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.atvEngineStereo;
    }

    @Override
    public double getMountedYOffset()
    {
        return 0.525;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.SMALL_MOTOR;
    }

    @Override
    public boolean isLockable()
    {
        return false;
    }
}
