package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.chatconsts;

public class CommandRegister {
    
    HelpCommand help = new HelpCommand();
    

    public void registerCommands(Minecraft mc){


        List<String> command = chatconsts.enteredcommands;

        
        help.register(command, mc);
      




        
    }

   

    // adds commands 
    public void addToCommandList(){
        Consts.commands.add(0,help.getName());
      
        
    }


    public void addToCommandDescList(){
        Consts.commanddesc.add(0,help.getDesc());
     
        
    }

    
}
