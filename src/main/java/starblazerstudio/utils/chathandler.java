package starblazerstudio.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;

public class chathandler {
    

    /**
     * thisallows me to grab one sidded mesages for my client
     * @param currentscreen
     * @param input
     * @param mc
     * @return
     */
    public boolean getUserinput(ChatScreen currentscreen,EditBox input, Minecraft mc){
        String s = input.getValue().trim();
                
        // allows me to send one sided messaged
        final GuiUtils utils = new GuiUtils();
        utils.allowOneSidedMessages(s, currentscreen, mc);
        return false;
    }
}
