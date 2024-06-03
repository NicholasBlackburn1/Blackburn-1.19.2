package starblazerstudio.screens;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class BugReportScreen   extends Screen
{
    private static final Component TITLE_COMPONENT = Component.translatable("blackburn.bugmenu.title");
    private static final Component BODY_COMPONENT = Component.translatable("blackburn.bugmenu.body");
    private Button addButton;
    private final BooleanConsumer callback;
  
    private EditBox title;
    private EditBox body;
    private Checkbox isConnected;
    private final Screen lastScreen;

    public TwitchSettingsScreen(Screen last){
        super(Component.translatable(I18n.a("blackburn.bugmenu.title")));
        this.callback = null;
        this.lastScreen = last;

    }

    public void tick()
    {
        this.title.tick();
        this.body.tick();
    }

    protected void init()
    {
        // sets up the Title box
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.title = new EditBox(this.font, this.width / 2 - 100, 66, 200, 20, Component.translatable("addServer.enterName"));
        this.title.setFocus(true);
        this.title.setValue(this.title.getValue());
      
        this.title.setResponder((p_169304_) ->

        {
            this.title.getValue();
        });

        
        this.addWidget(this.nameEdit);

        // creates the witch pass box
        this.ipEdit = new EditBox(this.font, this.width / 2 - 100, 106, 200, 20, Component.translatable("blackburn.twitch.password"));
        this.ipEdit.setMaxLength(128);
        this.ipEdit.setValue(this.ipEdit.getValue());
     
        this.ipEdit.setResponder((p_169302_) ->
        {
            Consts.TwitchPass = this.ipEdit.getValue();
        });
        this.addWidget(this.ipEdit);


    // connection button
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, Component.translatable("blakcburn.twitch.connect"), (p_96030_) ->
        {
            Consts.twitchconnector.setupBot();
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, CommonComponents.GUI_CANCEL, (p_169297_) ->
        {
            onClose();
        }));
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight)
    {
        String s = this.ipEdit.getValue();
        String s1 = this.nameEdit.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.ipEdit.setValue(s);
        this.nameEdit.setValue(s1);
    }

    public void removed()
    {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    
    public void onClose()
    {
        this.minecraft.setScreen(this.lastScreen);
    }

    private void updateAddButtonStatus()
    {
        //Consts.twitchconnector.setupBot();
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick)
    {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 17, 16777215);
        drawString(pPoseStack, this.font, NAME_LABEL, this.width / 2 - 100, 53, 10526880);
        drawString(pPoseStack, this.font, IP_LABEL, this.width / 2 - 100, 94, 10526880);
        this.nameEdit.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.ipEdit.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}

