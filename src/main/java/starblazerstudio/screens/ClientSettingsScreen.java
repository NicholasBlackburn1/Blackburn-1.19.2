package starblazerstudio.screens;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.optifine.gui.GuiScreenOF;
import starblazerstudio.twitch.TwitchIRC;
import starblazerstudio.utils.Consts;

import starblazerstudio.screens.BugReportScreen;

public class ClientSettingsScreen extends Screen
{

    private static final Component TwitchIrc = Component.translatable("blackburn.twitch.title");
    private static final Component IP_LABEL = Component.translatable("blackburn.twitch.password");
    private Button addButton;
    private final BooleanConsumer callback;

  
    
    private final Screen lastScreen;

    public ClientSettingsScreen(Screen last){
        super(Component.translatable(I18n.a("blackburn.clientsettings.buttontitle")));
        this.callback = null;
        this.lastScreen = last;

    }

    // this where pupping for data comes from 
    public void tick()
    {
       
    }

    protected void init()
    { 
        // the twtich button 
        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, Component.translatable("blackburn.twitch.title"), (p_96274_) ->
        {
            this.minecraft.setScreen(new TwitchSettingsScreen(this));
        }));
        
         // the twtich button 
         this.addRenderableWidget(new Button(this.width / 2 + 5, this.height / 6 + 48 + 20, 150, 20, Component.translatable("blackburn.clientsettings.buttontitle"), (p_96274_) ->
         {
             this.minecraft.setScreen(new BugReportScreen(this));
         }));
         

        // the exit button 
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, CommonComponents.GUI_CANCEL, (p_169297_) ->
        {
            onClose();
        }));
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight)
    {
        this.init(pMinecraft, pWidth, pHeight);
      
    }

    public void removed()
    {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    
    public void onClose()
    {
        this.minecraft.setScreen(this.lastScreen);
    }


    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick)
    {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 17, 16777215);
    
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
