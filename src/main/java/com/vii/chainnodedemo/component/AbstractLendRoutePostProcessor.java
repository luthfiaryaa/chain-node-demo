package com.vii.chainnodedemo.component;

import com.vii.chainnodedemo.chain.AbstractChainNode;
import com.vii.chainnodedemo.chain.ChainTypeEnum;
import com.vii.chainnodedemo.dto.InvestorRoutingLendRespVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

// This class is a bridge between the ChainNode Framework and Business Logic
public abstract class AbstractLendRoutePostProcessor extends AbstractChainNode<LendRoutePostProcessContext, InvestorRoutingLendRespVO> {

    private static final Logger log = LoggerFactory.getLogger(AbstractLendRoutePostProcessor.class);

    @Override
    public InvestorRoutingLendRespVO execute(LendRoutePostProcessContext context) {
        // 1. Check whether this Processor SUPPORTS the current context?
        if (!support(context)) {
            // If NOT supported, pass to the next node (Next)
            if (next != null) {
                return next.execute(context);
            }
            // If there is no next, it means there is no one to handle it
            log.warn("Tidak ada processor yang handle context ini: {}", context);
            return null;
        }

        // 2. If SUPPORT, run the execution logic
        return doExecute(context);
    }

    // Method abstrak yang WAJIB diisi oleh anak-anaknya (InvestorA, B, C)
    protected abstract InvestorRoutingLendRespVO doExecute(LendRoutePostProcessContext context);

    protected abstract boolean support(LendRoutePostProcessContext context);

    @Override
    public ChainTypeEnum getChainType() {
        // Pastikan Enum ini ada di ChainTypeEnum.java
        return ChainTypeEnum.LEND_ROUTE;
    }
}