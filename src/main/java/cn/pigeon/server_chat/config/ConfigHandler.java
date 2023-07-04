package cn.pigeon.server_chat.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler extends Configuration {
    public String host;
    public String message;
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
        host = getString("WebsocketServerAddress", "Websocket", "ws://127.0.0.1:3000/server-message", "Websocket��������ַ");
        message = getString("SendMessageFormat", "Message", "[{username}]({server}) : {message}", "������Ϣ�����ʽ");
        name = getString("ServerName", "Message", "Server", "��Ϣ���͵ķ���������");
        this.saveConfig();
    }

    public void saveConfig(){
        if (this.hasChanged()){
            this.save();
        }
    }
}
