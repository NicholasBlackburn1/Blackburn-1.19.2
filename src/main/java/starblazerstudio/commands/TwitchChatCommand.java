package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class TwitchChatCommand implements ICommandRegister{

    @Override
    public void register(List<String> command, Minecraft mc) {
        if(!command.isEmpty()){

            if (command.contains(".twitchchat")){
                
                mc.gui.getChat().clearMessages(true);
                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.command.twitchchat.useage")));
                command.clear();
            }

            if (command.contains(".twitchchat enable")){
                mc.gui.getChat().clearMessages(true);
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
