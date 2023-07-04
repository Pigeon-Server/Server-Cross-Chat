package cn.pigeon.server_chat;

import cn.pigeon.server_chat.command.RootCommand;
import cn.pigeon.server_chat.config.ConfigHandler;
import cn.pigeon.server_chat.utils.SocketClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URI;

@Mod(modid = CrossServerChat.MOD_ID, name = CrossServerChat.NAME, version = CrossServerChat.VERSION, acceptableRemoteVersions = "*", serverSideOnly = true)
public class CrossServerChat {
    public static final String MOD_ID = "cross_chat";
    public static final String NAME = "Cross Server Chat";
    public static final String VERSION = "1.0";
    public static Logger logger = LogManager.getLogger(NAME);
    public static ConfigHandler configuration;
    public static SocketClient socketClient;
    public static final Gson gson = new GsonBuilder().create();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File config = new File(event.getModConfigurationDirectory(), "cross_chat.cfg");
        configuration = new ConfigHandler(config, "0.1.0");
    }

//    @EventHandler
//    public void onServerStarting(FMLServerStartingEvent event) {
//        event.registerServerCommand(new RootCommand());
//    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            socketClient = new SocketClient(new URI(configuration.host));
            socketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        socketClient.close(1000);
    }
}
