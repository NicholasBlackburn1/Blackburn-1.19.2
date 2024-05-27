package starblazerstudio.twitch;

import net.minecraft.client.Options;
import starblazerstudio.screens.*;
import starblazerstudio.utils.Consts;
import net.minecraft.client.*;
public class TwitchEnabler {
    
    public void enableTwitch(Options opt){
        
        if(!opt.twitchchatenable.isDown()){
          
            Consts.enableTwitch = true;
        }else{
            
        }
    }

}
