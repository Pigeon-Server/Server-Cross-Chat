package cn.pigeon.server_chat.handler;

import cn.pigeon.server_chat.CrossServerChat;
import cn.pigeon.server_chat.utils.HeartBeat;
import cn.pigeon.server_chat.utils.SocketClient;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MessageSendHandler implements Runnable {
    public static List<String> list = cn.pigeon.server_chat.handler.ServerEventHandler.list;
    private static final Logger logger = CrossServerChat.logger;
    private static final HeartBeat hb = CrossServerChat.hb;
    @Override
    public void run() {
        while (true) {
            if (list.size() != 0 && hb.getStatus()) {
                try {
                    CrossServerChat.socketClient.send(list.get(0));
                    list.remove(0);
                } catch (Exception e) {
                    logger.error("信息发送失败！\n"+e.getMessage());
                }
            }
//            else if (!hb.getStatus()) {
//                logger.debug("连接中断，等待重发");
//            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
