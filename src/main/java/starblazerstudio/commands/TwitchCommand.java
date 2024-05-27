package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class TwitchCommand implements ICommandRegister{

    @Override
    public String getDesc() {
        return "blackburn.commands.twitch.desc";
    }

    @Override
    public String getName() {
      
        return "blackburn.commands.twitch.pre";
    }

    @Override
    public void register(List<String> command, Minecraft mc) {
        
         if(!command.isEmpty()){

            if (command.contains(".twitch")){

           
                }if(Consts.TwitchConnected.contains(I18n.a("blackburn.twitch.connected.true"))){

                    mc.gui.getChat().clearMessages(true);

                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > "+"is connected? "+I18n.a("blackburn.twitch.connected.true")));
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.connectacctrue")));
                    command.clear();


                }else{
                    mc.gui.getChat().clearMessages(true);
                 
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > "+"is connected? "+I18n.a("blackburn.twitch.connected.false")));
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.connectaccfail")));
                    command.clear();
                }

                   
                
                    
                
        
            }   


        }
        
    }
    
