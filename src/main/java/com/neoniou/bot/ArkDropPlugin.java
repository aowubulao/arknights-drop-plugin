package com.neoniou.bot;

import com.neoniou.bot.constant.PluginInfo;
import com.neoniou.bot.handler.CommonMessageHandler;
import com.neoniou.bot.status.StatusTotal;
import com.neoniou.bot.utils.ThreadUtil;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.SimpleListenerHost;
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

        Events.registerEvents(this, new SimpleListenerHost(this.getCoroutineContext()) {
            @EventHandler
            public void onGroup(GroupMessageEvent event) {
                ThreadUtil.execute(() -> CommonMessageHandler.handleGroupMessage(event));
            }

            @EventHandler
            public void onFriend(FriendMessageEvent event) {
                ThreadUtil.execute(() -> CommonMessageHandler.handleFriendMessage(event));
            }
        });
    }

    public static MiraiLogger getMiraiLogger() {
        return logger;
    }
}
