package com.neoniou.bot.status;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.neoniou.bot.constant.PenguinApi;
import com.neoniou.bot.status.pojo.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class StageStatus {

    public static Map<String, Stage> codeKeyMap;

    public static Map<String, Stage> idKeyMap;

    public static void initStatus() {
        codeKeyMap = new HashMap<>(512);
        idKeyMap = new HashMap<>(512);

        String body = HttpRequest.get(PenguinApi.GET_STAGES)
                .execute()
                .body();
        JSONArray stages = JSONUtil.parseArray(body);

        for (int i = 0; i < stages.size(); i++) {
            Stage stage = JSONUtil.toBean(stages.getJSONObject(i), Stage.class);
            codeKeyMap.put(stage.getCode(), stage);
            idKeyMap.put(stage.getStageId(), stage);
        }
    }
}