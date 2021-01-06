package com.neoniou.bot.constant;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public interface PenguinApi {

    String GET_ITEMS = "https://penguin-stats.cn/PenguinStats/api/v2/items/";

    String GET_MATRIX = "https://penguin-stats.cn/PenguinStats/api/v2/result/matrix?is_personal=false&server=CN&show_closed_zones=false";

    String GET_MATRIX_CLOSED = "https://penguin-stats.cn/PenguinStats/api/v2/result/matrix?is_personal=false&server=CN&show_closed_zones=true";

    String GET_STAGES = "https://penguin-stats.cn/PenguinStats/api/v2/stages?server=CN";

}
