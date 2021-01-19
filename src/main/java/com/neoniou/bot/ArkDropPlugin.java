package com.neoniou.bot;

import com.neoniou.bot.constant.PluginInfo;
import com.neoniou.bot.handler.CommonMessageHandler;
import com.neoniou.bot.status.StatusTotal;
import com.neoniou.bot.utils.ThreadUtil;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class ArkDropPlugin extends JavaPlugin {

    private static MiraiLogger logger;

    public ArkDropPlugin() {
        super(
                new JvmPluginDescriptionBuilder(PluginInfo.ID, PluginInfo.VERSION)
                        .author(PluginInfo.AUTHOR)
                        .name(PluginInfo.NAME)
                        .build()
        );
        logger = getLogger();
    }

    @Override
    public void onEnable() {
        ThreadUtil.createDefaultThreadPool(3);
        ThreadUtil.execute(StatusTotal::initStatus);

        GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> ThreadUtil.execute(() -> CommonMessageHandler.handleFriendMessage(event)));

        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            if (event.getGroup().getBotMuteRemaining() == 0) {
                ThreadUtil.execute(() -> CommonMessageHandler.handleGroupMessage(event));
            }
        });
    }

    public static MiraiLogger getMiraiLogger() {
        return logger;
    }
}
