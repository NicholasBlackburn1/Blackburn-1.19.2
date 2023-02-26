package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class VersionCommand implements ICommandRegister{

    @Override
    // This is where the command gets registered to run
    public void register(List<String> command,Minecraft mc) {
        
       
        
        if(!command.isEmpty()){

            if (command.contains(".version")){

                   
                
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.version")).append(" "+Consts.VERSION));

                    command.clear();
                
        
            }   


        }
    }
          

    @Override
    public String getName(){
        return "blackburn.commands.version.pre";
    }
    @Override
    public  String getDesc(){
        return "blackburn.commands.version.desc";
    }


}

