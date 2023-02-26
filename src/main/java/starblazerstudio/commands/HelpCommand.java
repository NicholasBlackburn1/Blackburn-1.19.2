package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.chatconsts;


/**
 * this registers the cthelpcommand in the command rfister
 */
public class HelpCommand implements ICommandRegister{

 
    @Override
    public void register(List<String> command,Minecraft mc) {
        
        int i = 0;
                
            if(!command.isEmpty()){

                if (command.contains(".help")){
                        mc.gui.getChat().addMessage(Component.translatable("blackburn.commands.help"));

                        // lists all the available commands loaded
                        while (true){
                            mc.gui.getChat().addMessage(Component.translatable(I18n.atrue(chatconsts.commands.get(i).toString())+" -> "+I18n.a(chatconsts.commanddesc.get(i).toString())));
                            i++;
                            if(i == chatconsts.commands.size()){
                                break;
                            }
                        }
                    

                    command.clear();
               


            }}
        }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "blackburn.commands.help.pre";
    }

    @Override
    public String getDesc() {
        // TODO Auto-generated method stub
        return "blackburn.commands.help.desc";
    }

  
}
