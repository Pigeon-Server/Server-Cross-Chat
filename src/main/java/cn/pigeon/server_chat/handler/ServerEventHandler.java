package cn.pigeon.server_chat.handler;

import cn.pigeon.server_chat.CrossServerChat;
import cn.pigeon.server_chat.utils.ChatData;
import cn.pigeon.server_chat.utils.SocketClient;
import com.google.gson.Gson;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = CrossServerChat.MOD_ID, value = Side.SERVER)
public class ServerEventHandler {
    private static final Logger logger = CrossServerChat.logger;
    private static final SocketClient socketClient = CrossServerChat.socketClient;
    private static final Gson gson = CrossServerChat.gson;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void chatReceived(ServerChatEvent event) {
        try {
            if (!socketClient.isOpen()) {
                socketClient.connectBlocking();
            }
            socketClient.send(gson.toJson(new ChatData(CrossServerChat.configuration.name, event.getUsername(), event.getMessage())));
        } catch (InterruptedException e){
            logger.error(e.getMessage());
        }
    }
}
