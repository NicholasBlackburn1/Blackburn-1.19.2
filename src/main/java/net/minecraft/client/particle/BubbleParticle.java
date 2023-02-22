package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;

public class BubbleParticle extends TextureSheetParticle
{
    BubbleParticle(ClientLevel p_105773_, double p_105774_, double p_105775_, double p_105776_, double p_105777_, double p_105778_, double p_105779_)
    {
        super(p_105773_, p_105774_, p_105775_, p_105776_);
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.xd = p_105777_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
        this.yd = p_105778_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
        this.zd = p_105779_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
    }

    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.lifetime-- <= 0)
        {
            this.remove();
        }
        else
        {
            this.yd += 0.002D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= (double)0.85F;
            this.yd *= (double)0.85F;
            this.zd *= (double)0.85F;

            if (!this.level.getFluidState(new BlockPos(this.x, this.y, this.z)).is(FluidTags.WATER))
            {
                this.remove();
            }
        }
    }

    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites)
        {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double p_105807_, double pY, double p_105809_, double pZ, double p_105811_)
        {
            BubbleParticle bubbleparticle = new BubbleParticle(pLevel, pX, p_105807_, pY, p_105809_, pZ, p_105811_);
            bubbleparticle.pickSprite(this.sprite);
            return bubbleparticle;
        }
    }
}
