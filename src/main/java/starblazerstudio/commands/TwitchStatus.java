package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class TwitchStatus implements ICommandRegister{

    @Override
    public String getDesc() {
        return "blackburn.commands.twitchstat.desc";
    }

    @Override
    public String getName() {
      
        return "blackburn.commands.twitchstat.pre";
    }

    @Override
    public void register(List<String> command, Minecraft mc) {
        
         if(!command.isEmpty()){

            if (command.contains(".twitchstat")){

           
                if(Consts.TwitchConnected == true){

                    mc.gui.getChat().clearMessages(true);

                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > "+"is connected? "+I18n.a("blackburn.twitch.connected.true")));
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.connectacctrue")));
                    command.clear();


                }if(Consts.TwitchConnected == false){
                    mc.gui.getChat().clearMessages(true);
                 
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > "+"is connected? "+I18n.a("blackburn.twitch.connected.false")));
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.connectaccfail")));
                    command.clear();
                }
     

        
            }   


        }
        
    }
}
    
