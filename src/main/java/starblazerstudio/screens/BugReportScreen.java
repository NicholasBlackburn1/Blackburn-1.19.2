package starblazerstudio.screens;

import java.io.IOException;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import starblazerstudio.guicomponents.GuiDropdownList;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.GitHubIssueCreator;
import starblazerstudio.utils.IssueLabel;
import starblazerstudio.utils.key;
import java.util.LinkedList;

public class BugReportScreen extends Screen {

    private static final Component TITLE_COMPONENT = Component.translatable("blackburn.bugmenu.title");
    private static final Component BODY_COMPONENT = Component.translatable("blackburn.bugmenu.body");
    private Button addButton;
    private final BooleanConsumer callback;
    private EditBox title;
    private EditBox body;
    private final Screen lastScreen;
    private GitHubIssueCreator issueCreator;
    private String titleinput = "";
    private String bodyinput = "";
    private GuiDropdownList dropdownList;
    private final Screen laast;
    private static String selected_label = "";

    
    private boolean isSuccess;

    public BugReportScreen(Screen last) {
        super(Component.translatable(I18n.a("blackburn.bugmenu.screentitle")));
        this.callback = null;
        this.lastScreen = last;
        this.laast = last;
        this.isSuccess = false;
    }

    public void tick() {
        this.title.tick();
        this.body.tick();
    }

        /**
     * Initializes the Bug Report Screen with text input fields for title and body,
     * dropdown list for selecting tags, and buttons for connecting and canceling.
     */
    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.title = new EditBox(this.font, this.width / 2 - 100, 66, 200, 20, Component.translatable("addServer.enterName"));
        this.title.setFocus(true);
        this.title.setValue(this.title.getValue());
        this.title.setMaxLength(255);
        this.title.setResponder((p_169304_) -> {
            titleinput = this.title.getValue();
        });
        this.addWidget(this.title);

        this.body = new EditBox(this.font, this.width / 2 - 100, 106, 200, 20, Component.translatable("blackburn.twitch.password"));
        this.body.setMaxLength(255);
        this.body.setValue(this.body.getValue());
        this.body.setResponder((p_169302_) -> {
            bodyinput = this.body.getValue();
        });
        this.addWidget(this.body);

        LinkedList<String[]> labelOptions = new LinkedList<>();
        labelOptions.add(new String[]{"Select Tag", ""});
        for (IssueLabel label : IssueLabel.values()) {
            labelOptions.add(new String[]{label.toString(), label.toString()});
        }
        this.addRenderableWidget(dropdownList = new GuiDropdownList(this.width / 2 - 100, this.height / 4 + 80, 50, 20, labelOptions.toArray(new String[0][]), (button) -> {}));

      
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, Component.translatable("blackburn.bugmenu.connect"), (p_96030_) -> {
            issueCreator = new GitHubIssueCreator(Consts.repouser, Consts.reponame, key.githubkey);
            Consts.error("Selected label: " + Consts.bugmenulabel);
            Consts.isconnected = true;

            try {
                issueCreator.createIssue(titleinput, bodyinput, Consts.bugmenulabel);
                isSuccess = true;
                
            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = false;
               
            }
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, CommonComponents.GUI_CANCEL, (p_169297_) -> {
            onClose();
        }));
    }


    /**
     * Resizes the BugReportScreen based on the provided Minecraft instance, width, and height.
     *
     * @param pMinecraft the Minecraft instance
     * @param pWidth     the new width
     * @param pHeight    the new height
     */
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
    /**
     * A method that renders the BugReportScreen with the provided parameters.
     *
     * @param  pPoseStack   the PoseStack instance
     * @param  pMouseX      the x-coordinate of the mouse
     * @param  pMouseY      the y-coordinate of the mouse
     * @param  pPartialTick the partial tick value
     */
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        drawString(pPoseStack, this.font, TITLE_COMPONENT, this.width / 2 - 100, 53, 10526880);
        drawString(pPoseStack, this.font, BODY_COMPONENT, this.width / 2 - 100, 94, 10526880);
        this.title.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.body.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
