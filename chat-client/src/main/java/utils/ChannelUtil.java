package utils;

import io.netty.channel.Channel;

import java.util.concurrent.TimeUnit;

/**
 * channel的工具类
 */
public class ChannelUtil {

    public static void sengLoginMsg(Channel channel,Long uid) {
        channel.writeAndFlush(ChatMsgUtil.builLoginMsg(uid));
    }

    /**
     * 开启发送ping消息的定时任务
     * @param channel
     * @param fromUid
     */
    public static void startSendPingMsgSchedule(Channel channel, Long fromUid) {
        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(ChatMsgUtil.buildPingMsg(fromUid));
            }
        },1,1, TimeUnit.SECONDS);
    }

}
