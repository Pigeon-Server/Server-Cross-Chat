package cn.pigeon.cross_chat.command.set_command;

import cn.pigeon.cross_chat.CrossServerChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Property;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.setHostCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.setHostCommandUsage;
import static cn.pigeon.cross_chat.utils.Utils.judgeWebsocketAddress;

public class SetHostCommand extends CommandBase {
    @Override
    public String getName() {
        return setHostCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return setHostCommandUsage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException(new TextComponentTranslation("error.args").getFormattedText());
        }
        if (args[0].startsWith("wss://")) {
            throw new CommandException(new TextComponentTranslation("error.set.host.wss").getFormattedText());
        }
        if (!args[0].startsWith("ws://")) {
            throw new CommandException(new TextComponentTranslation("error.set.host.head").getFormattedText());
        }
        if (!judgeWebsocketAddress(args[0])) {
            throw new CommandException(new TextComponentTranslation("error.set.host.illegal").getFormattedText());
        }
        CrossServerChat.configuration.get("Websocket",
                "WebsocketServerAddress",
                "ws://127.0.0.1:3000/server-message",
                "Websocket服务器地址 [default: ws://127.0.0.1:3000/server-message]",
                Property.Type.STRING).set(args[0]);
        CrossServerChat.configuration.saveConfig();
        sender.sendMessage(new TextComponentTranslation("command.set.host.success", args[0]));
    }
}
