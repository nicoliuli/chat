package utils;

import io.netty.channel.Channel;

/**
 * channel的工具类
 */
public class ChannelUtil {

    public static void sengLoginMsg(Channel channel,Long uid) {
        channel.writeAndFlush(ChatMsgUtil.builLoginMsg(uid));
    }
}
