package cn.pigeon.cross_chat.command.set_command;

import cn.pigeon.cross_chat.CrossServerChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Property;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.setLogoutMessageCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.setLogoutMessageCommandUsage;

public class SetLogoutMessageCommand extends CommandBase {
    @Override
    public String getName() {
        return setLogoutMessageCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return setLogoutMessageCommandUsage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String value = SetCommandTree.getOutputMessage(args);
        CrossServerChat.configuration.get("Message",
                "LogOutMessageFormat",
                "{username} logout {server}",
                "玩家下线输出格式 [default: [{username} logout {server}]",
                Property.Type.STRING).set(value);
        CrossServerChat.configuration.saveConfig();
        sender.sendMessage(new TextComponentTranslation("command.set.output.success", value));
    }
}
