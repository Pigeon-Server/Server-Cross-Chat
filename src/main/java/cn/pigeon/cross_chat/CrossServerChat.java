package cn.pigeon.cross_chat;

import cn.pigeon.cross_chat.command.RootCommandTree;
import cn.pigeon.cross_chat.config.ConfigHandler;
import cn.pigeon.cross_chat.websocket.SocketClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URI;

import static cn.pigeon.cross_chat.Static.configFileName;
import static cn.pigeon.cross_chat.Static.configVersion;

@Mod(modid = CrossServerChat.MOD_ID, name = CrossServerChat.NAME, version = CrossServerChat.VERSION, acceptableRemoteVersions = "*", serverSideOnly = true)
public class CrossServerChat {
    public static final String MOD_ID = "cross_chat";
    public static final String NAME = "Cross Server Chat";
    public static final String VERSION = "0.1.1";
    public static Logger logger = LogManager.getLogger(NAME);
    public static ConfigHandler configuration;
    public static SocketClient socketClient;
    public static final Gson gson = new GsonBuilder().create();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File config = new File(event.getModConfigurationDirectory(), configFileName);
        configuration = new ConfigHandler(config, configVersion);
    }


    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new RootCommandTree());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            socketClient = new SocketClient(new URI(configuration.host));
            logger.info(new TextComponentTranslation("websocket.create.success", configuration.host).getFormattedText());
        } catch (Exception e) {
            socketClient = null;
            logger.error(new TextComponentTranslation("websocket.create.fail").getFormattedText());
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        if (socketClient != null) {
            socketClient.startClient();
        }
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        if (socketClient != null) {
            socketClient.stopClient();
            socketClient.close(1000);
        }
    }
}
