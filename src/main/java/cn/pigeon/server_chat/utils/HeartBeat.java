package cn.pigeon.server_chat.utils;

import cn.pigeon.server_chat.CrossServerChat;
import org.apache.logging.log4j.Logger;

public class HeartBeat implements Runnable{
    private boolean heartBeat_Status = true;
    private boolean Status = true;
    private static final Logger logger = CrossServerChat.logger;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (heartBeat_Status) {
            if (CrossServerChat.socketClient != null) {
                try {
                    CrossServerChat.socketClient.send("HeartBeat");

                    if (!this.Status) {
                        logger.info("连接已恢复");
                    }

                    this.Status = true;
                } catch (Exception e) {
                    this.Status = false;
                    logger.warn("连接中断，正在重连");

                    try {
                        CrossServerChat.socketClient.reconnectBlocking();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Boolean getStatus() {
        return Status;
    }

    public boolean HeartBeat_Status() {
        return heartBeat_Status;
    }

    public void stop_HeartBeat() {
        this.heartBeat_Status = false;
        logger.debug("停跳");
    }
}
