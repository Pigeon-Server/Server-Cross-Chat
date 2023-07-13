package cn.pigeon.cross_chat.command.set_command;

import cn.pigeon.cross_chat.CrossServerChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Property;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.setOutputCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.setOutputCommandUsage;

public class SetOutputCommand extends CommandBase {
    @Override
    public String getName() {
        return setOutputCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return setOutputCommandUsage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String value = SetCommandTree.getOutputMessage(args);
        CrossServerChat.configuration.get("Message",
                "SendMessageFormat",
                "[{username}]({server}) : {message}",
                "接受消息输出格式 [default: [{username}]({server}) : {message}]",
                Property.Type.STRING).set(value);
        CrossServerChat.configuration.saveConfig();
        sender.sendMessage(new TextComponentTranslation("command.set.output.success", value));
    }
}
