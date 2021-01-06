package com.neoniou.bot.status;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.neoniou.bot.constant.PenguinApi;
import com.neoniou.bot.status.pojo.MatrixInfo;
import com.neoniou.bot.status.pojo.ItemDropInfo;
import com.neoniou.bot.status.pojo.StageDropInfo;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class ResultStatus {

    private static final String RANDOM_MATERIAL = "randomMaterialRune";

    private static final String MATRIX = "matrix";

    public static Map<String, List<ItemDropInfo>> itemIdKeyMap;

    public static Map<String, List<StageDropInfo>> stageIdMap;

    public static void initStatus() {
        itemIdKeyMap = new HashMap<>(1024);
        stageIdMap = new HashMap<>(1024);

        String body = HttpRequest.get(PenguinApi.GET_MATRIX)
                .execute()
                .body();
        List<MatrixInfo> matrix = JSONUtil.parseObj(body).getJSONArray(MATRIX).toList(MatrixInfo.class);
        initItemMap(matrix);

        body = HttpRequest.get(PenguinApi.GET_MATRIX_CLOSED)
                .execute()
                .body();
        List<MatrixInfo> matrixClosed = JSONUtil.parseObj(body).getJSONArray(MATRIX).toList(MatrixInfo.class);
        initStageMap(matrixClosed);
    }

    private static void initItemMap(List<MatrixInfo> matrix) {
        for (MatrixInfo info : matrix) {
            if (info.getStageId().contains(RANDOM_MATERIAL)) {
                continue;
            }

            if (!itemIdKeyMap.containsKey(info.getItemId())) {
                itemIdKeyMap.put(info.getItemId(), new ArrayList<>());
            }
            List<ItemDropInfo> dropInfos = itemIdKeyMap.get(info.getItemId());
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            double dropPro = getDropPro(info);
            double singleAp = Double.parseDouble(StageStatus.idKeyMap.get(info.getStageId()).getApCost()) / dropPro;
            dropInfos.add(
                    new ItemDropInfo(
                            info.getItemId(),
                            info.getStageId(),
                            decimalFormat.format(dropPro * 100),
                            decimalFormat.format(singleAp)
                    )
            );
            itemIdKeyMap.put(info.getItemId(), dropInfos);
        }
    }

    private static void initStageMap(List<MatrixInfo> matrix) {
        for (MatrixInfo info : matrix) {
            if (!stageIdMap.containsKey(info.getStageId())) {
                stageIdMap.put(info.getStageId(), new ArrayList<>());
            }
            List<StageDropInfo> dropInfos = stageIdMap.get(info.getStageId());
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            double dropPro = getDropPro(info);
            dropInfos.add(new StageDropInfo(info.getItemId(), decimalFormat.format(dropPro * 100)));
            stageIdMap.put(info.getStageId(), dropInfos);
        }

        //sort
        for (Map.Entry<String, List<StageDropInfo>> entry : stageIdMap.entrySet()) {
            List<StageDropInfo> dropInfos = entry.getValue();
            dropInfos.sort((x, y) -> Double.compare(Double.parseDouble(y.getDropPro()), Double.parseDouble(x.getDropPro())));
        }
    }

    private static double getDropPro(MatrixInfo info) {
        return Double.valueOf(info.getQuantity()) / info.getTimes();
    }
}
