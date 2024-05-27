package starblazerstudio.twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;

import starblazerstudio.utils.ChatColorCodes;
import starblazerstudio.utils.Consts;
import starblazerstudio.utils.McColors;

public class recvChannle {



    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     * @return 
     */
    public recvChannle(SimpleEventHandler eventHandler) {

        eventHandler.onEvent(ChannelMessageEvent.class, event -> {

            if (event.getMessage() == null){

            }else{
                // sets colors of name
                ChatColorCodes codes = new ChatColorCodes();
                Consts.chatuser.add(


                event.getUser().getName());
                Consts.chatmessage.add(event.getMessage());
                
               Consts.finalmessage = (codes.chatColor(McColors.RED)+event.getUser().getName()+codes.chatColor(McColors.WHITE)+":"+codes.chatColor(McColors.GREEN)+event.getMessage());
                
            }
        
        });
        
    }

    
}
