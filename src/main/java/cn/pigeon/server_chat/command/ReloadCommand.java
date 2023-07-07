package cn.pigeon.server_chat.command;

import cn.pigeon.server_chat.CrossServerChat;
import cn.pigeon.server_chat.utils.SocketClient;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class ReloadCommand implements ICommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/crosschat reload";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            sender.sendMessage(new TextComponentString("正在重载配置文件"));
            CrossServerChat.socketClient.close(1000);
            CrossServerChat.configuration.loadConfig();
            try {
                CrossServerChat.socketClient = new SocketClient(new URI(CrossServerChat.configuration.host));
                CrossServerChat.socketClient.connectBlocking();
            } catch (URISyntaxException e) {
                CrossServerChat.logger.error(e.getMessage());
            } catch (InterruptedException e) {
                CrossServerChat.logger.error(e.getMessage());
                sender.sendMessage(new TextComponentString("WS服务器连接失败"));
            }
        } catch (Exception e){
            CrossServerChat.logger.error(e.getMessage());
            sender.sendMessage(new TextComponentString("配置文件加载失败"));
            return;
        }
        sender.sendMessage(new TextComponentString("配置文件重载成功"));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
