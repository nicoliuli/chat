package utils;

import io.netty.channel.Channel;
import model.chat.ChatMsg;

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
    public static void startSendPingMsgSchedul(Channel channel,Long fromUid) {
        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(ChatMsgUtil.buildPingMsg(fromUid));
            }
        },1,1, TimeUnit.SECONDS);
    }

    public static void sendClustorMsgSchedule(Channel channel,Long fromUid,Long toUid){
        ChatMsg chatMsg = ChatMsgUtil.buildSingleChatMsg(fromUid, toUid);

        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(chatMsg);
                System.out.println(fromUid+" 给 "+toUid+" 发定时消息");
            }
        },1,1,TimeUnit.SECONDS);
    }
}
