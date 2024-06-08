package starblazerstudio.screens.guicomponetns;

package starblazerstudio.screens.guicomponetns;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class GuiToastNotification extends AbstractWidget {
    private final Minecraft minecraft;
    private long displayTime;
    private long fadeOutStartTime;
    private boolean isFadingOut;
    private String message;

    public GuiToastNotification(Minecraft minecraft, int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
        this.minecraft = minecraft;
        this.message = message.getString();
        this.displayTime = System.currentTimeMillis() + 3000; // Display for 3 seconds
        this.fadeOutStartTime = displayTime - 1000; // Start fading out 1 second before disappearing
        this.isFadingOut = false;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= fadeOutStartTime && currentTime < displayTime) {
            isFadingOut = true;
        } else if (currentTime >= displayTime) {
            this.visible = false;
        }

        if (this.visible) {
            int alpha = isFadingOut ? (int) (255 - (255 * (currentTime - fadeOutStartTime) / 1000)) : 255;
            int color = (alpha << 24) | 0xFFFFFF; // White text with varying alpha

            fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x80000000); // Background
            drawCenteredString(poseStack, minecraft.font, this.message, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
        }
    }
}
