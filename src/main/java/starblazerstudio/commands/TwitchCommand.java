package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class TwitchCommand implements ICommandRegister{

    @Override
    public String getDesc() {
        return "blackburn.commands.twich.pre";
    }

    @Override
    public String getName() {
      
        return "blackburn.commands.twitch.desc";
    }

    @Override
    public void register(List<String> command, Minecraft mc) {
        
         if(!command.isEmpty()){

            if (command.contains(".twitch")){

                // if twitch isnt connected
                if(Consts.TwitchConnected.contains("§4 NO!")){

                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.version.pre")).append(" "+"is connected? "+I18n.a("blackburn.twitch.connected.false")));

                    command.clear();

                }

                   
                
                    
                
        
            }   


        }
        
    }
    
}
