package com.neoniou.bot.status.pojo;

import lombok.Data;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
@Data
public class ItemDropInfo {

    private String itemId;

    private String stageId;

    private String dropPro;

    private String singleAp;

    public ItemDropInfo() {
    }

    public ItemDropInfo(String itemId, String stageId, String dropPro, String singleAp) {
        this.itemId = itemId;
        this.stageId = stageId;
        this.dropPro = dropPro;
        this.singleAp = singleAp;
    }
}
