package com.newsupplytech.jxsmh.until;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 获取普通UUID
     * @return  如fsfs-fdsfs-fdsf
     */
    public static String getUUIDStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取去除了-的UUID串
     * @return  如fsdfsfsfsf
     */
    public static String getUUIDShort() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
