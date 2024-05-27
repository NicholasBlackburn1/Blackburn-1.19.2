package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.chatconsts;

public class CommandRegister {
    
    HelpCommand help = new HelpCommand();
    VersionCommand version = new VersionCommand();
    TwitchStatus twitchstatus = new TwitchStatus();
    TwitchChatCommand twitchChat = new TwitchChatCommand();

    public void registerCommands(Minecraft mc){


        List<String> command = chatconsts.enteredcommands;

        
        help.register(command, mc);
        version.register(command, mc);
        twitchstatus.register(command,mc);
        twitchChat.register(command,mc);
      




        
    }

   

    // adds commands 
    public void addToCommandList(){
        chatconsts.commands.add(0,help.getName());
        chatconsts.commands.add(1,version.getName());
        chatconsts.commands.add(2,twitchstatus.getName());
        chatconsts.commands.add(3,twitchChat.getName());
      
        
    }


    public void addToCommandDescList(){
        chatconsts.commanddesc.add(0,help.getDesc());
        chatconsts.commanddesc.add(1,version.getDesc());
        chatconsts.commanddesc.add(2,twitchstatus.getDesc());
        chatconsts.commanddesc.add(3,twitchChat.getDesc());
        
    }

    
}
