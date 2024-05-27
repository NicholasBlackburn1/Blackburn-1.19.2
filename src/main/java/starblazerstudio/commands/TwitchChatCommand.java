package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class TwitchChatCommand implements ICommandRegister{

    @Override
    public void register(List<String> command, Minecraft mc) {
        if(!command.isEmpty()){

            if (command.contains(".twchat")){
                
                mc.gui.getChat().clearMessages(true);
                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > "));
                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.command.twitchchat.useage")));
                command.clear();
            }

            if (command.contains(".twchat enable")){
                Consts.enableTwitch = true;
                command.clear();
                
                
            }

            if (command.contains(".twchat disable")){
                Consts.enableTwitch = false;
                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > " + "is Disabled!"));
                command.clear();
                
                
            }



        }
    }

    @Override
    public String getDesc() {
        return "blackburn.commands.twitchchat.desc";
    }

    @Override
    public String getName() {
      
        return "blackburn.commands.twitchchat.pre";
    }

}
