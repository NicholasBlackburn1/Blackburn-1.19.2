package starblazerstudio.startup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import starblazerstudio.commands.CommandRegister;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.chatconsts;
public class ClientStartup{

  
    
      // allows me to send start up messa
      private void messages(){
        Consts.minecraft.gui.getChat().addMessage(Component.translatable("blackburn.message.startup"));
        Consts.minecraft.gui.getChat().addMessage(Component.translatable("blackburn.message.howto"));
    }


     //allows me to send start up messages
     public void sendStartupMessages(Minecraft mine){
      
      CommandRegister register = new CommandRegister();
      
      // registes the commands
      if(!mine.pause){
        register.registerCommands(mine);
      }

      // runs startup and registers commands
      if(mine.level != null){
        
        if (!mine.pause && Consts.showStart) {
            register.addToCommandDescList();
            register.addToCommandList();
         
            messages();
            Consts.showStart = false;
        }


      }  
     }
     }
    
    
    
    