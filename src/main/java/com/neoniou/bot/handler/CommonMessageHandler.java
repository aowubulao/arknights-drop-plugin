package com.neoniou.bot.handler;

import com.neoniou.bot.handler.child.ItemDropHandler;
import com.neoniou.bot.handler.child.RefreshDataHandler;
import com.neoniou.bot.handler.child.StageDropHandler;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class CommonMessageHandler {

    private static final ItemDropHandler ITEM_DROP_HANDLER = new ItemDropHandler();

    private static final StageDropHandler STAGE_DROP_HANDLER = new StageDropHandler();

    private static final RefreshDataHandler REFRESH_DATA_HANDLER = new RefreshDataHandler();

    public static void handleGroupMessage(GroupMessageEvent event) {
        ITEM_DROP_HANDLER.handleGroupMessage(event);
        STAGE_DROP_HANDLER.handleGroupMessage(event);
    }

    public static void handleFriendMessage(FriendMessageEvent event) {
        REFRESH_DATA_HANDLER.handleFriendMessage(event);
    }
}
