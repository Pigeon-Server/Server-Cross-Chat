package cn.pigeon.cross_chat.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler extends Configuration {
    public String host;
    public String message;
    public String loginMessage;
    public String logoutMessage;
    public String name;

    public ConfigHandler(File file) {
        super(file);
        loadConfig();
    }

    public ConfigHandler(File file, String configVersion) {
        super(file, configVersion);
        loadConfig();
    }

    public void loadConfig() {
        host = getString("WebsocketServerAddress", "Websocket", "ws://127.0.0.1:3000/server-message", "Websocket服务器地址");
        message = getString("SendMessageFormat", "Message", "[{username}]({server}) : {message}", "接受消息输出格式");
        loginMessage = getString("LogInMessageFormat", "Message", "{username} login {server}", "玩家上线输出格式");
        logoutMessage = getString("LogOutMessageFormat", "Message", "{username} logout {server}", "玩家下线输出格式");
        name = getString("ServerName", "Message", "Server", "消息发送的服务器名称");
        this.saveConfig();
    }

    public void saveConfig() {
        if (this.hasChanged()) {
            this.save();
        }
    }
}
