package com.neoniou.bot.status.pojo;

import lombok.Data;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
@Data
public class MatrixInfo {

    private String stageId;

    private String itemId;

    private Integer quantity;

    private Integer times;
}