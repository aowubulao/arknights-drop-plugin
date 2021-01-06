package com.neoniou.bot.handler.child;

import com.neoniou.bot.handler.MessageHandler;
import com.neoniou.bot.status.StatusTotal;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class RefreshDataHandler extends MessageHandler {

    private static final String STR = "#刷新掉率数据";

    private static final int MATCHING = 3;

    @Override
    public void handleGroupMessage(GroupMessageEvent event) {

    }

    public void handleFriendMessage(FriendMessageEvent event) {
        String messageBody = getMessageBody(event);
        if (!isMatch(messageBody, STR, MATCHING)) {
            return;
        }
        if (!StatusTotal.isOk) {
            event.getFriend().sendMessage("已经在重新加载数据，请勿重复操作！");
            return;
        }
        event.getFriend().sendMessage("开始重新加载...");
        StatusTotal.initStatus();
        event.getFriend().sendMessage("数据重新加载完成！");
    }
}
