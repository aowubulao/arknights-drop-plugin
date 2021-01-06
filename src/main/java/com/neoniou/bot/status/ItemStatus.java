package com.neoniou.bot.status;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.neoniou.bot.constant.PenguinApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class ItemStatus {

    private static final String ITEM_ID = "itemId";

    private static final String NAME = "name";

    private static final String ALIAS = "alias";

    private static final String ZH = "zh";

    public static Map<String, String> nameKeyMap;

    public static Map<String, List<String>> aliasMap;

    public static Map<String, String> idKeyMap;


    public static void initStatus() {
        nameKeyMap = new HashMap<>(512);
        aliasMap = new HashMap<>(512);
        idKeyMap = new HashMap<>(512);

        String body = HttpRequest.get(PenguinApi.GET_ITEMS)
                .execute()
                .body();

        JSONArray items = JSONUtil.parseArray(body);
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            String name = item.getStr(NAME);
            String itemId = item.getStr(ITEM_ID);

            nameKeyMap.put(name, itemId);
            idKeyMap.put(itemId, name);

            JSONArray alias = item.getJSONObject(ALIAS).getJSONArray(ZH);
            for (Object a : alias) {
                String key = a.toString();
                if (!aliasMap.containsKey(key)) {
                    aliasMap.put(key, new ArrayList<>());
                }
                List<String> tempList = aliasMap.get(key);
                tempList.add(name);
                aliasMap.put(key, tempList);
            }
        }
    }
}