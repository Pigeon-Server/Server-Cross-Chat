package cn.pigeon.cross_chat.command;

import cn.pigeon.cross_chat.command.set_command.SetCommandTree;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.rootCommand;
import static cn.pigeon.cross_chat.Static.Command.CommandUsage.rootCommandUsage;

public class RootCommandTree extends CommandTreeBase {
    public RootCommandTree() {
        this.addSubcommand(new ReloadCommand());
        this.addSubcommand(new SetCommandTree());
    }

    @Override
    public String getName() {
        return rootCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return rootCommandUsage;
    }
}
