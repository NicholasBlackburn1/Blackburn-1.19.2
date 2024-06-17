package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class LewdVersion implements ICommandRegister {

        @Override
    // This is where the command gets registered to run
    public void register(List<String> command,Minecraft mc) {
        
       
        
        if(!command.isEmpty()){

            if (command.contains(".lewd") && Consts.ishorny == true){

                   
                
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.lewd.version")).append(" "+Consts.VERSION));

                    command.clear();
                
        
            }  else {
                if(command.contains(".lewd")){
                    mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.lewd.no_access")));
                    command.clear();
                }
            }


        }
    }
          

    @Override
    public String getName(){
        return "blackburn.commands.lewd.pre";
    }
    @Override
    public  String getDesc(){
        return "blackburn.commands.lewd.desc";
    }


}


