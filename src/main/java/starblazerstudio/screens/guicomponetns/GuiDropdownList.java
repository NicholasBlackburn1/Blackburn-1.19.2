package starblazerstudio.screens.guicomponetns;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;

public class GuiDropdownList extends Button {
    private final String[][] data;
    private boolean expanded;
    private int selectedIndex;
    private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    
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
            // Render background
            RenderSystem.setShaderTexture(0, BUTTON_TEXTURES);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            blit(poseStack, this.x, this.y + this.height, 0, 46 + (this.isHovered() ? 20 : 0), this.width / 2, 20, 200, 20);
            blit(poseStack, this.x + this.width / 2, this.y + this.height, 200 - this.width / 2, 46 + (this.isHovered() ? 20 : 0), this.width / 2, 20, 200, 20);

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
