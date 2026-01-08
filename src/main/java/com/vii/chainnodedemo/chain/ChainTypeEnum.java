package com.vii.chainnodedemo.chain;

import lombok.Getter;
/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@Getter
public enum ChainTypeEnum {
    LEND_ROUTE(false);
    private final boolean needSentinelNode;

    ChainTypeEnum(boolean needSentinelNode) {
        this.needSentinelNode = needSentinelNode;
    }

    public boolean isNeedSentinelNode() {
        return needSentinelNode;
    }
}
