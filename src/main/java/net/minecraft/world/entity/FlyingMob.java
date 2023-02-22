package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class FlyingMob extends Mob
{
    protected FlyingMob(EntityType <? extends FlyingMob > p_20806_, Level p_20807_)
    {
        super(p_20806_, p_20807_);
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource)
    {
        return false;
    }

    protected void checkFallDamage(double pY, boolean p_20810_, BlockState pOnGround, BlockPos pState)
    {
    }

    public void travel(Vec3 pTravelVector)
    {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance())
        {
            if (this.isInWater())
            {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
            }
            else if (this.isInLava())
            {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            }
            else
            {
                float f = 0.91F;

                if (this.onGround)
                {
                    f = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getFriction() * 0.91F;
                }

                float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;

                if (this.onGround)
                {
                    f = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getFriction() * 0.91F;
                }

                this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
            }
        }

        this.calculateEntityAnimation(this, false);
    }

    public boolean onClimbable()
    {
        return false;
    }
}
