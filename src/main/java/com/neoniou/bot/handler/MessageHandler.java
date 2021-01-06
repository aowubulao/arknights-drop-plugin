package com.neoniou.bot.handler;

import cn.hutool.http.HttpRequest;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.MessageUtils;

import java.io.InputStream;

/**
 * @author Neo.Zzj
 * @date 2020/12/13
 */
public abstract class MessageHandler {

    private static final int REGEX = 1;

    private static final int EQUAL = 2;

    private static final int CONTAINS = 3;

    private static final String PREFIX = "{";

    private static final String SUFFIX = "]";

    /**
     * 处理 Mirai MessageEvent，获取消息
     * @param event MessageEvent
     * @return Message Body 原消息
     */
    public static String getMessageBody(MessageEvent event) {
        String msgString = event.getMessage().toString();
        return msgString.substring(msgString.indexOf("]") + 1);
    }

    /**
     * 验证消息是否匹配
     *
     * @param srcMsg 发送的源消息
     * @param str 需要匹配的字符串
     * @param matching 匹配规则 1:正则 2:相等 3:包含
     * @return true or false
     */
    public static boolean isMatch(String srcMsg, String str, int matching) {
        if (matching == REGEX) {
            return srcMsg.matches(str);
        }
        if (matching == EQUAL) {
            return srcMsg.equals(str);
        }
        if (matching == CONTAINS) {
            return srcMsg.contains(str);
        }
        return false;
    }

    /**
     * 根据 url 获取图片 inputStream
     *
     * @param url 图片链接
     * @return 输入流
     */
    public static InputStream getImageFile(String url) {
        return HttpRequest.get(url)
                .execute()
                .bodyStream();
    }

    /**
     * 根据 message body提取图片链接
     *
     * @param event MessageEvent
     * @return image url
     */
    public static String getImageUrl(MessageEvent event) {
        String messageBody = getMessageBody(event);
        String imageId = messageBody.substring(messageBody.indexOf(PREFIX), messageBody.indexOf(SUFFIX));
        Bot bot = event.getBot();
        return bot.queryImageUrl(MessageUtils.newImage(imageId));
    }

    /**
     * 处理群消息
     *
     * @param event 群消息事件
     */
    public abstract void handleGroupMessage(GroupMessageEvent event);
}
