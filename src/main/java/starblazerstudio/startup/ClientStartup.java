package starblazerstudio.startup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import starblazerstudio.utils.Consts;
public class ClientStartup{

    
      // allows me to send start up messa
      private void messages(){
        Consts.minecraft.gui.getChat().addMessage(new Component.translatable("blackburn.message.startup"));
        Consts.minecraft.gui.getChat().addMessage(new  Component.translatable("blackburn.message.howto"));
    }


     //allows me to send start up messages
     public void sendStartupMessages(Minecraft mine){

      if (!mine.pause && Consts.showStart) {
          messages();
          Consts.showStart = false;
      }
     }
}