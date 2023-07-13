package cn.pigeon.cross_chat.command.set_command;

import cn.pigeon.cross_chat.CrossServerChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Property;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.setNameCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.setNameCommandUsage;

public class SetNameCommand extends CommandBase{
    @Override
    public String getName() {
        return setNameCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return setNameCommandUsage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1){
            throw new CommandException(new TextComponentTranslation("error.args").getFormattedText());
        }
        CrossServerChat.configuration.get("Message",
                "ServerName",
                "Server",
                "消息发送的服务器名称 [default: Server]",
                Property.Type.STRING).set(args[0]);
        CrossServerChat.configuration.saveConfig();
        sender.sendMessage(new TextComponentTranslation("command.set.name.success", args[0]));
    }
}
