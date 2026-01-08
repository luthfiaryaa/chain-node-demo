package com.vii.chainnodedemo.chain;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

public class NoopChainNode implements ChainNode<Object, Object> {
    private final ChainTypeEnum chainType;

    public NoopChainNode(ChainTypeEnum chainType) {
        this.chainType = chainType;
    }

    @Override public void setNext(ChainNode<Object, Object> chainNode) {}
    @Override public ChainNode<Object, Object> getNext() { return null; }
    @Override public Object execute(Object o) { return null; }
    @Override public int getOrder() { return 0; }
    @Override public ChainTypeEnum getChainType() { return null; }
    @Override public int compareTo(ChainNode<Object, Object> o) { return 0; }
}
