package com.vii.chainnodedemo.component.processor;

import com.vii.chainnodedemo.component.AbstractLendRoutePostProcessor;
import com.vii.chainnodedemo.component.LendRoutePostProcessContext;
import com.vii.chainnodedemo.dto.InvestorRoutingLendRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@Slf4j
@Component
public class InvestorCRouteProcessor extends AbstractLendRoutePostProcessor {

    @Override
    protected boolean support(LendRoutePostProcessContext context) {
        // Return TRUE means accepting ALL remaining requests
        return true;
    }

    @Override
    protected InvestorRoutingLendRespVO doExecute(LendRoutePostProcessContext context) {
        log.info(">>> [STEP 3] Processing Logic for DEFAULT (Investor C)");

        // Logic Default
        InvestorRoutingLendRespVO response = context.getInvestorRoutingLendRespVO();
        response.setStatus("DEFAULT_ROUTE_C");
        response.setInvestorCode("INVESTOR_C");

        return response;
    }

    @Override
    public int getOrder() {
        // Largest number = LOWEST Priority (Last Resort)
        return Ordered.LOWEST_PRECEDENCE;
    }
}