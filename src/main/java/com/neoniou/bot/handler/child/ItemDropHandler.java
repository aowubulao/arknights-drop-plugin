package com.neoniou.bot.handler.child;

import com.neoniou.bot.handler.MessageHandler;
import com.neoniou.bot.status.ItemStatus;
import com.neoniou.bot.status.ResultStatus;
import com.neoniou.bot.status.StageStatus;
import com.neoniou.bot.status.StatusTotal;
import com.neoniou.bot.status.pojo.ItemDropInfo;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Comparator;
import java.util.List;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class ItemDropHandler extends MessageHandler {

    private static final String STR = "\\u7269\\u54c1\\u67e5\\u8be2\\u0023.+?";

    private static final int MATCHING = 1;

    private static final int MAX_SHOW = 3;

    private static final String ENDLESS = "∞";

    private static final String SHARP = "#";

    @Override
    public void handleMessage(MessageEvent event) {
        String messageBody = getMessageBody(event);
        if (!isMatch(messageBody, STR, MATCHING)) {
            return;
        }
        if (!StatusTotal.isOk) {
            event.getSubject().sendMessage("正在加载数据中，请稍后再试...");
            return;
        }

        String itemName = messageBody.substring(messageBody.lastIndexOf(SHARP) + 1);
        if (ItemStatus.nameKeyMap.containsKey(itemName)) {
            getDropAndSend(ItemStatus.nameKeyMap.get(itemName), event);
        } else if (ItemStatus.aliasMap.containsKey(itemName)) {
            List<String> list = ItemStatus.aliasMap.get(itemName);
            if (list.size() == 1) {
                getDropAndSend(list.get(0), event);
            } else {
                event.getSubject().sendMessage("你要找的是不是：\n" + list);
            }
        } else {
            event.getSubject().sendMessage("没有找到此材料！");
        }
    }

    private void getDropAndSend(String itemId, MessageEvent event) {
        String name = ItemStatus.idKeyMap.get(itemId);
        StringBuilder sb = new StringBuilder(name + "的掉率统计\n");

        List<ItemDropInfo> dropInfos = ResultStatus.itemIdKeyMap.get(itemId);
        sb.append("按照单件期望理智：\n");
        dropInfos.sort(Comparator.comparingDouble(x -> x.getSingleAp().equals(ENDLESS) ? Double.MAX_VALUE : Double.parseDouble(x.getSingleAp())));
        for (int i = 0; i < MAX_SHOW; i++) {
            if (i == dropInfos.size()) {
                break;
            }
            ItemDropInfo dropInfo = dropInfos.get(i);
            String stageId = dropInfo.getStageId();
            String code = StageStatus.idKeyMap.get(stageId).getCode();
            sb.append(code).append("(").append(dropInfo.getSingleAp()).append(")，");
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append("\n按照掉率：\n");
        dropInfos.sort((x, y) -> Double.compare(Double.parseDouble(y.getDropPro()), Double.parseDouble(x.getDropPro())));
        for (int i = 0; i < MAX_SHOW; i++) {
            if (i == dropInfos.size()) {
                break;
            }
            ItemDropInfo dropInfo = dropInfos.get(i);
            String stageId = dropInfo.getStageId();
            String code = StageStatus.idKeyMap.get(stageId).getCode();
            sb.append(code).append("(").append(dropInfo.getDropPro()).append("%)，");
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append("\n数据来源：企鹅物流数据统计（https://exusi.ai/").append(itemId).append("）");

        event.getSubject().sendMessage(sb.toString());
    }
}