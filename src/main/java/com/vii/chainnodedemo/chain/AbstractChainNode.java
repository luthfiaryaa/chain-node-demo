package com.vii.chainnodedemo.chain;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

public abstract class AbstractChainNode<Request,Response> implements ChainNode<Request,Response> {
    protected ChainNode<Request,Response> next;

    @Override
    public void setNext(ChainNode<Request,Response> chainNode) {
        this.next = chainNode;
    }

    @Override
    public ChainNode<Request, Response> getNext() {
        return next;
    }

    protected Response invokeNext(Request request) {
        if (next != null) {
            return next.execute(request);
        }
        return null;
    }

    @Override
    public int compareTo(ChainNode<Request, Response> o) {
        return this.getOrder() - o.getOrder();
    }
}
