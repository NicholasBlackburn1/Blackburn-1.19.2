package starblazerstudio.screens;

import java.io.IOException;

import com.mojang.blaze3d.vertex.PoseStack;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.optifine.util.LinkedList;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.GitHubIssueCreator;
import starblazerstudio.utils.IssueLabel;
import starblazerstudio.utils.key;
import starblazerstudio.utils.IssueLable;

public class BugReportScreen extends Screen {

    private static final Component TITLE_COMPONENT = Component.translatable("blackburn.bugmenu.title");
    private static final Component BODY_COMPONENT = Component.translatable("blackburn.bugmenu.body");
    private Button addButton;
    private final BooleanConsumer callback;

    private EditBox title;
    private EditBox body;
    private Checkbox isConnected;
    private final Screen lastScreen;
    private GitHubIssueCreator issueCreator;
    private String titleinput = "";
    private String bodyinput = "";

    private LinkedList<IssueLabel> isssue = new LinkedList<IssueLabel>();

    public BugReportScreen(Screen last) {

        super(Component.translatable(I18n.a("blackburn.bugmenu.screentitle")));
        this.callback = null;
        this.lastScreen = last;
    }

    public void tick() {
        this.title.tick();
        this.body.tick();
    }

    protected void init() {

        // sets up the Title box
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.title = new EditBox(this.font, this.width / 2 - 100, 66, 200, 20, Component.translatable("addServer.enterName"));
        this.title.setFocus(true);
        this.title.setValue(this.title.getValue());

        this.title.setResponder((p_169304_)
                -> {
            titleinput = this.title.getValue();
        });

        this.addWidget(this.title);

        // creates the witch pass box
        this.body = new EditBox(this.font, this.width / 2 - 100, 106, 200, 20, Component.translatable("blackburn.twitch.password"));
        this.body.setMaxLength(128);
        this.body.setValue(this.body.getValue());

        this.body.setResponder((p_169302_)
                -> {
            bodyinput = this.body.getValue();
        });
        this.addWidget(this.body);

        // 

        // connection button
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, Component.translatable("blakcburn.twitch.connect"), (p_96030_)
                -> {

            issueCreator = new GitHubIssueCreator("NicholasBlackburn1", "Blackburn-1.19.2", key.githubkey);
            try {
                issueCreator.createIssue(titleinput, bodyinput, isssue);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, CommonComponents.GUI_CANCEL, (p_169297_)
                -> {
            onClose();
        }));
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.body.getValue();
        String s1 = this.title.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.body.setValue(s);
        this.title.setValue(s1);
    }

    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }

    private void updateAddButtonStatus() {
        //Consts.twitchconnector.setupBot();
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 17, 16777215);
        drawString(pPoseStack, this.font, TITLE_COMPONENT, this.width / 2 - 100, 53, 10526880);
        drawString(pPoseStack, this.font, BODY_COMPONENT, this.width / 2 - 100, 94, 10526880);
        this.title.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.body.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
