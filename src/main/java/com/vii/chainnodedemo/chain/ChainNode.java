package com.vii.chainnodedemo.chain;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

public interface ChainNode<Request,Response> extends Comparable<ChainNode<Request,Response>> {
    void setNext(ChainNode<Request,Response> chainNode);
    ChainNode<Request,Response> getNext();
    Response execute(Request request);
    int getOrder();
    ChainTypeEnum getChainType();
}
