package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;

public class FireworkParticles
{
    public static class FlashProvider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprite;

        public FlashProvider(SpriteSet pSprites)
        {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double p_106671_, double pY, double p_106673_, double pZ, double p_106675_)
        {
            FireworkParticles.OverlayParticle fireworkparticles$overlayparticle = new FireworkParticles.OverlayParticle(pLevel, pX, p_106671_, pY);
            fireworkparticles$overlayparticle.pickSprite(this.sprite);
            return fireworkparticles$overlayparticle;
        }
    }

    public static class OverlayParticle extends TextureSheetParticle
    {
        OverlayParticle(ClientLevel p_106677_, double p_106678_, double p_106679_, double p_106680_)
        {
            super(p_106677_, p_106678_, p_106679_, p_106680_);
            this.lifetime = 4;
        }

        public ParticleRenderType getRenderType()
        {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }

        public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks)
        {
            this.setAlpha(0.6F - ((float)this.age + pPartialTicks - 1.0F) * 0.25F * 0.5F);
            super.render(pBuffer, pRenderInfo, pPartialTicks);
        }

        public float getQuadSize(float pScaleFactor)
        {
            return 7.1F * Mth.sin(((float)this.age + pScaleFactor - 1.0F) * 0.25F * (float)Math.PI);
        }
    }

    static class SparkParticle extends SimpleAnimatedParticle
    {
        private boolean trail;
        private boolean flicker;
        private final ParticleEngine engine;
        private float fadeR;
        private float fadeG;
        private float fadeB;
        private boolean hasFade;

        SparkParticle(ClientLevel pLevel, double pX, double p_106704_, double pY, double p_106706_, double pZ, double p_106708_, ParticleEngine pXSpeed, SpriteSet p_106710_)
        {
            super(pLevel, pX, p_106704_, pY, p_106710_, 0.1F);
            this.xd = p_106706_;
            this.yd = pZ;
            this.zd = p_106708_;
            this.engine = pXSpeed;
            this.quadSize *= 0.75F;
            this.lifetime = 48 + this.random.nextInt(12);
            this.setSpriteFromAge(p_106710_);
        }

        public void setTrail(boolean pTrail)
        {
            this.trail = pTrail;
        }

        public void setFlicker(boolean pTwinkle)
        {
            this.flicker = pTwinkle;
        }

        public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks)
        {
            if (!this.flicker || this.age < this.lifetime / 3 || (this.age + this.lifetime) / 3 % 2 == 0)
            {
                super.render(pBuffer, pRenderInfo, pPartialTicks);
            }
        }

        public void tick()
        {
            super.tick();

            if (this.trail && this.age < this.lifetime / 2 && (this.age + this.lifetime) % 2 == 0)
            {
                FireworkParticles.SparkParticle fireworkparticles$sparkparticle = new FireworkParticles.SparkParticle(this.level, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D, this.engine, this.sprites);
                fireworkparticles$sparkparticle.setAlpha(0.99F);
                fireworkparticles$sparkparticle.setColor(this.rCol, this.gCol, this.bCol);
                fireworkparticles$sparkparticle.age = fireworkparticles$sparkparticle.lifetime / 2;

                if (this.hasFade)
                {
                    fireworkparticles$sparkparticle.hasFade = true;
                    fireworkparticles$sparkparticle.fadeR = this.fadeR;
                    fireworkparticles$sparkparticle.fadeG = this.fadeG;
                    fireworkparticles$sparkparticle.fadeB = this.fadeB;
                }

                fireworkparticles$sparkparticle.flicker = this.flicker;
                this.engine.add(fireworkparticles$sparkparticle);
            }
        }
    }

    public static class SparkProvider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public SparkProvider(SpriteSet pSprites)
        {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double p_106747_, double pY, double p_106749_, double pZ, double p_106751_)
        {
            FireworkParticles.SparkParticle fireworkparticles$sparkparticle = new FireworkParticles.SparkParticle(pLevel, pX, p_106747_, pY, p_106749_, pZ, p_106751_, Minecraft.getInstance().particleEngine, this.sprites);
            fireworkparticles$sparkparticle.setAlpha(0.99F);
            return fireworkparticles$sparkparticle;
        }
    }

    public static class Starter extends NoRenderParticle
    {
        private int life;
        private final ParticleEngine engine;
        private ListTag explosions;
        private boolean twinkleDelay;

        public Starter(ClientLevel pLevel, double pX, double p_106759_, double pY, double p_106761_, double pZ, double p_106763_, ParticleEngine pXSpeed, @Nullable CompoundTag p_106765_)
        {
            super(pLevel, pX, p_106759_, pY);
            this.xd = p_106761_;
            this.yd = pZ;
            this.zd = p_106763_;
            this.engine = pXSpeed;
            this.lifetime = 8;

            if (p_106765_ != null)
            {
                this.explosions = p_106765_.getList("Explosions", 10);

                if (this.explosions.isEmpty())
                {
                    this.explosions = null;
                }
                else
                {
                    this.lifetime = this.explosions.size() * 2 - 1;

                    for (int i = 0; i < this.explosions.size(); ++i)
                    {
                        CompoundTag compoundtag = this.explosions.getCompound(i);

                        if (compoundtag.getBoolean("Flicker"))
                        {
                            this.twinkleDelay = true;
                            this.lifetime += 15;
                            break;
                        }
                    }
                }
            }
        }

        public void tick()
        {
            if (this.life == 0 && this.explosions != null)
            {
                boolean flag = this.isFarAwayFromCamera();
                boolean flag1 = false;

                if (this.explosions.size() >= 3)
                {
                    flag1 = true;
                }
                else
                {
                    for (int i = 0; i < this.explosions.size(); ++i)
                    {
                        CompoundTag compoundtag = this.explosions.getCompound(i);

                        if (FireworkRocketItem.Shape.byId(compoundtag.getByte("Type")) == FireworkRocketItem.Shape.LARGE_BALL)
                        {
                            flag1 = true;
                            break;
                        }
                    }
                }

                SoundEvent soundevent1;

                if (flag1)
                {
                    soundevent1 = flag ? SoundEvents.FIREWORK_ROCKET_LARGE_BLAST_FAR : SoundEvents.FIREWORK_ROCKET_LARGE_BLAST;
                }
                else
                {
                    soundevent1 = flag ? SoundEvents.FIREWORK_ROCKET_BLAST_FAR : SoundEvents.FIREWORK_ROCKET_BLAST;
                }

                this.level.playLocalSound(this.x, this.y, this.z, soundevent1, SoundSource.AMBIENT, 20.0F, 0.95F + this.random.nextFloat() * 0.1F, true);
            }

            if (this.life % 2 == 0 && this.explosions != null && this.life / 2 < this.explosions.size())
            {
                int k = this.life / 2;
                CompoundTag compoundtag1 = this.explosions.getCompound(k);
                FireworkRocketItem.Shape fireworkrocketitem$shape = FireworkRocketItem.Shape.byId(compoundtag1.getByte("Type"));
                boolean flag4 = compoundtag1.getBoolean("Trail");
                boolean flag2 = compoundtag1.getBoolean("Flicker");
                int[] aint = compoundtag1.getIntArray("Colors");
                int[] aint1 = compoundtag1.getIntArray("FadeColors");

                if (aint.length == 0)
                {
                    aint = new int[] {DyeColor.BLACK.getFireworkColor()};
                }

                switch (fireworkrocketitem$shape)
                {
                    case SMALL_BALL:
                    default:
                        this.a(0.25D, 2, aint, aint1, flag4, flag2);
                        break;

                    case LARGE_BALL:
                        this.a(0.5D, 4, aint, aint1, flag4, flag2);
                        break;

                    case STAR:
                        this.a(0.5D, new double[][] {{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, aint, aint1, flag4, flag2, false);
                        break;

                    case CREEPER:
                        this.a(0.5D, new double[][] {{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, aint, aint1, flag4, flag2, true);
                        break;

                    case BURST:
                        this.a(aint, aint1, flag4, flag2);
                }

                int j = aint[0];
                float f = (float)((j & 16711680) >> 16) / 255.0F;
                float f1 = (float)((j & 65280) >> 8) / 255.0F;
                float f2 = (float)((j & 255) >> 0) / 255.0F;
                Particle particle = this.engine.createParticle(ParticleTypes.FLASH, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
                particle.setColor(f, f1, f2);
            }

            ++this.life;

            if (this.life > this.lifetime)
            {
                if (this.twinkleDelay)
                {
                    boolean flag3 = this.isFarAwayFromCamera();
                    SoundEvent soundevent = flag3 ? SoundEvents.FIREWORK_ROCKET_TWINKLE_FAR : SoundEvents.FIREWORK_ROCKET_TWINKLE;
                    this.level.playLocalSound(this.x, this.y, this.z, soundevent, SoundSource.AMBIENT, 20.0F, 0.9F + this.random.nextFloat() * 0.15F, true);
                }

                this.remove();
            }
        }

        private boolean isFarAwayFromCamera()
        {
            Minecraft minecraft = Minecraft.getInstance();
            return minecraft.gameRenderer.getMainCamera().getPosition().distanceToSqr(this.x, this.y, this.z) >= 256.0D;
        }

        private void a(double p_106768_, double p_106769_, double p_106770_, double p_106771_, double p_106772_, double p_106773_, int[] p_106774_, int[] p_106775_, boolean p_106776_, boolean p_106777_)
        {
            FireworkParticles.SparkParticle fireworkparticles$sparkparticle = (FireworkParticles.SparkParticle)this.engine.createParticle(ParticleTypes.FIREWORK, p_106768_, p_106769_, p_106770_, p_106771_, p_106772_, p_106773_);
            fireworkparticles$sparkparticle.setTrail(p_106776_);
            fireworkparticles$sparkparticle.setFlicker(p_106777_);
            fireworkparticles$sparkparticle.setAlpha(0.99F);
            int i = this.random.nextInt(p_106774_.length);
            fireworkparticles$sparkparticle.setColor(p_106774_[i]);

            if (p_106775_.length > 0)
            {
                fireworkparticles$sparkparticle.setFadeColor(Util.a(p_106775_, this.random));
            }
        }

        private void a(double p_106779_, int p_106780_, int[] p_106781_, int[] p_106782_, boolean p_106783_, boolean p_106784_)
        {
            double d0 = this.x;
            double d1 = this.y;
            double d2 = this.z;

            for (int i = -p_106780_; i <= p_106780_; ++i)
            {
                for (int j = -p_106780_; j <= p_106780_; ++j)
                {
                    for (int k = -p_106780_; k <= p_106780_; ++k)
                    {
                        double d3 = (double)j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                        double d4 = (double)i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                        double d5 = (double)k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                        double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / p_106779_ + this.random.nextGaussian() * 0.05D;
                        this.a(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, p_106781_, p_106782_, p_106783_, p_106784_);

                        if (i != -p_106780_ && i != p_106780_ && j != -p_106780_ && j != p_106780_)
                        {
                            k += p_106780_ * 2 - 1;
                        }
                    }
                }
            }
        }

        private void a(double p_106786_, double[][] p_106787_, int[] p_106788_, int[] p_106789_, boolean p_106790_, boolean p_106791_, boolean p_106792_)
        {
            double d0 = p_106787_[0][0];
            double d1 = p_106787_[0][1];
            this.a(this.x, this.y, this.z, d0 * p_106786_, d1 * p_106786_, 0.0D, p_106788_, p_106789_, p_106790_, p_106791_);
            float f = this.random.nextFloat() * (float)Math.PI;
            double d2 = p_106792_ ? 0.034D : 0.34D;

            for (int i = 0; i < 3; ++i)
            {
                double d3 = (double)f + (double)((float)i * (float)Math.PI) * d2;
                double d4 = d0;
                double d5 = d1;

                for (int j = 1; j < p_106787_.length; ++j)
                {
                    double d6 = p_106787_[j][0];
                    double d7 = p_106787_[j][1];

                    for (double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D)
                    {
                        double d9 = Mth.lerp(d8, d4, d6) * p_106786_;
                        double d10 = Mth.lerp(d8, d5, d7) * p_106786_;
                        double d11 = d9 * Math.sin(d3);
                        d9 *= Math.cos(d3);

                        for (double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D)
                        {
                            this.a(this.x, this.y, this.z, d9 * d12, d10, d11 * d12, p_106788_, p_106789_, p_106790_, p_106791_);
                        }
                    }

                    d4 = d6;
                    d5 = d7;
                }
            }
        }

        private void a(int[] p_106794_, int[] p_106795_, boolean p_106796_, boolean p_106797_)
        {
            double d0 = this.random.nextGaussian() * 0.05D;
            double d1 = this.random.nextGaussian() * 0.05D;

            for (int i = 0; i < 70; ++i)
            {
                double d2 = this.xd * 0.5D + this.random.nextGaussian() * 0.15D + d0;
                double d3 = this.zd * 0.5D + this.random.nextGaussian() * 0.15D + d1;
                double d4 = this.yd * 0.5D + this.random.nextDouble() * 0.5D;
                this.a(this.x, this.y, this.z, d2, d4, d3, p_106794_, p_106795_, p_106796_, p_106797_);
            }
        }
    }
}
