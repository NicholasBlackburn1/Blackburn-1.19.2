/**
 * 
 * this is my crash command it should crash the game for dev purpers
 */

package starblazerstudio.commands;

import java.util.List;

import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class CrashCommand implements ICommandRegister{

    @SuppressWarnings("static-access")
    @Override
    public void register(List<String> command, Minecraft mc) {
         
        if(!command.isEmpty()){

            if(command.contains(".crash")){

                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.crash.usage")));

                command.clear();
            } else{
                if(command.contains(".crash accept")){
                    try {
                        // Intentionally throw an exception to simulate a crash
                        throw new RuntimeException("This is a test exception for generating a crash report");
                    } catch (Throwable t) {
                        mc.crash(new CrashReport("Crashed on Request By user"+ mc.getUser().getName(),t));
                }
            }
        }
    }
        

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
       return "blackburn.crashcommand.pre";
    }

    @Override
    public String getDesc() {
        // TODO Auto-generated method stub
        return "blackburn.crashccommand.desc";
    }
}