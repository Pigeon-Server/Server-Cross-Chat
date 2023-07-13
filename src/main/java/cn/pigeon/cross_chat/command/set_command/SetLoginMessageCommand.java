package cn.pigeon.cross_chat.command.set_command;

import cn.pigeon.cross_chat.CrossServerChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Property;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.setLoginMessageCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.setLoginMessageCommandUsage;


public class SetLoginMessageCommand extends CommandBase {
    @Override
    public String getName() {
        return setLoginMessageCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return setLoginMessageCommandUsage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String value = SetCommandTree.getOutputMessage(args);
        CrossServerChat.configuration.get("Message",
                "LogInMessageFormat",
                "{username} login {server}",
                "玩家上线输出格式 [default: {username} login {server}",
                Property.Type.STRING).set(value);
        CrossServerChat.configuration.saveConfig();
        sender.sendMessage(new TextComponentTranslation("command.set.output.login.success", value));
    }
}
