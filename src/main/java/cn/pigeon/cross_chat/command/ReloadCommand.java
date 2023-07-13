package cn.pigeon.cross_chat.command;

import cn.pigeon.cross_chat.CrossServerChat;
import cn.pigeon.cross_chat.websocket.SocketClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

import java.net.URI;
import java.net.URISyntaxException;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.reloadCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.reloadCommandUsage;

public class ReloadCommand extends CommandBase {
    @Override
    public String getName() {
        return reloadCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return reloadCommandUsage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        SocketClient temp = CrossServerChat.socketClient;
        try {
            sender.sendMessage(new TextComponentTranslation("config.reload"));
            CrossServerChat.socketClient.stopClient();
            CrossServerChat.configuration.loadConfig();
            try {
                CrossServerChat.socketClient = new SocketClient(new URI(CrossServerChat.configuration.host));
                CrossServerChat.socketClient.startClient();
            } catch (URISyntaxException e) {
                CrossServerChat.logger.error(e.getMessage());
            }
        } catch (Exception e) {
            CrossServerChat.logger.error(e.getMessage());
            sender.sendMessage(new TextComponentTranslation("config.reload.fail"));
            if (temp != null) {
                CrossServerChat.socketClient = temp;
                CrossServerChat.socketClient.startClient();
            }
            return;
        }
        sender.sendMessage(new TextComponentTranslation("config.reload.success"));
    }
}
