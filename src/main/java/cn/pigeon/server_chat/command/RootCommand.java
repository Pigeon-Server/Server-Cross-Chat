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
        // �������������
        return "crosschat";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        // ���������ʹ�÷���
        return "/crosschat";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        // ����ľ����߼�
        sender.sendMessage(new TextComponentString("This is my custom command!"));
    }

}
