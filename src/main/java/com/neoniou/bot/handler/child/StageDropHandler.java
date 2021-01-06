package com.neoniou.bot.handler.child;

import com.neoniou.bot.handler.MessageHandler;
import com.neoniou.bot.status.ItemStatus;
import com.neoniou.bot.status.ResultStatus;
import com.neoniou.bot.status.StageStatus;
import com.neoniou.bot.status.StatusTotal;
import com.neoniou.bot.status.pojo.Stage;
import com.neoniou.bot.status.pojo.StageDropInfo;
import net.mamoe.mirai.message.GroupMessageEvent;

import java.util.List;
import java.util.Locale;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class StageDropHandler extends MessageHandler {

    private static final String STR = "#地图掉率 ";

    private static final int MATCHING = 3;

    @Override
    public void handleGroupMessage(GroupMessageEvent event) {
        String messageBody = getMessageBody(event);
        if (!isMatch(messageBody, STR, MATCHING)) {
            return;
        }
        if (!StatusTotal.isOk) {
            event.getGroup().sendMessage("正在加载数据中，请稍后再试...");
            return;
        }

        String code = messageBody.substring(messageBody.lastIndexOf(" ") + 1).toUpperCase(Locale.ROOT);
        Stage stage = StageStatus.codeKeyMap.get(code);
        if (stage == null) {
            event.getGroup().sendMessage("没有该地图！");
            return;
        }

        StringBuilder sb = new StringBuilder("地图：");
        sb.append(stage.getCode()).append("，理智消耗：").append(stage.getApCost());
        sb.append("\n掉落物品：");

        List<StageDropInfo> dropInfos = ResultStatus.stageIdMap.get(stage.getStageId());
        for (StageDropInfo info : dropInfos) {
            sb.append(ItemStatus.idKeyMap.get(info.getItemId())).append("(").append(info.getDropPro()).append("%)，");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n数据来源：企鹅物流数据统计（https://exusi.ai/").append(code).append("）");

        event.getGroup().sendMessage(sb.toString());
    }
}
