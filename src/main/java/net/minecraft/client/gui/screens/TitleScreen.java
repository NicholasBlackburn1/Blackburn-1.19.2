package net.minecraft.client.gui.screens;

import com.google.common.util.concurrent.Runnables;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;
import starblazerstudio.screens.CopyRightScreen;
import starblazerstudio.utils.Consts;
import starblazerstudio.screens.TitleScreenOverlay;

import com.google.common.util.concurrent.Runnables;
import com.google.gson.JsonPrimitive;

public class TitleScreen extends Screen
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DEMO_LEVEL_ID = "Demo_World";
    public static final Component COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. Do not distribute!");
    public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
    private static ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private static final ResourceLocation ACCESSIBILITY_TEXTURE = new ResourceLocation("textures/gui/accessibility.png");
    private final boolean minceraftEasterEgg;
    private int i;
    private int copyrightWidth;
    private int copyrightX;
    @Nullable
    private String splash;
    private Button resetDemoButton;
    private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
    @Nullable
    private RealmsNotificationsScreen realmsNotificationsScreen;
    private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
    private final boolean fading;
    private long fadeInStart;
    @Nullable
    private TitleScreen.WarningLabel warningLabel;
    private Screen modUpdateNotification;

    public TitleScreen()
    {
        this(false);
    }

    public TitleScreen(boolean pFading)
    {
        super(Component.translatable("narrator.screen.title"));
        this.fading = pFading;
        this.minceraftEasterEgg = (double)RandomSource.create().nextFloat() < 1.0E-4D;
    }

    private boolean realmsNotificationsEnabled()
    {
        return this.minecraft.options.realmsNotifications().get() && this.realmsNotificationsScreen != null;
    }

    public void tick()
    {
        if (this.realmsNotificationsEnabled())
        {
            this.realmsNotificationsScreen.tick();
        }

        this.minecraft.getRealms32BitWarningStatus().showRealms32BitWarningIfNeeded(this);
    }

    public static CompletableFuture<Void> preloadResources(TextureManager pTexMngr, Executor pBackgroundExecutor)
    {
        return CompletableFuture.allOf(pTexMngr.preload(MINECRAFT_LOGO, pBackgroundExecutor), pTexMngr.preload(MINECRAFT_EDITION, pBackgroundExecutor), pTexMngr.preload(PANORAMA_OVERLAY, pBackgroundExecutor), CUBE_MAP.preload(pTexMngr, pBackgroundExecutor));
    }

    public boolean isPauseScreen()
    {
        return false;
    }

    public boolean shouldCloseOnEsc()
    {
        return false;
    }

    protected void init()
    {


        i++;
        Consts.showStart = true;
        
        TitleScreenOverlay overlay = new TitleScreenOverlay();

        if (this.splash == null) {
            this.splash = minecraft.getSplashManager().getSplash();
        }

        // runs only on 2nd startup of main menu
        if(i == 1){
            overlay.BlackburnTitleInit();
        }
        
        this.PANORAMA_OVERLAY = new ResourceLocation(overlay.setBackgroundScreen());

    

        i = 24;
        int j = height / 4 + 48;


        int l = this.font.width(COPYRIGHT_TEXT);
        int i1 = this.width - l - 2;
        int j1 = 24;
        int k = this.height / 4 + 48;
        Button button = null;

        
        if(Consts.background.size() == 0){
            Consts.warn("Cannot Register new Main menu  because list is 0");

         } else{
            Consts.log("Registering main menu");
            overlay.LoadCustomMainMenu(minecraft,this,width, j);


            //overlay.setUpCustomMainMenu(minecraft, this, width,height, j, realmsNotificationsScreen);
            Consts.log("Registered main menu");
        }

            
    }

      

    private void createNormalMenuOptions(int pY, int pRowHeight)
    {
        this.addRenderableWidget(new Button(this.width / 2 - 100, pY, 200, 20, Component.translatable("menu.singleplayer"), (p_232778_1_) ->
        {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }));
        final Component component = this.getMultiplayerDisabledReason();
        boolean flag = component == null;
        Button.OnTooltip button$ontooltip = component == null ? Button.NO_TOOLTIP : new Button.OnTooltip()
        {
            public void onTooltip(Button p_169458_, PoseStack p_169459_, int p_169460_, int p_169461_)
            {
                TitleScreen.this.renderTooltip(p_169459_, TitleScreen.this.minecraft.font.split(component, Math.max(TitleScreen.this.width / 2 - 43, 170)), p_169460_, p_169461_);
            }
            public void narrateTooltip(Consumer<Component> p_169456_)
            {
                p_169456_.accept(component);
            }
        };
        (this.addRenderableWidget(new Button(this.width / 2 - 100, pY + pRowHeight * 1, 200, 20, Component.translatable("menu.multiplayer"), (p_96775_1_) ->
        {
            Screen screen = (Screen)(this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this));
            this.minecraft.setScreen(screen);
        }, button$ontooltip))).active = flag;
        boolean flag1 = Reflector.ModListScreen_Constructor.exists();
        int i = flag1 ? this.width / 2 + 2 : this.width / 2 - 100;
        int j = flag1 ? 98 : 200;
        (this.addRenderableWidget(new Button(i, pY + pRowHeight * 2, j, 20, Component.translatable("menu.online"), (p_210871_1_) ->
        {
            this.realmsButtonClicked();
        }, button$ontooltip))).active = flag;
    }

    @Nullable
    private Component getMultiplayerDisabledReason()
    {
        if (this.minecraft.allowsMultiplayer())
        {
            return null;
        }
        else
        {
            BanDetails bandetails = this.minecraft.multiplayerBan();

            if (bandetails != null)
            {
                return bandetails.expires() != null ? Component.translatable("title.multiplayer.disabled.banned.temporary") : Component.translatable("title.multiplayer.disabled.banned.permanent");
            }
            else
            {
                return Component.translatable("title.multiplayer.disabled");
            }
        }
    }

   

    private void realmsButtonClicked()
    {
        this.minecraft.setScreen(new RealmsMainScreen(this));
    }

    public void render(PoseStack p_96739_, int p_96740_, int p_96741_, float p_96742_) {
        
        TitleScreenOverlay overlay = new TitleScreenOverlay();
   
         // Shows the Lurking presents 
         //Consts.rich.LerkingPresence();
         
        if (this.fadeInStart == 0L && this.fading) {
           this.fadeInStart = Util.getMillis();
        }
  
        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        this.panorama.render(p_96742_, Mth.clamp(f, 0.0F, 1.0F));
        int i = 274;
        int j = this.width / 2 - 137;
        int k = 30;
  
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
        blit(p_96739_, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        float f1 = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int l = Mth.ceil(f1 * 255.0F) << 24;
        if ((l & -67108864) != 0) {
           RenderSystem.setShader(GameRenderer::getPositionTexShader);
  
           // Renders adition
           RenderSystem.setShaderTexture(0, MINECRAFT_EDITION);
  
           // sets Edition Placemnt & splash text
           JsonPrimitive editionXFull, editionYFull, splashy, editionXSmall,editionYSmol,editionImageWidth,editionImageHight,editionTextureWidth,splashRot;
  
           editionTextureWidth = (JsonPrimitive) Consts.background.get(20);
           editionImageWidth = (JsonPrimitive) Consts.background.get(18);
           editionImageHight = (JsonPrimitive) Consts.background.get(19);
  
           editionXSmall = (JsonPrimitive) Consts.background.get(16);
           editionYSmol = (JsonPrimitive) Consts.background.get(17);
  
           editionXFull = (JsonPrimitive) Consts.background.get(14);
           editionYFull = ( JsonPrimitive) Consts.background.get(15);
  
           splashy = (JsonPrimitive) Consts.background.get(11);
           splashRot = (JsonPrimitive) Consts.background.get(13);
  
           overlay.renderEdition(this,splash, p_96739_,font, this.width,this.height, editionXFull.getAsInt(),editionYFull.getAsInt(),splashy.getAsInt(), j, l,editionXSmall.getAsInt(),editionYSmol.getAsInt(),editionImageWidth.getAsInt(),editionImageHight.getAsInt(),editionTextureWidth.getAsInt(),splashRot.getAsInt(),minecraft.getWindow().isFullscreen());
  
           // draws version string at the bottom
           overlay.setDrawVersionName(this.minecraft,this,p_96739_,this.font,this.height,l);
  
           // this sets the copyright text
           overlay.drawCopyRightString(this, p_96739_, this.font, Consts.copyright, this.height, this.width, this.copyrightX, this.copyrightWidth, p_96740_, p_96741_,f1);
  
           for(GuiEventListener guieventlistener : this.children()) {
              if (guieventlistener instanceof AbstractWidget) {
                 ((AbstractWidget)guieventlistener).setAlpha(f1);
              }
           }
  
           super.render(p_96739_, p_96740_, p_96741_, p_96742_);
           if (this.realmsNotificationsEnabled() && f1 >= 1.0F) {
              this.realmsNotificationsScreen.render(p_96739_, p_96740_, p_96741_, p_96742_);
           }
  
        }
     }
  
    public boolean mouseClicked(double pMouseX, double p_96736_, int pMouseY)
    {
        if (super.mouseClicked(pMouseX, p_96736_, pMouseY))
        {
            return true;
        }
        else
        {
            return this.realmsNotificationsEnabled() && this.realmsNotificationsScreen.mouseClicked(pMouseX, p_96736_, pMouseY);
        }
    }

    public void removed()
    {
        if (this.realmsNotificationsScreen != null)
        {
            this.realmsNotificationsScreen.removed();
        }
    }

    private void confirmDemo(boolean p_96778_)
    {
        if (p_96778_)
        {
            try
            {
                LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.minecraft.getLevelSource().createAccess("Demo_World");

                try
                {
                    levelstoragesource$levelstorageaccess.deleteLevel();
                }
                catch (Throwable throwable1)
                {
                    if (levelstoragesource$levelstorageaccess != null)
                    {
                        try
                        {
                            levelstoragesource$levelstorageaccess.close();
                        }
                        catch (Throwable throwable)
                        {
                            throwable1.addSuppressed(throwable);
                        }
                    }

                    throw throwable1;
                }

                if (levelstoragesource$levelstorageaccess != null)
                {
                    levelstoragesource$levelstorageaccess.close();
                }
            }
            catch (IOException ioexception1)
            {
                SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
                LOGGER.warn("Failed to delete demo world", (Throwable)ioexception1);
            }
        }

        this.minecraft.setScreen(this);
    }

    static record WarningLabel(Font font, MultiLineLabel label, int x, int y)
    {
        public void render(PoseStack p_232791_, int p_232792_)
        {
            this.label.renderBackgroundCentered(p_232791_, this.x, this.y, 9, 2, 1428160512);
            this.label.renderCentered(p_232791_, this.x, this.y, 9, 16777215 | p_232792_);
        }
        public Font font()
        {
            return this.font;
        }
        public MultiLineLabel label()
        {
            return this.label;
        }
        public int x()
        {
            return this.x;
        }
        public int y()
        {
            return this.y;
        }
    }
}
