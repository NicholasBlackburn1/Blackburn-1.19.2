package starblazerstudio.commands;

import java.util.List;

import net.minecraft.client.Minecraft;

/**
 * interface for registering command classes
 */
public interface ICommandRegister{

    public void register(List<String> command,Minecraft mc);
    
    public String getName();

    public String getDesc();
}