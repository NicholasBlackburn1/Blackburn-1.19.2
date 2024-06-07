package starblazerstudio.screens.guicomponetns;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

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
        super.renderButton(poseStack, mouseX, mouseY, partialTicks);
        if (this.expanded) {
            // Render dropdown items
            for (int i = 0; i < data.length; i++) {
                int yPosition = this.y + (i + 1) * 20;
                drawString(poseStack, Minecraft.getInstance().font, Component.nullToEmpty(data[i][0]), this.x + 5, yPosition + 5, 0xFFFFFF);
            }
        }
    }

    @Override
    public void onPress() {
        super.onPress();
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

