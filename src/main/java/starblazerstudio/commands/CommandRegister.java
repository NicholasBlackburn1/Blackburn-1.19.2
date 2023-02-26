package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.chatconsts;

public class CommandRegister {
    
    HelpCommand help = new HelpCommand();
    VersionCommand version = new VersionCommand();

    public void registerCommands(Minecraft mc){


        List<String> command = chatconsts.enteredcommands;

        
        help.register(command, mc);
        version.register(command, mc);
      




        
    }

   

    // adds commands 
    public void addToCommandList(){
        chatconsts.commands.add(0,help.getName());
        chatconsts.commands.add(1,version.getName());
      
        
    }


    public void addToCommandDescList(){
        chatconsts.commanddesc.add(0,help.getDesc());
        chatconsts.commanddesc.add(1,version.getDesc());
        
    }

    
}
