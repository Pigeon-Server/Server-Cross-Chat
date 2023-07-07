package cn.pigeon.server_chat;

import cn.pigeon.server_chat.config.ConfigHandler;
import cn.pigeon.server_chat.handler.MessageSendHandler;
import cn.pigeon.server_chat.utils.HeartBeat;
import cn.pigeon.server_chat.utils.SocketClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = CrossServerChat.MOD_ID, name = CrossServerChat.NAME, version = CrossServerChat.VERSION, acceptableRemoteVersions = "*", serverSideOnly = true)
public class CrossServerChat {
    public static final String MOD_ID = "cross_chat";
    public static final String NAME = "Cross Server Chat";
    public static final String VERSION = "1.0";
    public static Logger logger = LogManager.getLogger(NAME);
    public static ConfigHandler configuration;
    public static SocketClient socketClient;
    public static final Gson gson = new GsonBuilder().create();
    public static final HeartBeat hb = new HeartBeat();
    public static final Thread hb_thread = new Thread(hb);
    public static final Thread msh_thread = new Thread(new MessageSendHandler());

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File config = new File(event.getModConfigurationDirectory(), "cross_chat.cfg");
        configuration = new ConfigHandler(config, "0.1.0");
    }

//  TODO 命令部分

//    @EventHandler
//    public void onServerStarting(FMLServerStartingEvent event) {
//        event.registerServerCommand(new RootCommand());
//    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            socketClient = new SocketClient(new URI(configuration.host));
            logger.debug("已创建客户端"+configuration.host);
        } catch (Exception e) {
            logger.error("客户端启动失败！\n"+e.getMessage());
        }
    }

    @Mod.EventHandler
    public void LoadWorlded(FMLServerStartedEvent event) {
        socketClient.connect();
        hb_thread.start();

        logger.debug("发信线程已启动");
        msh_thread.start();
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        logger.info("关闭中~");
        if (hb.HeartBeat_Status()) {
            hb.stop_HeartBeat();
        }
        socketClient.close(1000);
    }
}
