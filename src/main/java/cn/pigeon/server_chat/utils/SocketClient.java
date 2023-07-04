package cn.pigeon.server_chat.utils;

import cn.pigeon.server_chat.CrossServerChat;
import com.google.gson.Gson;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class SocketClient extends WebSocketClient {

    private static final Gson gson = CrossServerChat.gson;

    public SocketClient(URI serverUri) {
        super(serverUri);
    }

    public SocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public SocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public SocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public SocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        ChatData chatData = gson.fromJson(message, ChatData.class);
        ITextComponent textComponent = new TextComponentString(CrossServerChat.configuration.message
                .replace("{username}", chatData.username)
                .replace("{server}", chatData.name)
                .replace("{message}", chatData.message));
        MinecraftServer server = FMLServerHandler.instance().getServer();
        if (server != null) {
            server.getPlayerList().sendMessage(textComponent);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}

