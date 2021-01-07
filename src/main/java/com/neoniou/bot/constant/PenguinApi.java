package com.neoniou.bot.constant;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public interface PenguinApi {

    String IO_BASE = "https://penguin-stats.io";

    String CN_BASE = "https://penguin-stats.cn";

    String GET_ITEMS = IO_BASE + "/PenguinStats/api/v2/items/";

    String GET_MATRIX = IO_BASE + "/PenguinStats/api/v2/result/matrix?is_personal=false&server=CN&show_closed_zones=false";

    String GET_MATRIX_CLOSED = IO_BASE + "/PenguinStats/api/v2/result/matrix?is_personal=false&server=CN&show_closed_zones=true";

    String GET_STAGES = IO_BASE + "/PenguinStats/api/v2/stages?server=CN";

}
