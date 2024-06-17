package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.chatconsts;

public class CommandRegister {
    
    HelpCommand help = new HelpCommand();
    VersionCommand version = new VersionCommand();
    TwitchStatus twitchstatus = new TwitchStatus();
    TwitchChatCommand twitchChat = new TwitchChatCommand();
    LewdVersion lewdVersion = new LewdVersion();
    CrashCommand crashCommand = new CrashCommand();

    public void registerCommands(Minecraft mc){


        List<String> command = chatconsts.enteredcommands;

        
        help.register(command, mc);
        version.register(command, mc);
        twitchstatus.register(command,mc);
        twitchChat.register(command,mc);
        lewdVersion.register(command,mc);
        crashCommand.register(command,mc);
      

        // enables twitch messages to diusplay in chat
        if(Consts.enableTwitch == true && Consts.TwitchConnected == true){

            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.twitch.pre.col")).append(" > "+Consts.finalmessage));
            
            // trys to remove repet messges
            if (mc.gui.getChat().getRecentChat().toString().equals(Consts.finalmessage)){
                Consts.error("shouldnt be copying string .... "+ Consts.finalmessage);
            }
           

        }




        
    }

   

    // adds commands 
    public void addToCommandList(){
        chatconsts.commands.add(0,help.getName());
        chatconsts.commands.add(1,version.getName());
        chatconsts.commands.add(2,twitchstatus.getName());
        chatconsts.commands.add(3,twitchChat.getName());
        chatconsts.commands.add(4,lewdVersion.getName());
        chatconsts.commands.add(5,crashCommand.getName());
      
        
    }


    public void addToCommandDescList(){
        chatconsts.commanddesc.add(0,help.getDesc());
        chatconsts.commanddesc.add(1,version.getDesc());
        chatconsts.commanddesc.add(2,twitchstatus.getDesc());
        chatconsts.commanddesc.add(3,twitchChat.getDesc());
        chatconsts.commanddesc.add(4,lewdVersion.getDesc());
        chatconsts.commanddesc.add(5,crashCommand.getDesc());
    }

    
}
