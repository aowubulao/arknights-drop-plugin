package com.neoniou.bot.status;

import com.neoniou.bot.ArkDropPlugin;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class StatusTotal {

    public static volatile boolean isOk = false;

    public static void initStatus() {
        MiraiLogger logger = ArkDropPlugin.getMiraiLogger();
        isOk = false;
        logger.info("明日方舟掉率查询插件正在加载数据...");

        long startTime = System.currentTimeMillis();
        init();
        long endTime = System.currentTimeMillis();

        logger.info("明日方舟掉率查询插件加载数据完成！耗时：" + (endTime - startTime) + "ms");
        isOk = true;
    }

    private static void init() {
        ItemStatus.initStatus();
        StageStatus.initStatus();
        ResultStatus.initStatus();
    }
}
