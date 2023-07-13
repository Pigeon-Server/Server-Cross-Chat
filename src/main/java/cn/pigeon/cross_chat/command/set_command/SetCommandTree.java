package cn.pigeon.cross_chat.command.set_command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.command.CommandTreeBase;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.setCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.setCommandUsage;

public class SetCommandTree extends CommandTreeBase {
    public SetCommandTree() {
        this.addSubcommand(new SetHostCommand());
        this.addSubcommand(new SetNameCommand());
        this.addSubcommand(new SetOutputCommand());
        this.addSubcommand(new SetLoginMessageCommand());
        this.addSubcommand(new SetLogoutMessageCommand());
    }

    public static String getOutputMessage(String[] args) throws CommandException {
        if (args.length == 0){
            throw new CommandException(new TextComponentTranslation("error.args").getFormattedText());
        }
        StringBuilder str = new StringBuilder();
        for (String arg : args) {
            str.append(arg).append(" ");
        }
        return str.toString().replace("&", "§").trim();
    }

    @Override
    public String getName() {
        return setCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return setCommandUsage;
    }
}
