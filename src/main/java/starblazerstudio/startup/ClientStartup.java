package starblazerstudio.startup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import starblazerstudio.commands.CommandRegister;
import starblazerstudio.utils.Consts;
public class ClientStartup{

  private CommandRegister register = new CommandRegister();
    
      // allows me to send start up messa
      private void messages(){
        Consts.minecraft.gui.getChat().addMessage(Component.translatable("blackburn.message.startup"));
        Consts.minecraft.gui.getChat().addMessage(Component.translatable("blackburn.message.howto"));
    }


     //allows me to send start up messages
     public void sendStartupMessages(Minecraft mine){
      

      // allows me to register commands and startup stuff
      if (!mine.pause && Consts.showStart) {
          if(mine.level != null){
            messages();
            Consts.showStart = false;

            register.addToCommandDescList();
            register.addToCommandList();
            
          }
        }

      }  
}