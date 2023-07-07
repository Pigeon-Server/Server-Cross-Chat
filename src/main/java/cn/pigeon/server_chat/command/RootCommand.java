package cn.pigeon.server_chat.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.command.CommandTreeBase;

public class RootCommand extends CommandTreeBase {
    public RootCommand() {
        this.addSubcommand(new ReloadCommand());
    }

    @Override
    public String getName() {
        // 设置命令的名称
        return "crosschat";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        // 设置命令的使用方法
        return "/crosschat";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        // 命令的具体逻辑
        sender.sendMessage(new TextComponentString("This is my custom command!"));
    }

}
