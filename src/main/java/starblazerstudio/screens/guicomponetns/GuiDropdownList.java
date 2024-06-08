package starblazerstudio.screens.guicomponetns;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;


public class GuiDropdownList extends Button {
    private final String[][] data;
    private boolean expanded;
    private int selectedIndex;

    public GuiDropdownList(int x, int y, int width, int height, String[][] data, Button.OnPress onPress) {
        super(x, y, width, height, Component.nullToEmpty(data[0][0]), onPress);
        this.data = data;
        this.selectedIndex = 0;
        this.expanded = false;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (!this.expanded) {
            super.renderButton(poseStack, mouseX, mouseY, partialTicks);
        } else {
            // Render the button without background when expanded
            fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0xFF000000);
            drawCenteredString(poseStack, Minecraft.getInstance().font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
        }

        if (this.expanded) {
            // Render dropdown items
            for (int i = 0; i < data.length; i++) {
                int yPosition = this.y + (i + 1) * 20;
                fill(poseStack, this.x, yPosition, this.x + this.width, yPosition + 20, 0xFF000000);
                drawString(poseStack, Minecraft.getInstance().font, Component.nullToEmpty(data[i][0]), this.x + 5, yPosition + 6, 0xFFFFFF);
            }
        }
    }

    @Override
    public void onPress() {
        this.expanded = !this.expanded;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.expanded) {
            for (int i = 0; i < data.length; i++) {
                int yPosition = this.y + (i + 1) * 20;
                if (mouseX >= this.x && mouseX < this.x + this.width && mouseY >= yPosition && mouseY < yPosition + 20) {
                    this.selectedIndex = i;
                    this.setMessage(Component.nullToEmpty(data[i][0]));
                    this.expanded = false;
                   Consts.warn("Selected index: " + this.selectedIndex); // Debug statement
                   Consts.warn("Selected label: " + data[i][0]); // Debug statement
                    return true;
                }
            }
            this.expanded = false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public String getSelected() {
   
        return data[selectedIndex][0];
    }
}
