package cn.pigeon.server_chat.handler;

import cn.pigeon.server_chat.CrossServerChat;
import cn.pigeon.server_chat.utils.ChatData;
import cn.pigeon.server_chat.utils.HeartBeat;
import cn.pigeon.server_chat.utils.SocketClient;
import com.google.gson.Gson;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CrossServerChat.MOD_ID, value = Side.SERVER)
public class ServerEventHandler {
    static final List<String> list = new ArrayList<>();
    private static final Gson gson = CrossServerChat.gson;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void chatReceived(ServerChatEvent event) {
        list.add(gson.toJson(new ChatData(CrossServerChat.configuration.name, event.getUsername(), event.getMessage())));
//        try {
//            if (!socketClient.isOpen() && hb.getStatus()) {
//                socketClient.connectBlocking();
//            }
//            socketClient.send(gson.toJson(new ChatData(CrossServerChat.configuration.name, event.getUsername(), event.getMessage())));
//        } catch (InterruptedException e){
//            logger.error(e.getMessage());
//        }
    }
}
