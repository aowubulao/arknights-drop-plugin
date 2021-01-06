package com.neoniou.bot.status.pojo;

import lombok.Data;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
@Data
public class StageDropInfo {

    private String itemId;

    private String dropPro;

    public StageDropInfo() {
    }

    public StageDropInfo(String itemId, String dropPro) {
        this.itemId = itemId;
        this.dropPro = dropPro;
    }
}
