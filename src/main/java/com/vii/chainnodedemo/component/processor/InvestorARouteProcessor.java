package com.vii.chainnodedemo.component.processor; // Sesuaikan packagemu

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
public class InvestorARouteProcessor extends AbstractLendRoutePostProcessor {

    @Override
    protected boolean support(LendRoutePostProcessContext context) {
        // CHECK WHETHER THE INVESTOR CODE IS A
        String investorCode = context.getInvestorChannelRouteDTO().getInvestorCode();
        return "INVESTOR_A".equals(investorCode);
    }

    @Override
    protected InvestorRoutingLendRespVO doExecute(LendRoutePostProcessContext context) {
        log.info(">>> [STEP 1] Processing Special Logic for INVESTOR A");

        // Example Logic A: Set status to APPROVED_BY_A
        InvestorRoutingLendRespVO response = context.getInvestorRoutingLendRespVO();
        response.setStatus("APPROVED_BY_A");
        response.setInvestorCode("INVESTOR_A");

        return response;
    }

    @Override
    public int getOrder() {
        // Small Number = HIGH Priority (Go First)
        // HIGHEST_PRECEDENCE is around -2 Billion
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}