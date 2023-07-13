package cn.pigeon.cross_chat.handler;

import cn.pigeon.cross_chat.CrossServerChat;
import cn.pigeon.cross_chat.Static.DataType;
import cn.pigeon.cross_chat.utils.Datapack;
import com.google.gson.Gson;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.LinkedList;
import java.util.Queue;


@Mod.EventBusSubscriber(modid = CrossServerChat.MOD_ID, value = Side.SERVER)
public class ServerEventHandler {
    public static final Queue<String> queue = new LinkedList<>();
    private static final Gson gson = CrossServerChat.gson;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void chatReceived(ServerChatEvent event) {
        queue.add(gson.toJson(new Datapack(CrossServerChat.configuration.name, event.getUsername(), event.getMessage(), DataType.MESSAGE)));
    }

    @SubscribeEvent
    public static void playerLoginIn(PlayerLoggedInEvent event) {
        queue.add(gson.toJson(new Datapack(CrossServerChat.configuration.name, event.player.getName(), "", DataType.LOGIN)));
    }

    @SubscribeEvent
    public static void playerLoginOut(PlayerLoggedOutEvent event) {
        queue.add(gson.toJson(new Datapack(CrossServerChat.configuration.name, event.player.getName(), "", DataType.LOGOUT)));
    }
}
