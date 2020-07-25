package utils;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import model.chat.ChatMsg;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScannerUtil {
    /**
     * 监听客户端的输入
     */
    public static void scanner(Channel channel,Long fromUid) {

        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("私聊输入single，群聊输入group...");
                String chatType = sc.nextLine();
                if("single".equals(chatType)){
                    System.out.print("请输入对方的uid：");
                    String toUid = sc.nextLine();
                    while (true){
                        String line = sc.nextLine();
                        if ("exit".equals(line) || "quit".equals(line)) {
                            close(channel);
                            return;
                        }
                        if("changeChatType".equals(line)){
                            break;
                        }
                        if ("changeUser".equals(line)) {
                            System.out.print("请输入对方的uid：");
                            toUid = sc.nextLine();
                            System.out.println("切换成功，当前对方uid=" + toUid);
                            continue;
                        }
                        System.out.println("发送的内容是：" + line);
                        ChatMsg chatMsg = ChatMsgUtil.buildSingleChatMsg(fromUid,Long.parseLong(toUid),line);
                        channel.writeAndFlush(chatMsg);

                    }

                }else if("group".equals(chatType)){
                    System.out.println("请输入要发给的uid和消息，格式：消息-->uid1,uid2...");
                    while (true){
                        String line = sc.nextLine();
                        if ("exit".equals(line) || "quit".equals(line)) {
                            close(channel);
                            return;
                        }
                        if(line==null || line.length()==0 || line.split("-->").length!=2){
                            System.out.println("输入格式有误");
                            break;
                        }
                        if("changeChatType".equals(line)){
                            break;
                        }

                        String[] split = line.split("-->");
                        String text = split[0];
                        List<String> toUidList = Arrays.asList(split[1].split(","));
                        ChatMsg chatMsg = ChatMsgUtil.buildGroupChatMsg(fromUid,toUidList,text);
                        channel.writeAndFlush(chatMsg);
                    }
                }
            }

        }).start();


    }

    private static void close(Channel channel){
        channel.close().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("channel close");
            }
        });
    }
}
