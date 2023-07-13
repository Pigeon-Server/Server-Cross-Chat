package cn.pigeon.cross_chat.websocket;

import cn.pigeon.cross_chat.CrossServerChat;
import cn.pigeon.cross_chat.Static.DataType;
import cn.pigeon.cross_chat.handler.ServerEventHandler;
import cn.pigeon.cross_chat.utils.Datapack;
import com.google.gson.Gson;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SocketClient extends WebSocketClient {
    private static final Logger logger = CrossServerChat.logger;
    private static final Gson gson = CrossServerChat.gson;
    private static final Timer timer = new Timer();
    private static boolean connected = false;
    private final TimerTask connectServerTask = new TimerTask() {
        @Override
        public void run() {
            logger.info(new TextComponentTranslation("websocket.connect", CrossServerChat.configuration.host).getFormattedText());
            try {
                connected = getReadyState() == READYSTATE.NOT_YET_CONNECTED ? connectBlocking() : reconnectBlocking();
            } catch (InterruptedException e) {
                connected = false;
            }
            if (connected) {
                logger.info(new TextComponentTranslation("websocket.connect.success").getFormattedText());
            } else {
                logger.error(new TextComponentTranslation("websocket.connect.fail").getFormattedText());
            }
            timer.schedule(pingServerTask, 1000, 1000);
            timer.schedule(messageSendTask, 1000, 100);
            this.cancel();
        }
    };
    private final TimerTask pingServerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                if (!connected) {
                    connected = reconnectBlocking();
                    if (connected) {
                        logger.info(new TextComponentTranslation("websocket.reconnect.success", CrossServerChat.configuration.host).getFormattedText());
                    }
                    return;
                }
                send("Ping");
            } catch (Exception ignored) {
                logger.info(new TextComponentTranslation("websocket.ping.fail").getFormattedText());
                connected = false;
            }
        }
    };
    private final TimerTask messageSendTask = new TimerTask() {
        @Override
        public void run() {
            if (ServerEventHandler.queue.size() != 0 && connected) {
                send(ServerEventHandler.queue.remove());
            }
        }
    };

    public SocketClient(URI serverUri) {
        super(serverUri);
    }

    public void startClient() {
        timer.schedule(connectServerTask, 0, 5000);
    }

    public void stopClient() {
        if (getReadyState() == READYSTATE.OPEN){
            close(1000);
        }
        connectServerTask.cancel();
        pingServerTask.cancel();
        messageSendTask.cancel();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        if (Objects.equals(message, "Pong")) {
            connected = true;
            return;
        }
        Datapack datapack = gson.fromJson(message, Datapack.class);
        ITextComponent textComponent;
        switch (datapack.type){
            case DataType.MESSAGE:
                textComponent = new TextComponentString(CrossServerChat.configuration.message
                        .replace("{username}", datapack.username)
                        .replace("{server}", datapack.name)
                        .replace("{message}", datapack.message));
                break;
            case DataType.LOGIN:
                textComponent = new TextComponentString(CrossServerChat.configuration.loginMessage
                        .replace("{username}", datapack.username)
                        .replace("{server}", datapack.name));
                break;
            case DataType.LOGOUT:
                textComponent = new TextComponentString(CrossServerChat.configuration.logoutMessage
                        .replace("{username}", datapack.username)
                        .replace("{server}", datapack.name));
                break;
            default:
                return;
        }
        MinecraftServer server = FMLServerHandler.instance().getServer();
        if (server != null) {
            server.getPlayerList().sendMessage(textComponent);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.debug(new TextComponentTranslation("websocket.close", code).getFormattedText());
    }

    @Override
    public void onError(Exception ex) {

    }
}

